package beatprogramming.github.com.teacker_tracker;

/**
 * Created by Adrian on 07/11/2015.
 */
import java.util.ArrayList;
import java.util.List;



public class DataSource {
    static List TAREAS = new ArrayList<Tarea>();

    static{

        TAREAS.add(new Tarea("Trotar 30 minutos","08:00"));
        TAREAS.add(new Tarea("Estudiar análisis técnico","10:00"));
        TAREAS.add(new Tarea("Comer 4 rebanadas de manzana","10:30"));
        TAREAS.add(new Tarea("Asistir al taller de programación gráfica","15:45"));
        TAREAS.add(new Tarea("Consignarle a Marta","18:00"));

    }
}
