package beatprogramming.github.com.teacker_tracker.domain;

import java.io.Serializable;

/**
 * Created by malkomich on 27/11/15.
 */
public class Subject implements Serializable {

    private int id;
    private String nombre;
    private String descripcion;
    private String curso;
    private String aula;

    public Subject() {

    }

    public Subject(String nombre, String descripcion, String curso, String aula) {
        this.nombre=nombre;
        this.descripcion = descripcion;
        this.curso=curso;
        this.aula=aula;
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

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return this.nombre + " " + this.curso;
    }
}