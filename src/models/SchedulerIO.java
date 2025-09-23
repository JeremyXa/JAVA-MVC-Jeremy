package models; // Paquete donde se ubica esta clase

// Importaciones necesarias para leer/escribir archivos y manejar colecciones
import java.io.BufferedReader;  
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import core.Model; // Interfaz base de los modelos
import core.View;  // Interfaz base de las vistas

// Clase encargada de manejar la persistencia de eventos en un archivo (entrada/salida)
public class SchedulerIO implements Model {

    // ----------------------------
    // Constantes de ubicación de archivo
    // ----------------------------
    private static final String DIRECTORY = ".";        // Carpeta actual del proyecto
    private static final String FILE = "events.txt";    // Nombre del archivo donde se guardan los eventos

    // ----------------------------
    // Atributos
    // ----------------------------
    private List<View> views = new ArrayList<>(); // Lista de vistas observadoras (patrón Observer)
    private String notice;                        // Mensaje de notificación para las vistas

    // ----------------------------
    // Métodos del patrón Observer
    // ----------------------------
    @Override
    public void attach(View view) { // Registra una vista para escuchar cambios
        views.add(view);
    }

    @Override
    public void detach(View view) { // Quita una vista de la lista
        views.remove(view);
    }

    @Override
    public void notifyViews() { // Notifica a todas las vistas registradas
        for (View v : views) {
            v.update(this, notice); // Llama al método update de la vista
        }
    }

    // ----------------------------
    // Guardar un evento en el archivo
    // ----------------------------
    /**
     * Guarda un evento en el archivo.
     * @param event Evento a guardar.
     */
    public void saveEvent(SchedulerEvent event) throws Exception {
        try {
            // Abre el archivo en modo append (agregar al final)
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(DIRECTORY, FILE), true));
            writer.write(event.toString(), 0, event.toString().length()); // Escribe el evento como string
            writer.newLine(); // Salto de línea
            writer.close();   // Cierra el escritor
        } catch (FileNotFoundException fnfe) { // Si no se encuentra el archivo
            notice = "File not found";
            notifyViews();
        } catch (Exception ex) { // Cualquier otro error
            notice = "Error while writing the file";
            notifyViews();
        }
    }

    // ----------------------------
    // Obtener todos los eventos del archivo
    // ----------------------------
    /**
     * Obtiene la lista completa de eventos del archivo.
     * @return Vector con la información de los eventos.
     */
    public Vector<Vector<Object>> getEvents() throws Exception {
        Vector<Vector<Object>> response = new Vector<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(DIRECTORY, FILE)));
            String line = reader.readLine(); // Lee la primera línea

            while (line != null) { // Mientras haya datos
                Vector<Object> eventInfo = new Vector<>();
                String[] tokens = line.split(";"); // Divide la línea en partes separadas por ";"

                eventInfo.add(tokens[0]);                       // Fecha
                eventInfo.add(tokens[1]);                       // Descripción
                eventInfo.add(Frequency.valueOf(tokens[2]));    // Frecuencia (enum)
                eventInfo.add(tokens[3]);                       // Email
                eventInfo.add(tokens[4].equals("1") ? "ON" : "OFF"); // Alarma ON/OFF

                response.add(eventInfo); // Agrega el evento al vector de respuesta
                line = reader.readLine(); // Lee la siguiente línea
            }

            reader.close();
        } catch (FileNotFoundException fnfe) {
            notice = "File not found";
            notifyViews();
        } catch (Exception ex) {
            notice = "There was a problem reading the event file";
            notifyViews();
        }

        return response; // Devuelve todos los eventos leídos
    }

    // ----------------------------
    // Eliminar un evento del archivo
    // ----------------------------
    /**
     * Elimina un evento de la lista y actualiza el archivo.
     * @param eventToRemove Evento que se desea eliminar.
     */
    public void removeEvent(SchedulerEvent eventToRemove) throws IOException {
        try {
            Vector<SchedulerEvent> allEvents = readAllEvents(); // Lee todos los eventos del archivo
            
            // Elimina el evento que coincide con el recibido
            allEvents.removeIf(event -> event.equals(eventToRemove));

            // Reescribe el archivo con los eventos restantes
            writeAllEvents(allEvents);

            notice = "Evento eliminado con éxito.";
        } catch (FileNotFoundException fnfe) {
            notice = "File not found";
        } catch (Exception ex) {
            notice = "Error al eliminar el evento: " + ex.getMessage();
        }
        notifyViews(); // Notifica a las vistas sobre el cambio
    }
    
    // ----------------------------
    // Leer todos los eventos como objetos SchedulerEvent
    // ----------------------------
    private Vector<SchedulerEvent> readAllEvents() throws IOException, java.text.ParseException {
        Vector<SchedulerEvent> allEvents = new Vector<>();
        BufferedReader reader = new BufferedReader(new FileReader(new File(DIRECTORY, FILE)));
        String line = reader.readLine();

        while (line != null) {
            String[] tokens = line.split(";");
            
            // Validación: debe haber 5 tokens (fecha, desc, freq, email, alarma)
            if (tokens.length == 5) {
                allEvents.add(new SchedulerEvent(
                    tokens[0], // Fecha
                    tokens[1], // Descripción
                    tokens[2], // Frecuencia
                    tokens[3], // Email
                    tokens[4].equals("1") // Alarma (boolean)
                ));
            }
            line = reader.readLine(); // Pasa a la siguiente línea
        }
        reader.close();
        return allEvents;
    }
    
    // ----------------------------
    // Escribir todos los eventos en el archivo (sobrescribir)
    // ----------------------------
    private void writeAllEvents(Vector<SchedulerEvent> eventsToSave) throws IOException {
        // Sobrescribe el archivo (false indica sobreescribir, no append)
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(DIRECTORY, FILE), false));
        for (SchedulerEvent event : eventsToSave) {
            writer.write(event.toString()); // Escribe el evento
            writer.newLine();               // Salto de línea
        }
        writer.close(); // Cierra el archivo
    }
}
