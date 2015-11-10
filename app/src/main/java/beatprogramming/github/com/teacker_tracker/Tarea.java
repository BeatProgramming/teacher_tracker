package beatprogramming.github.com.teacker_tracker;

/**
 * Created by Adrian on 07/11/2015.
 */
public class Tarea {
    private String nombre;
    private String aux;
    private String hora;

    public Tarea(String nombre, String hora, String aux){

        this.nombre = nombre;
        this.aux = aux;
        this.hora=hora;

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
    public String toString(){return nombre+","+aux;}
}
