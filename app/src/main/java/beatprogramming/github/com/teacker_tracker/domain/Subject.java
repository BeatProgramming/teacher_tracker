package beatprogramming.github.com.teacker_tracker.domain;

import java.io.Serializable;

/**
 * Clase que modela una asignatura de la base de datos.
 */
public class Subject implements Serializable {

    private int id;
    private String nombre;
    private String descripcion;
    private String curso;

    public Subject() {

    }

    public Subject(String nombre, String descripcion, String curso) {
        this.nombre=nombre;
        this.descripcion = descripcion;
        this.curso=curso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return nombre + ", " + curso;
    }
}
