package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import controllers.RemoveEventController;
import core.Model;
import core.View;

@SuppressWarnings("serial")
public class RemoveEventView extends JPanel implements View {
    private RemoveEventController removeEventController;
    private JTable table;
    private JButton removeButton;
    private JButton cancelButton;
    private JButton selectAllButton; // Botón "Seleccionar Todos"

    public RemoveEventView(RemoveEventController removeEventController) {
        this.removeEventController = removeEventController;
        make_frame();
        make_buttons();
    }
    
    @Override
    public void update(Model model, Object data) {
        if (data != null) {
            String notice = (String) data;
            JOptionPane.showMessageDialog(this, notice);
        }
    }
    
    private void make_frame() {
        setLayout(new BorderLayout());
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void make_buttons() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        selectAllButton = new JButton("Seleccionar Todos");
        selectAllButton.addActionListener(e -> selectAllRows());
        
        removeButton = new JButton("Remover");
        removeButton.addActionListener(e -> removeEventController.removeSelectedEvents());

        cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> {
            // Lógica para cerrar la vista o regresar a la vista anterior
            // Podrías necesitar un método en el controlador para manejar esto
            // Por ejemplo: HomeView.switchToTab("Events");
        });

        buttonPanel.add(selectAllButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(removeButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    // Método para actualizar la tabla con nuevos datos
    public void updateTable(Vector<Vector<Object>> data, Vector<String> columnNames) {
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 5) { // Suponiendo que la última columna "Boolean" es el checkbox
                    return Boolean.class;
                }
                return super.getColumnClass(column);
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Solo la columna del checkbox es editable
            }
        };
        table.setModel(model);
    }
    
    // Método para obtener los índices de las filas seleccionadas
    public Vector<Integer> getSelectedRows() {
        Vector<Integer> selectedRows = new Vector<>();
        for (int i = 0; i < table.getRowCount(); i++) {
            Boolean isSelected = (Boolean) table.getModel().getValueAt(i, 5); // Suponiendo columna 5 es el checkbox
            if (isSelected != null && isSelected) {
                selectedRows.add(i);
            }
        }
        return selectedRows;
    }
    
    // Método para seleccionar o deseleccionar todas las filas
    private void selectAllRows() {
        boolean allSelected = true;
        for (int i = 0; i < table.getRowCount(); i++) {
            Boolean isSelected = (Boolean) table.getModel().getValueAt(i, 5);
            if (isSelected == null || !isSelected) {
                allSelected = false;
                break;
            }
        }
        for (int i = 0; i < table.getRowCount(); i++) {
            table.getModel().setValueAt(!allSelected, i, 5);
        }
    }
}