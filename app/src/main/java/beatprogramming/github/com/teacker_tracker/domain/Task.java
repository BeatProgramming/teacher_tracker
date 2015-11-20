package beatprogramming.github.com.teacker_tracker.domain;

/**
 * Created by Adrian on 07/11/2015.
 */
public class Task {
    private String nombre;
    private String curso;
    private String aux;
    private String hora;

    public Task(String nombre, String curso, String hora, String aux){

        this.nombre = nombre;
        this.aux = aux;
        this.hora=hora;
        this.curso=curso;

    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public void setHora(String hora){
        this.hora = hora;}

    public void setAux(String aux){
        this.aux=aux;
    }
    public String getNombre(){return nombre;}
    public String getHora(){return hora;}
    public String getAux() {return aux;}

    @Override
    public String toString(){return nombre+" "+curso;}
}
