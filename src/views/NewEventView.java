package views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import controllers.NewEventController;
import core.Model;
import core.View;
import models.Frequency;
import models.SchedulerEvent;
import models.SchedulerUtil;

/**
 * View responsible for the creation of a new event.
 * Esta vista permite al usuario ingresar todos los datos de un evento:
 * descripción, email, fecha, frecuencia y alarma.
 */
@SuppressWarnings("serial")
public class NewEventView extends JPanel implements View
{
	//-----------------------------------------------------------------------
	//		Attributes
	//-----------------------------------------------------------------------
	private NewEventController newEventController;	
	private JTextField tf_eventDesc;        // Campo para descripción del evento
	private JTextField tf_forwardEmail;     // Campo para email de reenvío
	private JFormattedTextField tf_date;    // Campo para fecha con máscara dd/MM/yyyy
	private JCheckBox cbx_alarm;            // Checkbox para activar alarma
	private JRadioButton rbtn_monthly;      // Opción de frecuencia mensual
	private JRadioButton rbtn_weekly;       // Opción de frecuencia semanal
	private JRadioButton rbtn_daily;        // Opción de frecuencia diaria
	
	
	//-----------------------------------------------------------------------
	//		Constructor
	//-----------------------------------------------------------------------
	/**
	 * Constructor que inicializa los elementos de la vista y recibe el controlador.
	 * @param newEventController controlador asociado a esta vista
	 */
	public NewEventView(NewEventController newEventController) 
	{
		this.newEventController = newEventController;
		
		make_frame();              // Define layout base
		make_field_eventDesc();    // Campo descripción del evento
		make_field_fwdEmail();     // Campo email
		make_field_date();         // Campo fecha
		make_field_frequency();    // Opciones de frecuencia
		make_field_alarm();        // Checkbox de alarma
		make_btn_save();           // Botón guardar
		make_btn_clean();          // Botón limpiar
	}

	
	//-----------------------------------------------------------------------
	//		Methods
	//-----------------------------------------------------------------------
	@Override
	public void update(Model model, Object data) 
	{
		// Muestra un mensaje en pantalla si el controlador envía una notificación
		if (data != null) {
			String notice = (String) data;
			JOptionPane.showMessageDialog(null, notice);
		}
	}
	
	/**
	 * Resetea todos los campos de la vista a su estado inicial.
	 */
	private void cleanFields() 
	{
		tf_date.setText("");				// Borra campo fecha
		tf_eventDesc.setText("");			// Borra campo descripción
		cbx_alarm.setSelected(false);		// Deselecciona alarma
		tf_forwardEmail.setText("");		// Borra campo email
		rbtn_daily.setSelected(true);		// Frecuencia por defecto: diaria
	}
	
	/**
	 * Crea el frame principal de la vista (layout nulo para posicionamiento manual).
	 */
	private void make_frame() { setLayout(null); }
	
	/**
	 * Crea el campo de texto para la descripción del evento.
	 */
	private void make_field_eventDesc()
	{
		JLabel lbl_eventDesc = new JLabel("Event description");
		lbl_eventDesc.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbl_eventDesc.setBounds(29, 29, 134, 14);
		add(lbl_eventDesc);
		
		tf_eventDesc = new JTextField();
		tf_eventDesc.setBounds(169, 26, 196, 20);
		add(tf_eventDesc);
		tf_eventDesc.setColumns(10);
	}
	
	/**
	 * Crea el campo de texto para el email de reenvío.
	 */
	private void make_field_fwdEmail()
	{
		JLabel lbl_forwardEmail = new JLabel("Forward e-mail");
		lbl_forwardEmail.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbl_forwardEmail.setBounds(29, 71, 104, 14);
		add(lbl_forwardEmail);

		tf_forwardEmail = new JTextField();
		tf_forwardEmail.setBounds(169, 68, 196, 20);
		add(tf_forwardEmail);
		tf_forwardEmail.setColumns(10);
	}
	
	/**
	 * Crea el campo de texto formateado para ingresar la fecha (dd/MM/yyyy).
	 */
	private void make_field_date()
	{
		JLabel lbl_date = new JLabel("Date");
		lbl_date.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbl_date.setBounds(29, 119, 78, 14);
		add(lbl_date);

		try {
			tf_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
			tf_date.setBounds(169, 116, 96, 20);
			add(tf_date);
			tf_date.setColumns(10);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Crea los botones de selección de frecuencia (diaria, semanal, mensual).
	 */
	private void make_field_frequency()
	{
		final ButtonGroup btng_periodicity = new ButtonGroup();
		
		JLabel lbl_frequency = new JLabel("Frequency");
		lbl_frequency.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbl_frequency.setBounds(29, 164, 78, 14);
		add(lbl_frequency);
		
		// Opción diaria
		rbtn_daily = new JRadioButton("Daily");
		btng_periodicity.add(rbtn_daily);
		rbtn_daily.setSelected(true);
		rbtn_daily.setBounds(169, 160, 60, 23);
		add(rbtn_daily);

		// Opción semanal
		rbtn_weekly = new JRadioButton("Weekly");
		btng_periodicity.add(rbtn_weekly);
		rbtn_weekly.setBounds(253, 160, 67, 23);
		add(rbtn_weekly);

		// Opción mensual
		rbtn_monthly = new JRadioButton("Monthly");
		btng_periodicity.add(rbtn_monthly);
		rbtn_monthly.setBounds(347, 160, 78, 23);
		add(rbtn_monthly);
	}
	
	/**
	 * Crea el checkbox para activar o desactivar alarma en el evento.
	 */
	private void make_field_alarm()
	{
		cbx_alarm = new JCheckBox("Alarm");
		cbx_alarm.setBounds(29, 220, 97, 23);
		add(cbx_alarm);
	}
	
	/**
	 * Crea el botón Save que construye un objeto SchedulerEvent con los datos ingresados
	 * y lo envía al controlador para guardarlo.
	 */
	private void make_btn_save()
	{
		JButton btn_save = new JButton("Save");
		btn_save.setBounds(127, 220, 89, 23);
		add(btn_save);

		btn_save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				// Validación básica de campos obligatorios
				if (tf_eventDesc.getText().isEmpty() || tf_date.getText().trim().equals("//")) {
					JOptionPane.showMessageDialog(null, "Please complete all fields.");
					return;
				}
				
				// Crear evento y asignar datos desde la vista
				SchedulerEvent event = new SchedulerEvent();
				event.setDate(SchedulerUtil.getDateFromString(tf_date.getText()));
				event.setEventDesc(tf_eventDesc.getText());
				event.setAlarm(cbx_alarm.isSelected());
				event.setFwdEmail(tf_forwardEmail.getText());
				
				if (rbtn_daily.isSelected()) {
					event.setFrequency(Frequency.DAILY);
				} else if (rbtn_weekly.isSelected()) {
					event.setFrequency(Frequency.WEEKLY);
				} else {
					event.setFrequency(Frequency.MONTHLY);
				}
				
				// Enviar evento al controlador
				newEventController.addEvent(event);
				
				// Mensaje de confirmación
				JOptionPane.showMessageDialog(null, "Event saved successfully!");
				
				// Limpiar campos
				cleanFields();
			}
		});
	}
	
	/**
	 * Crea el botón Clean que limpia todos los campos de la vista.
	 */
	private void make_btn_clean()
	{
		JButton btn_clean = new JButton("Clean");
		btn_clean.setBounds(253, 220, 89, 23);
		add(btn_clean);

		btn_clean.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cleanFields();
			}
		});
	}
}
