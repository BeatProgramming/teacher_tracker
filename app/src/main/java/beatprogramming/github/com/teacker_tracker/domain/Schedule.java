package beatprogramming.github.com.teacker_tracker.domain;

import java.io.Serializable;

/**
 * Clase que modela un horario.
 */
public class Schedule implements Serializable{

    private int id;
    private Subject subject;
    private String dateTime;
    private Boolean[] dias;
    private String aula;


    public Schedule(Subject subject, String dateTime, Boolean[] dias, String aula) {
        this.subject = subject;
        this.dateTime = dateTime;
        this.aula = aula;
        this.dias=dias;
    }

    public Boolean[] getDias() {
        return dias;
    }


    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getAula() {
        return aula;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}