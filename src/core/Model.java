/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package core;


public interface Model 
{
	
	public void attach(View view);
	//Sirve para conectar una vista al modelo.
	
	public void detach(View view);
	//Sirve para desconectar una vista del modelo.

	public void notifyViews();
       //Se encarga de avisar a todas las vistas conectadas que hubo un cambio en los datos.
}