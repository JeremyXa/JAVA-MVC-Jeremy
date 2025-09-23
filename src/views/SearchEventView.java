package views;
// Indica que esta clase está en el paquete "views"

import java.awt.BorderLayout; // Layout que organiza componentes en Norte, Sur, Este, Oeste y Centro
import java.awt.FlowLayout;   // Layout que organiza componentes de izquierda a derecha
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; // Interfaces para manejar acciones de los botones
import java.util.Vector; // Estructura dinámica para manejar listas de datos
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable; // Tabla para mostrar resultados de la búsqueda
import javax.swing.JTextField; // Campo de texto para ingresar la búsqueda
import javax.swing.table.DefaultTableModel; // Modelo de datos para la tabla
import controllers.SearchEventController; // Controlador que maneja la lógica de búsqueda
import core.Model;
import core.View;

@SuppressWarnings("serial")
public class SearchEventView extends JPanel implements View {
    // Esta clase es una vista (implementa View) y un panel de Swing (JPanel)

    private SearchEventController searchEventController; // Controlador asociado a la búsqueda
    private JTextField searchField; // Campo de texto donde el usuario escribe el evento a buscar
    private JButton searchButton;   // Botón para ejecutar la búsqueda
    private JTable resultsTable;    // Tabla para mostrar los resultados encontrados

    // Constructor: recibe el controlador y construye la interfaz gráfica
    public SearchEventView(SearchEventController searchEventController) {
        this.searchEventController = searchEventController; // Guarda referencia del controlador
        make_frame();          // Configura el panel principal
        make_search_panel();   // Crea la barra de búsqueda
        make_results_table();  // Crea la tabla de resultados
        addListeners();        // Agrega eventos a los botones
    }
    
    @Override
    public void update(Model model, Object data) {
        // Método de la interfaz View: se usa cuando el modelo envía datos a la vista
        if (data != null) {
            String notice = (String) data; // Convierte el objeto recibido en String
            JOptionPane.showMessageDialog(this, notice); // Muestra un mensaje en pantalla
        }
    }
    
    // Configura el panel principal con BorderLayout
    private void make_frame() {
        setLayout(new BorderLayout());
    }
    
    // Crea la barra de búsqueda (campo de texto + botón)
    private void make_search_panel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        // FlowLayout centra los elementos con un espacio de 5 píxeles entre ellos
        searchField = new JTextField(20); // Campo de texto de 20 columnas
        searchButton = new JButton("Buscar evento"); // Botón para ejecutar la búsqueda
        searchPanel.add(searchField); // Añade el campo al panel
        searchPanel.add(searchButton); // Añade el botón al panel
        add(searchPanel, BorderLayout.NORTH); // Coloca el panel en la parte superior (NORTH)
    }
    
    // Crea la tabla para mostrar los resultados de la búsqueda
    private void make_results_table() {
        resultsTable = new JTable(); // Inicializa la tabla vacía
        JScrollPane scrollPane = new JScrollPane(resultsTable); 
        // Se usa JScrollPane para que la tabla tenga barra de desplazamiento
        add(scrollPane, BorderLayout.CENTER); // La tabla ocupa el centro del panel
    }
    
    // Agrega los listeners (eventos de los botones)
    private void addListeners() {
        // Cuando se hace clic en el botón, se llama al controlador para buscar eventos
        searchButton.addActionListener(e -> searchEventController.searchEvents());
    }

    // ---------------- Métodos para interactuar con el controlador ----------------
    
    // Devuelve el texto ingresado en el campo de búsqueda
    public String getSearchQuery() {
        return searchField.getText();
    }
    
    // Actualiza la tabla con los resultados de la búsqueda
    public void updateTable(Vector<Vector<Object>> data, Vector<String> columnNames) {
        // Se crea un modelo de tabla con los datos encontrados
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        resultsTable.setModel(model); // Se asigna el modelo a la tabla
    }
}
