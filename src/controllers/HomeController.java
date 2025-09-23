package controllers; // Define que la clase pertenece al paquete controllers

import core.Controller; // Clase base Controller del framework MVC
import views.EventListView; // Vista de la lista de eventos
import views.HomeView; // Vista principal del programa
import views.NewEventView; // Vista para agregar un nuevo evento
import views.RemoveEventView; // Vista para eliminar un evento
import views.RegisterGuestView; // Vista para registrar un invitado
import views.SearchEventView; // Vista para buscar eventos

/**
 * Controlador principal. Responsable del comportamiento de la pantalla principal del programa.
 */
public class HomeController extends Controller // Clase que hereda de Controller
{
    //-----------------------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------------------
    private HomeView homeView; // Referencia a la vista principal
    private EventListController eventListController = new EventListController(); 
    // Controlador de la lista de eventos
    private NewEventController newEventController = new NewEventController(eventListController); 
    // Controlador para agregar nuevos eventos, recibe referencia de la lista
    private RemoveEventController removeEventController; // Controlador para eliminar eventos
    private RegisterGuestController registerGuestController; // Controlador para registrar invitados
    private SearchEventController searchEventController; // Controlador para buscar eventos

    //-----------------------------------------------------------------------
    // Métodos
    //-----------------------------------------------------------------------
    @Override
    public void run() // Método principal que inicializa la aplicación
    {
        // Inicializa los controladores de eventos existentes
        eventListController.run(); // Ejecuta la inicialización de la lista de eventos
        newEventController.run(); // Ejecuta la inicialización del controlador de nuevos eventos

        // Inicializa los controladores que dependen de otros o no se habían inicializado aún
        removeEventController = new RemoveEventController(eventListController); 
        // Crea el controlador de eliminación de eventos y le pasa la lista
        removeEventController.run(); // Ejecuta la inicialización del controlador de eliminación

        registerGuestController = new RegisterGuestController(); // Crea controlador de invitados
        registerGuestController.run(); // Inicializa controlador de registro de invitados

        searchEventController = new SearchEventController(eventListController); 
        // Crea controlador de búsqueda de eventos y le pasa la lista
        searchEventController.run(); // Inicializa controlador de búsqueda

        // Inicializa la vista principal y la vincula con este controlador y la ventana principal
        homeView = new HomeView(this, mainFrame); 
        addView("HomeView", homeView); // Añade la vista al registro de vistas del programa

        // Muestra la ventana principal del programa
        mainFrame.setVisible(true); 
    }

    //-----------------------------------------------------------------------
    // Getters
    //-----------------------------------------------------------------------
    public EventListView getEventListView() // Devuelve la vista de la lista de eventos
    {
        return eventListController.getView(); 
    }

    public NewEventView getNewEventView() // Devuelve la vista de agregar nuevo evento
    {
        return newEventController.getView();
    }

    public RemoveEventView getRemoveEventView() // Devuelve la vista de eliminar evento
    {
        return removeEventController.getView();
    }

    public RegisterGuestView getRegisterGuestView() // Devuelve la vista de registro de invitados
    {
        return registerGuestController.getView();
    }

    public SearchEventView getSearchEventView() // Devuelve la vista de búsqueda de eventos
    {
        return searchEventController.getView();
    }
}
