/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core;

import java.awt.CardLayout;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class Controller 
{	
	//-----------------------------------------------------------------------
	//		Attributos
	//-----------------------------------------------------------------------
	
	protected static final JFrame mainFrame = new JFrame();
	
	
	private static final JPanel viewsViewer = new JPanel(new CardLayout());
		
	
	private static final Map<String,Component> mainFrameComponents = new HashMap<>();
	
	
	//-----------------------------------------------------------------------
	//		Initialization blocks
	//-----------------------------------------------------------------------
	
	{
		mainFrame.add(viewsViewer);
	}
	
        
        
        
        
        
        
        
	
	//-----------------------------------------------------------------------
	//		Methods
	//-----------------------------------------------------------------------
	
        
        
        
        
        
	public abstract void run();
	
	
	
        
        
        
        
        
	public static final void addView(String viewName, View view)
	{
		viewsViewer.add((Component)view, viewName);
	}
	
	
        
        
        
        
        
        
        
	public static final void loadView(String viewName)
	{
		CardLayout cl = (CardLayout)viewsViewer.getLayout();
		cl.show(viewsViewer, viewName);
	}

	
        
        
        
        
        
        
        
	
	public static final void addComponent(String name, Component component)
	{
		mainFrameComponents.put(name, component);
	}
	
	
        
        
        
        
        
        
	public static final void removeComponent(String name)
	{
		mainFrameComponents.remove(name);
	}
	
	
        
        
        
        
        
	public static final Component getComponent(String name)
	{
		return mainFrameComponents.get(name);
	}
}
