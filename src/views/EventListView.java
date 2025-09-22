package views;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import controllers.EventListController;
import core.Model;
import core.View;

@SuppressWarnings("serial")
public class EventListView extends JPanel implements View
{
	
	@SuppressWarnings("unused")
	private EventListController eventListController;
	private JTable table;
	
	
	
	
	public EventListView(EventListController eventListController, JTable table)
	{
		this.eventListController = eventListController;
		this.table = table;
		
		make_frame();
	}
	
	
	
	@Override
	public void update(Model model, Object data) 
	{
		if (data != null) {
			String notice = (String) data;
			JOptionPane.showMessageDialog(this, notice);
		}
	}
	

	private void make_frame()
	{
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);
	}
}