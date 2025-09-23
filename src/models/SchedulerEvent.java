package models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.text.ParseException;

public class SchedulerEvent {

    private String eventDesc;
    private String fwdEmail;
    private Date date;
    private Frequency frequency;
    private boolean alarm;

    // Constructor vacío por defecto
    public SchedulerEvent() {
    }

    // Nuevo constructor para crear un evento a partir de los datos de la tabla
    public SchedulerEvent(String dateString, String description, String frequencyString, String email, boolean alarm) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        try {
            this.date = sdf.parse(dateString);
        } catch (ParseException e) {
            System.err.println("Error al parsear la fecha: " + e.getMessage());
            this.date = null;
        }
        this.eventDesc = description;
        this.frequency = Frequency.valueOf(frequencyString.toUpperCase());
        this.fwdEmail = email;
        this.alarm = alarm;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        return sdf.format(this.date) + ";" + getEventDesc()
                + ";" + getFrequency() + ";" + getFwdEmail() + ";"
                + (getAlarm() == true ? "1" : "0");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SchedulerEvent that = (SchedulerEvent) obj;
        return alarm == that.alarm &&
               Objects.equals(eventDesc, that.eventDesc) &&
               Objects.equals(fwdEmail, that.fwdEmail) &&
               Objects.equals(date, that.date) &&
               frequency == that.frequency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventDesc, fwdEmail, date, frequency, alarm);
    }
    
    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String descEvento) {
        this.eventDesc = descEvento;
    }

    public String getFwdEmail() {
        return fwdEmail;
    }

    public void setFwdEmail(String encEmail) {
        this.fwdEmail = encEmail;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date data) {
        this.date = data;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public boolean getAlarm() {
        return alarm;
    }

    public void setAlarm(boolean alarme) {
        this.alarm = alarme;
    }
}