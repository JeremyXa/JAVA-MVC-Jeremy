package views; // Paquete donde está ubicada la clase

// Importaciones necesarias para diseño gráfico y componentes Swing
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controllers.EventListController; // Controlador asociado
import core.Model;  // Interfaz de modelo del patrón MVC
import core.View;  // Interfaz de vista del patrón MVC

// Anotación para evitar advertencias de serialización en la clase
@SuppressWarnings("serial")
// Clase que representa la vista de la lista de eventos, hereda de JPanel e implementa View
public class EventListView extends JPanel implements View {
    private EventListController eventListController; // Controlador que maneja la lógica
    private JTable table;                            // Tabla que muestra los eventos
    private JButton removeButton;                    // Botón para eliminar eventos
    private JButton selectAllButton;                 // Botón para seleccionar/deseleccionar todos los eventos

    // Constructor que recibe el controlador y la tabla
    public EventListView(EventListController eventListController, JTable table) {
        this.eventListController = eventListController; // Guarda referencia al controlador
        this.table = table;                             // Asigna la tabla recibida

        make_frame();   // Crea la estructura de la vista (layout + tabla con scroll)
        make_buttons(); // Crea y agrega los botones de acción
    }

    // Método de actualización implementado desde la interfaz View
    // Se llama cuando el modelo notifica a la vista
    @Override
    public void update(Model model, Object data) {
        if (data != null) { // Si hay un mensaje (ej: error, confirmación, etc.)
            String notice = (String) data; // Convierte el objeto recibido en String
            JOptionPane.showMessageDialog(this, notice); // Muestra un cuadro de diálogo
        }
    }

    // Configura el panel principal con un BorderLayout y agrega la tabla dentro de un JScrollPane
    private void make_frame() {
        setLayout(new BorderLayout());            // Layout principal
        JScrollPane scrollPane = new JScrollPane(table); // Scroll para la tabla
        add(scrollPane, BorderLayout.CENTER);     // Se coloca al centro del panel
    }
    
    // Crea los botones (Seleccionar Todos y Remover) y los agrega en la parte inferior
    private void make_buttons() {
        // Panel para botones con FlowLayout (centrado, separación de 10px y margen 5px)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        // Botón para seleccionar o deseleccionar todas las filas
        selectAllButton = new JButton("Seleccionar Todos");
        selectAllButton.addActionListener(e -> selectAllRows()); // Acción asociada

        // Botón para remover eventos seleccionados
        removeButton = new JButton("Remover");
        // Acción que invoca el método del controlador
        removeButton.addActionListener(e -> eventListController.removeSelectedEvents());

        // Agrega botones al panel
        buttonPanel.add(selectAllButton);
        buttonPanel.add(removeButton);

        // Coloca el panel de botones en la parte inferior (SOUTH) del layout
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    // Devuelve un Vector con los índices de las filas seleccionadas (basado en un checkbox en la última columna)
    public Vector<Integer> getSelectedRows() {
        Vector<Integer> selectedRows = new Vector<>();
        for (int i = 0; i < table.getRowCount(); i++) { // Recorre todas las filas
            // Verifica que la tabla tenga más de 5 columnas (la última se usa como checkbox)
            if (table.getModel().getColumnCount() > 5) {
                Boolean isSelected = (Boolean) table.getModel().getValueAt(i, 5); // Lee valor del checkbox
                if (isSelected != null && isSelected) { // Si está marcado
                    selectedRows.add(i); // Agrega el índice de la fila seleccionada
                }
            }
        }
        return selectedRows; // Retorna el listado de filas seleccionadas
    }

    // Método para seleccionar o deseleccionar todas las filas de la tabla
    private void selectAllRows() {
        boolean allSelected = true; // Bandera para verificar si todas están seleccionadas
        // Recorre las filas para ver si hay alguna sin seleccionar
        for (int i = 0; i < table.getRowCount(); i++) {
            if (table.getModel().getColumnCount() > 5) {
                Boolean isSelected = (Boolean) table.getModel().getValueAt(i, 5);
                if (isSelected == null || !isSelected) {
                    allSelected = false; // Encontró una fila sin marcar
                    break;
                }
            }
        }
        // Si todas estaban seleccionadas → desmarca todas
        // Si alguna no estaba seleccionada → marca todas
        for (int i = 0; i < table.getRowCount(); i++) {
            if (table.getModel().getColumnCount() > 5) {
                table.getModel().setValueAt(!allSelected, i, 5);
            }
        }
    }
}
