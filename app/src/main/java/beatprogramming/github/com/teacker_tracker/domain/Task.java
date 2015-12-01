package beatprogramming.github.com.teacker_tracker.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Adrian on 07/11/2015.
 */
public class Task implements Serializable {
    private Subject tarea;
    private String hora;
    private List<String> notas;

    public Task() {

    }

    public Task(Subject tarea, String hora){
        this.tarea=tarea;
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

    public Subject getTarea() {
        return tarea;
    }

    public void setTarea(Subject tarea) {
        this.tarea = tarea;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
