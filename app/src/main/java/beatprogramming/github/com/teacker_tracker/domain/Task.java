package beatprogramming.github.com.teacker_tracker.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Adrian on 07/11/2015.
 */
public class Task implements Serializable {

    private int id;
    private Subject subject;
    private String hora;
    private List<String> notas;

    public Task() {

    }

    public Task(Subject tarea, String hora){
        this.subject =tarea;
        this.hora=hora;
    }

    public void addNota(String nota){
        this.notas.add(nota);
    }

    public List<String> getNotas() {
        return notas;
    }

    public void setNotas(List<String> notas) {
        this.notas = notas;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }
}
