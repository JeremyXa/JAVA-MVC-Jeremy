package controllers; // Define el paquete donde se encuentra la clase

import javax.swing.JOptionPane; // Para mostrar cuadros de diálogo (mensajes de error/información)

import core.Controller; // Clase base Controller del framework MVC
import models.SchedulerEvent; // Modelo que representa un evento
import models.SchedulerIO; // Clase que maneja la lectura/escritura de eventos
import views.EventListView; // Vista que muestra la lista de eventos
import views.NewEventView; // Vista para crear un nuevo evento

/**
 * Controlador responsable del comportamiento de NewEventView
 */
public class NewEventController extends Controller // Hereda de Controller
{
    //-----------------------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------------------
    private NewEventView newEventView; // Vista asociada a la creación de eventos
    private EventListController eventListController; // Referencia al controlador de lista de eventos

    //-----------------------------------------------------------------------
    // Constructor
    //-----------------------------------------------------------------------
    /**
     * Constructor que recibe el controlador de lista de eventos.
     * Esto permite que, cuando se cree un nuevo evento,
     * se pueda actualizar la lista en EventListView.
     */
    public NewEventController(EventListController eventListController) 
    {
        this.eventListController = eventListController; // Guarda la referencia
    }

    //-----------------------------------------------------------------------
    // Métodos
    //-----------------------------------------------------------------------
    @Override
    public void run()  // Inicializa la vista de creación de eventos
    {
        newEventView = new NewEventView(this); // Crea la vista y la vincula con este controlador
    }
    
    /**
     * Crea un nuevo SchedulerEvent y lo agrega a EventListView.
     * También lo guarda en el archivo usando SchedulerIO.
     * 
     * @param event Evento que será agregado
     */
    public void addEvent(SchedulerEvent event)
    {
        // Crea un arreglo de metadatos que representará la fila en la tabla
        Object[] metadata = new Object[5];
        metadata[0] = event.getDate();        // Fecha del evento
        metadata[1] = event.getEventDesc();   // Descripción
        metadata[2] = event.getFrequency();   // Frecuencia
        metadata[3] = event.getFwdEmail();    // Email
        metadata[4] = event.getAlarm() ? "ON" : "OFF"; // Estado de la alarma (ON/OFF)

        try {
            SchedulerIO schedulerIO = new SchedulerIO(); // Crea instancia de IO
            schedulerIO.attach(newEventView); // Asocia la vista para escuchar cambios
            schedulerIO.saveEvent(event); // Guarda el evento en el archivo
        } catch (Exception e) {
            // Muestra mensaje de error si ocurre un problema al guardar
            JOptionPane.showMessageDialog(null, "ERROR", e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
        
        // Agrega la nueva fila a la tabla de eventos
        eventListController.addNewRow(metadata);
    }

    //-----------------------------------------------------------------------
    // Getters
    //-----------------------------------------------------------------------
    /**
     * Devuelve la vista asociada a este controlador.
     * @return newEventView
     */
    public NewEventView getView()
    {
        return newEventView; // Retorna la instancia de la vista
    }
}
