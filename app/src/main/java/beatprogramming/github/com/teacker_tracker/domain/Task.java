package beatprogramming.github.com.teacker_tracker.domain;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * Clase que modela una tarea de la base de datos.
 */
public class Task implements Serializable {

    private int id;
    private String description;
    private Subject subject;
    private DateTime dateTime;
    private String note;

    public Task() {

    }

    public Task(String name, Subject subject, DateTime dateTime) {
        this.subject = subject;
        this.description = description;
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }
}
