package controllers; // Define el paquete al que pertenece la clase

import java.util.Vector; // Trae la clase Vector para listas dinámicas
import javax.swing.JTable; // Permite crear y manejar tablas en la interfaz gráfica
import javax.swing.table.DefaultTableModel; // Modelo de tabla para manipular filas y columnas
import javax.swing.JOptionPane; // Para mostrar cuadros de diálogo (información, errores)

import core.Controller; // Clase base Controller de tu framework MVC
import models.SchedulerEvent; // Clase que representa un evento
import models.SchedulerIO; // Clase que maneja lectura/escritura de eventos
import views.EventListView; // Vista asociada a la lista de eventos

/**
 * Controlador responsable del comportamiento de EventListView
 */
public class EventListController extends Controller // Clase que hereda de Controller
{
    //-----------------------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------------------
    private EventListView eventListView; // Referencia a la vista de la lista de eventos
    private JTable table; // JTable que mostrará los eventos

    //-----------------------------------------------------------------------
    // Métodos
    //-----------------------------------------------------------------------
    @Override
    public void run() // Método principal que inicializa la vista y la tabla
    {
        // Crea el modelo de la tabla con datos y nombres de columnas
        DefaultTableModel model = new DefaultTableModel(getDataColumns(), getNameColumns()) {
            @Override
            public Class<?> getColumnClass(int column) { // Define el tipo de dato de cada columna
                if (column == 5) { // Si es la columna 5 (checkbox)
                    return Boolean.class; // Tipo Boolean (checkbox)
                }
                return super.getColumnClass(column); // Para las demás columnas, usar el tipo por defecto
            }
            @Override
            public boolean isCellEditable(int row, int column) { // Controla qué celdas son editables
                return column == 5; // Solo la última columna es editable
            }
        };
        table = new JTable(model); // Crea la tabla con el modelo definido
        eventListView = new EventListView(this, table); // Crea la vista y la vincula con este controlador y la tabla
    }

    /**
     * Agrega una nueva fila a la tabla
     * @param values Valores a agregar en la nueva fila
     */
    public void addNewRow(Object[] values)
    {
        ((DefaultTableModel) table.getModel()).addRow(values); // Convierte el modelo y agrega la fila
    }

    //-----------------------------------------------------------------------
    // Getters
    //-----------------------------------------------------------------------
    /**
     * Devuelve la vista asociada al controlador
     * @return eventListView
     */
    public EventListView getView()
    {
        return eventListView; // Retorna la instancia de la vista
    }

    /**
     * Devuelve los nombres de las columnas de la tabla
     * @return Vector de nombres de columnas
     */
    public Vector<String> getNameColumns()
    {
        Vector<String> nameColumns = new Vector<String>(); // Crea un vector para almacenar los nombres

        nameColumns.add("Date"); // Columna de fecha
        nameColumns.add("Description"); // Columna de descripción
        nameColumns.add("Frequency"); // Columna de frecuencia
        nameColumns.add("E-mail"); // Columna de email
        nameColumns.add("Alarm"); // Columna de alarma
        nameColumns.add("Boolean"); // Columna de checkbox editable

        return nameColumns; // Retorna el vector con los nombres
    }

    /**
     * Devuelve los datos de los eventos para la tabla
     * @return Vector de vectores (matriz de datos)
     */
    public Vector<Vector<Object>> getDataColumns()
    {
        Vector<Vector<Object>> dataColumns = null; // Inicializa la variable que contendrá los datos

        try {
            SchedulerIO schedulerIO = new SchedulerIO(); // Crea instancia de SchedulerIO
            schedulerIO.attach(eventListView); // Vincula la vista para notificaciones de cambios
            dataColumns = schedulerIO.getEvents(); // Obtiene los eventos del archivo
        } catch (Exception ex) { 
            // En caso de error no hace nada
        }

        return dataColumns; // Retorna los datos obtenidos
    }

    /**
     * Recarga la tabla con los datos actuales
     */
    public void reloadTable() {
        ((DefaultTableModel) table.getModel()).setDataVector(getDataColumns(), getNameColumns()); 
        // Actualiza la tabla con los datos actuales y nombres de columnas
    }

    /**
     * Elimina los eventos seleccionados de la tabla y del archivo
     */
    public void removeSelectedEvents() {
        try {
            SchedulerIO schedulerIO = new SchedulerIO(); // Instancia SchedulerIO
            Vector<Integer> selectedRows = eventListView.getSelectedRows(); // Obtiene filas seleccionadas
            Vector<Vector<Object>> data = getDataColumns(); // Obtiene todos los datos actuales

            // Itera las filas seleccionadas de atrás hacia adelante
            for (int i = selectedRows.size() - 1; i >= 0; i--) {
                int rowIndex = selectedRows.get(i); // Índice de la fila a eliminar
                Vector<Object> rowData = data.get(rowIndex); // Datos de la fila

                // Crea un objeto SchedulerEvent a partir de los datos para eliminarlo
                SchedulerEvent eventToRemove = new SchedulerEvent(
                    rowData.get(0).toString(), // Fecha
                    rowData.get(1).toString(), // Descripción
                    rowData.get(2).toString(), // Frecuencia
                    rowData.get(3).toString(), // Email
                    rowData.get(4).toString().equals("ON") // Alarma activada o no
                );
                schedulerIO.removeEvent(eventToRemove); // Elimina el evento del archivo
            }

            reloadTable(); // Recarga la tabla para reflejar los cambios
            JOptionPane.showMessageDialog(null, "Eventos eliminados correctamente."); // Mensaje de éxito
        } catch (Exception e) {
            // Muestra mensaje de error si ocurre un problema
            JOptionPane.showMessageDialog(null, "Error al eliminar eventos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
