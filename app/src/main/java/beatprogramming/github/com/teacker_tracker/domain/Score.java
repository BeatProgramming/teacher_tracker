package beatprogramming.github.com.teacker_tracker.domain;

import java.io.Serializable;

/**
 * Clase que modela una puntuación de la base de datos.
 */
public class Score implements Serializable {

    private Float calificacion;
    private String comentario;
    private Student student;
    private Review review;

    public Score() {

    }

    public Score(Float calificacion, String comentario, Student student, Review review) {
        this.calificacion = calificacion;
        this.comentario = comentario;
        this.student = student;
        this.review = review;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Float calificacion) {
        this.calificacion = calificacion;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    @Override
    public String toString() {
        return ((student != null) ? student.toString() : "New student") + " (" + ((review != null) ? review.toString() : "New review") + ") --> " + calificacion;
    }

    public int getReviewId() {
        return review.getId();
    }

    public int getStudentId() {
        return student.getId();
    }
}
