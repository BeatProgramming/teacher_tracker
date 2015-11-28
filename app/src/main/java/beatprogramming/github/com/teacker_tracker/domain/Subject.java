package beatprogramming.github.com.teacker_tracker.domain;

/**
 * Created by malkomich on 27/11/15.
 */
public class Subject {

    private String curso;
    private String nombre;
    private String aula;

    public Subject(String nombre, String curso, String aula) {
        this.curso=curso;
        this.nombre=nombre;
        this.aula=aula;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString(){
        return this.nombre + " " + this.curso;
    }
}
