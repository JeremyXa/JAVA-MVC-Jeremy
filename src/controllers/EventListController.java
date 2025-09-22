/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import core.Controller;
import models.SchedulerIO;
import views.EventListView;

public class EventListController {
    
   
	private EventListView eventListView;
	private JTable table;
	
	
	public void run() 
	{
		table = new JTable(getDataColumns(), getNameColumns());
		eventListView = new EventListView(this, table);
	}
	
	
	public void addNewRow(Object[] values) 
	{
		((DefaultTableModel) table.getModel()).addRow(values);
	}
	
	

	public EventListView getView()
	{
		return eventListView;
	}
	
	
	public Vector<String> getNameColumns() 
	{
		Vector<String> nameColumns = new Vector<String>();
		
		nameColumns.add("Date");
		nameColumns.add("Description");
		nameColumns.add("Frequency");
		nameColumns.add("E-mail");
		nameColumns.add("Alarm");
		
		return nameColumns;
	}
	
	
	public Vector<Vector<Object>> getDataColumns() 
	{
		Vector<Vector<Object>> dataColumns = null;

		try {
			SchedulerIO schedulerIO = new SchedulerIO();
			schedulerIO.attach(eventListView);
			dataColumns = schedulerIO.getEvents();
		} catch (Exception ex) { }

		return dataColumns;
	}
    
}
