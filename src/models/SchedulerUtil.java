/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;  // Paquete donde se encuentra la clase

import java.util.Calendar; // Clase para manejar fechas de forma más flexible
import java.util.Date;     // Clase estándar de Java para representar fechas

// Clase de utilidades relacionadas con el manejo de fechas
public class SchedulerUtil 
{
    // Método estático que convierte un String con formato "dd/MM/yyyy" en un objeto Date
    public static Date getDateFromString(String dateStr)
    {
        // Obtiene una instancia de Calendar con la fecha/hora actual
        Calendar date = Calendar.getInstance();
        
        // Extrae el año de la cadena (caracteres del 6 al 10, ej: "2025")
        date.set(Calendar.YEAR, Integer.parseInt(dateStr.substring(6, 10)));

        // Extrae el mes de la cadena (caracteres del 3 al 5, ej: "09" para septiembre)
        // OJO: Calendar.MONTH empieza en 0 (enero = 0), aquí no se resta 1 → posible bug.
        date.set(Calendar.MONTH, Integer.parseInt(dateStr.substring(3, 5)));

        // Extrae el día de la cadena (caracteres del 0 al 2, ej: "23")
        date.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateStr.substring(0, 2)));
           
        // Retorna un objeto Date con la fecha configurada
        return date.getTime();
    }
}
