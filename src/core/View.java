package core;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author jerem
 */
public interface View 
{	
//Indica qué debe hacer la vista cuando el modelo le avisa que hubo un cambio.
    
  
    
	public void update(Model model, Object data);
}