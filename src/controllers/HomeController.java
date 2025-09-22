/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import core.Controller;
import views.EventListView;
import views.HomeView;
import views.NewEventView;


public class HomeController extends Controller {
    private HomeView homeView;
    private EventListController eventListController = new EventListController();
    private NewEventController newEventController = new NewEventController(eventListController);
    
    
    

	public void run()
	{
		// Initializes others controllers
		eventListController.run();
		newEventController.run();
		
		// Initializes HomeView
		homeView = new HomeView(this, mainFrame);
		addView("HomeView", homeView);
		
		// Displays the program window
		mainFrame.setVisible(true);
	}
	
	
	//-----------------------------------------------------------------------
	//		Getters
	//-----------------------------------------------------------------------
	public EventListView getEventListView()
	{
		return eventListController.getView();
	}
	
	public NewEventView getNewEventView()
	{
		return newEventController.getView();
	}
}