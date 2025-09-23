package controllers; // Define el paquete en el que se encuentra esta clase

import core.Controller; // Clase base de todos los controladores
import models.SchedulerEvent; // Importa el modelo de evento (aunque aquí no se usa directamente)
import models.SchedulerIO; // Importa la clase de entrada/salida de eventos (no se usa aquí, pero puede usarse si extiendes funcionalidad)
import views.SearchEventView; // Importa la vista para buscar eventos
import java.util.Vector; // Estructura de datos tipo lista dinámica
import java.util.stream.Collectors; // Importación para usar streams (aquí no se usa, pero podrías reemplazar el for con un stream)

// Controlador encargado de manejar la lógica de búsqueda de eventos
public class SearchEventController extends Controller {
    
    // Vista asociada a este controlador
    private SearchEventView searchEventView;
    // Controlador de la lista de eventos (fuente de datos)
    private EventListController eventListController;

    // Constructor: recibe el controlador de lista de eventos para poder acceder a los datos
    public SearchEventController(EventListController eventListController) {
        this.eventListController = eventListController;
    }

    // Método que se ejecuta al iniciar este controlador
    @Override
    public void run() {
        // Crea una nueva vista de búsqueda y le pasa este controlador
        searchEventView = new SearchEventView(this);
    }

    // Método principal que realiza la búsqueda de eventos
    public void searchEvents() {
        // Obtiene la consulta de búsqueda escrita por el usuario y la pasa a minúsculas
        String query = searchEventView.getSearchQuery().toLowerCase();
        
        // Obtiene todos los datos (todas las filas de eventos)
        Vector<Vector<Object>> allData = eventListController.getDataColumns();
        // Vector para guardar los resultados filtrados
        Vector<Vector<Object>> filteredData = new Vector<>();
        
        // Recorre todos los eventos y filtra por descripción
        for (Vector<Object> row : allData) {
            // Toma la descripción (columna índice 1) y la pasa a minúsculas
            String description = row.get(1).toString().toLowerCase();
            // Si la descripción contiene el texto buscado, añade la fila a los resultados
            if (description.contains(query)) {
                filteredData.add(row);
            }
        }
        
        // Actualiza la tabla de la vista con los resultados filtrados
        searchEventView.updateTable(filteredData, eventListController.getNameColumns());
    }

    // Devuelve la vista asociada a este controlador
    public SearchEventView getView() {
        return searchEventView;
    }
}
