package beatprogramming.github.com.teacker_tracker.domain;

import java.io.Serializable;

/**
 * Clase que modela una puntuación de la base de datos.
 */
public class Score implements Serializable {

    private int id;
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

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }
}
