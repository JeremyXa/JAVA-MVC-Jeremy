package controllers;

import core.Controller;
import views.RegisterGuestView;
import javax.swing.JOptionPane;

/**
 * Controller responsible for the behavior of RegisterGuestView.
 */
public class RegisterGuestController extends Controller {

    private RegisterGuestView registerGuestView;

    @Override
    public void run() {
        registerGuestView = new RegisterGuestView(this);
    }

    /**
     * Gets the view associated with this controller.
     * @return The RegisterGuestView instance.
     */
    public RegisterGuestView getView() {
        return registerGuestView;
    }

    /**
     * Handles the guest registration logic.
     * This method would contain the business logic, such as validating fields,
     * creating a guest object, and saving it to a data source (e.g., a file or database).
     */
    public void registerGuest() {
        // Here you would add the logic to get data from the view and save it.
        // For now, it just shows a confirmation message.
        // This is where you would call a Model class to handle the data.
        
        // Example:
        // String name = registerGuestView.getName();
        // String phone = registerGuestView.getPhone();
        // ...
        
        JOptionPane.showMessageDialog(registerGuestView, "Invitado registrado con éxito.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
    }
}