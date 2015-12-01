package beatprogramming.github.com.teacker_tracker.domain;

import java.io.Serializable;

/**
 * Created by malkomich on 27/11/15.
 */
public class Score implements Serializable {

    private float calificacion;
    private String comentario;

    private Student student;

    public Score() {

    }

    public Score(float calificacion, String comentario, Student student) {
        this.calificacion = calificacion;
        this.comentario = comentario;
        this.student = student;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
