package views; // Paquete donde está ubicada la clase

// Importaciones necesarias para UI
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import controllers.HomeController; // Controlador principal
import core.Model;  // Parte del patrón MVC
import core.View;   // Parte del patrón MVC

// Anotación para ignorar advertencias de serialización
@SuppressWarnings("serial")
// Clase HomeView → Representa la vista principal con pestañas
public class HomeView extends JPanel implements View
{
	//-----------------------------------------------------------------------
	//		Attributes (Atributos)
	//-----------------------------------------------------------------------
	@SuppressWarnings("unused")
	private HomeController homeController; // Referencia al controlador principal
	private JFrame mainFrame;              // Ventana principal de la aplicación
	
	// Constantes para tamaño y posición de la ventana
	private final static int MAIN_FRAME_WIDTH = 500;
	private final static int MAIN_FRAME_HEIGHT = 350;
	private final static int MAIN_FRAME_X = 100;
	private final static int MAIN_FRAME_Y = 100;
	
	//-----------------------------------------------------------------------
	//		Constructor
	//-----------------------------------------------------------------------
	public HomeView(HomeController homeController, JFrame mainFrame)
	{
		// Asigna referencias al controlador y al frame principal
		this.homeController = homeController;
		this.mainFrame = mainFrame;
		
		make_mainFrame(); // Configura las propiedades del frame
		make_tabs();      // Crea y agrega las pestañas de navegación
	}
	
	//-----------------------------------------------------------------------
	//		Methods
	//-----------------------------------------------------------------------
	
	// Método de actualización (parte del patrón MVC)
	// Por ahora vacío, ya que esta vista no necesita responder a cambios del modelo
	@Override
	public void update(Model model, Object data) 
	{}
	
	// Configura el JFrame principal
	private void make_mainFrame()
	{
		mainFrame.setOpacity(1.0f); // Hace la ventana totalmente opaca
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra el programa al salir
		mainFrame.setBounds(MAIN_FRAME_X, MAIN_FRAME_Y, MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT); // Tamaño y posición inicial
		mainFrame.setMinimumSize(new Dimension(MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT)); // Tamaño mínimo
		
		// Configuración visual del panel principal
		setBorder(new EmptyBorder(5, 5, 5, 5));      // Bordes internos de 5px
		setLayout(new BorderLayout(0, 0));           // Layout principal tipo BorderLayout
	}
	
	/**
	 * Crea la navegación por pestañas con todas las vistas del sistema
	 */
	private void make_tabs()
	{
		// Crea un contenedor de pestañas (ubicado en la parte superior)
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		// Agrega cada vista como una pestaña, obtenidas desde el controlador
		tabbedPane.addTab("New event", homeController.getNewEventView());         // Crear nuevo evento
		tabbedPane.addTab("Events", homeController.getEventListView());           // Listar eventos
		
		// Pestañas adicionales agregadas
		tabbedPane.addTab("Remove Event", homeController.getRemoveEventView());   // Eliminar evento
		tabbedPane.addTab("Registrar invitado", homeController.getRegisterGuestView()); // Registrar invitado
		tabbedPane.addTab("Buscar evento", homeController.getSearchEventView());  // Buscar evento
		
		// Agrega el panel de pestañas al centro del layout principal
		add(tabbedPane, BorderLayout.CENTER);
	}
}
