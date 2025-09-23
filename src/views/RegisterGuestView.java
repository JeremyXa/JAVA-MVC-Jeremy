package views;

import controllers.RegisterGuestController;
import core.Model;
import core.View;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class RegisterGuestView extends JPanel implements View {

    private RegisterGuestController controller;
    private JTextField nameField, phoneField, addressField;
    private JRadioButton maleButton, femaleButton;
    private JCheckBox termsCheckBox;
    private JButton acceptButton; // Nuevo botón de aceptar

    public RegisterGuestView(RegisterGuestController controller) {
        this.controller = controller;
        setLayout(new GridBagLayout()); // Usamos GridBagLayout para un mejor control del diseño
        make_frame();
    }

    private void make_frame() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espacio entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label y campo de texto para el nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Ingrese Nombre:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        add(nameField, gbc);

        // Label y campo de texto para el número de celular
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Ingrese número celular:"), gbc);
        gbc.gridx = 1;
        phoneField = new JTextField(20);
        add(phoneField, gbc);

        // Sección de género
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Género:"), gbc);
        JPanel genderPanel = new JPanel();
        maleButton = new JRadioButton("Masculino");
        femaleButton = new JRadioButton("Femenino");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        maleButton.setSelected(true);
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        gbc.gridx = 1;
        add(genderPanel, gbc);

        // Sección de fecha de nacimiento
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Fecha de Nacimiento:"), gbc);
        JPanel datePanel = new JPanel();
        // Aquí podrías usar JComboBox para el día, mes y año como en la imagen
        // Por simplicidad, se usa un placeholder
        datePanel.add(new JLabel("[Fecha de Nacimiento]"));
        gbc.gridx = 1;
        add(datePanel, gbc);

        // Label y campo de texto para la dirección
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 1;
        addressField = new JTextField(20);
        add(addressField, gbc);
        
        // Casilla de verificación de "Acepta Términos y Condiciones"
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2; // Ocupa 2 columnas
        termsCheckBox = new JCheckBox("Acepta Términos y Condiciones");
        add(termsCheckBox, gbc);

        // Botón "Aceptar"
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        acceptButton = new JButton("Aceptar");
        acceptButton.addActionListener(e -> {
            if (termsCheckBox.isSelected()) {
                JOptionPane.showMessageDialog(this, "Términos y condiciones aceptados.", "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Debe aceptar los términos y condiciones.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(acceptButton, gbc);
    }
    
    @Override
    public void update(Model model, Object data) {
        // Lógica para actualizar la vista (si es necesaria)
    }
}