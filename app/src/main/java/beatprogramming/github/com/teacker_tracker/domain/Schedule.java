package beatprogramming.github.com.teacker_tracker.domain;

import org.joda.time.DateTime;

/**
 * Clase que modela un horario.
 */
public class Schedule {

    private Subject subject;
    private DateTime dateTime;
    private String aula;


    public Schedule(Subject subject, DateTime dateTime, String aula) {
        this.subject = subject;
        this.dateTime = dateTime;
        this.aula = aula;
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

}