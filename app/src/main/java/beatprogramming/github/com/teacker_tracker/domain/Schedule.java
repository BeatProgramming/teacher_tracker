package beatprogramming.github.com.teacker_tracker.domain;

import org.joda.time.DateTime;

/**
 * Clase que modela un horario.
 */
public class Schedule {

    private int id;
    private Subject subject;
    private DateTime dateTime;
    private Boolean[] dias;
    private String aula;


    public Schedule(Subject subject, DateTime dateTime, Boolean[] dias, String aula) {
        this.subject = subject;
        this.dateTime = dateTime;
        this.aula = aula;
        this.dias=dias;
    }

    public Boolean[] getDias() {
        return dias;
    }

    public void setDias(Boolean[] dias) {
        this.dias = dias;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}