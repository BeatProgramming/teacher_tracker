package beatprogramming.github.com.teacker_tracker.domain;

/**
 * Created by malkomich on 27/11/15.
 */
public class Score {
    private float calificacion;
    private String comentario;

    public Score(float calificacion, String comentario) {
        this.calificacion = calificacion;
        this.comentario = comentario;
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
}
