package controllers; // Define el paquete al que pertenece esta clase

import core.Controller; // Importa la clase base Controller
import models.SchedulerEvent; // Importa el modelo de evento
import models.SchedulerIO; // Importa la clase que maneja entrada/salida de eventos (guardar/eliminar)
import views.RemoveEventView; // Importa la vista para eliminar eventos
import javax.swing.JOptionPane; // Para mostrar mensajes al usuario
import java.util.Vector; // Para manejar listas dinámicas (Vector)

// Controlador que maneja la lógica de la vista RemoveEventView
public class RemoveEventController extends Controller {
    
    // Atributo que guarda la referencia a la vista asociada
    private RemoveEventView removeEventView;
    // Referencia al controlador de la lista de eventos (para interactuar con los datos existentes)
    private EventListController eventListController;

    // Constructor: recibe el controlador de lista de eventos y lo asocia
    public RemoveEventController(EventListController eventListController) {
        this.eventListController = eventListController;
    }

    // Método que se ejecuta al iniciar el controlador
    @Override
    public void run() {
        // Crea la vista de eliminación de eventos, pasándole este controlador
        removeEventView = new RemoveEventView(this);
        // Carga los eventos en la tabla de la vista
        loadEvents(); 
    }

    // Método que obtiene todos los eventos desde EventListController y los muestra en la tabla
    public void loadEvents() {
        // Obtiene los datos (todas las filas de eventos)
        Vector<Vector<Object>> data = eventListController.getDataColumns();
        // Obtiene los nombres de las columnas (cabecera de tabla)
        Vector<String> columnNames = eventListController.getNameColumns();
        // Actualiza la tabla de la vista con esos datos
        removeEventView.updateTable(data, columnNames);
    }
    
    // Método para manejar la eliminación de eventos seleccionados
    public void removeSelectedEvents() {
        try {
            // Clase que maneja entrada/salida de eventos (persistencia)
            SchedulerIO schedulerIO = new SchedulerIO();
            // Obtiene las filas seleccionadas en la vista
            Vector<Integer> selectedRows = removeEventView.getSelectedRows();
            // Obtiene los datos completos de la lista de eventos
            Vector<Vector<Object>> data = eventListController.getDataColumns();
            
            // Recorre las filas seleccionadas en orden inverso (para no desajustar los índices)
            for (int i = selectedRows.size() - 1; i >= 0; i--) {
                int rowIndex = selectedRows.get(i); // Índice de la fila seleccionada
                Vector<Object> rowData = data.get(rowIndex); // Datos de esa fila
                
                // Crea un objeto SchedulerEvent a partir de los datos de la fila
                SchedulerEvent eventToRemove = new SchedulerEvent(
                    rowData.get(0).toString(), // Fecha
                    rowData.get(1).toString(), // Descripción
                    rowData.get(2).toString(), // Frecuencia
                    rowData.get(3).toString(), // Email
                    rowData.get(4).toString().equals("ON") // Alarma (ON/OFF → booleano)
                );
                // Elimina el evento del archivo persistente
                schedulerIO.removeEvent(eventToRemove);
            }
            
            // Recarga los eventos en la tabla de esta vista
            loadEvents();
            // Recarga también la tabla en EventListController (para mantener sincronización)
            eventListController.reloadTable(); // Debes implementar este método en EventListController
            
            // Muestra un mensaje de éxito al usuario
            JOptionPane.showMessageDialog(null, "Eventos eliminados con éxito.");
        } catch (Exception e) {
            // Si ocurre un error, muestra un mensaje de error
            JOptionPane.showMessageDialog(
                null, 
                "Error al eliminar eventos: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Getter que devuelve la vista asociada a este controlador
    public RemoveEventView getView() {
        return removeEventView;
    }
}
