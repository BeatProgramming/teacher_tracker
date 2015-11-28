package beatprogramming.github.com.teacker_tracker.domain;

/**
 * Created by malkomich on 27/11/15.
 */
public class Project extends Review{

    private String descripcion;

    public Project(Subject subject, String descripcion) {
        super(subject);
        this.descripcion=descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
