package beatprogramming.github.com.teacker_tracker;

/** Clase que simula una base de datos para las asignaturas creadas por un usuario
 *
 * Created by Adrian on 07/11/2015.
 */

import java.util.ArrayList;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.domain.Subject;
import beatprogramming.github.com.teacker_tracker.domain.Task;


public class DataSource {

    public final static List TASK = new ArrayList<Task>();
    public final static List SUBJECT = new ArrayList<Subject>();

    static{

        TASK.add(new Task(new Subject("Lengua", "2º ESO", "A101"),"08:00"));
        TASK.add(new Task(new Subject("Lengua", "2º ESO", "A101"),"10:00"));
        TASK.add(new Task(new Subject("Matemáticas", "1º ESO", "A103"), "12:00"));
        TASK.add(new Task(new Subject("Filosofía", "2º ESO", "A106"),"16:00"));
        TASK.add(new Task(new Subject("Filosofía", "2º ESO", "A106"),"17:00"));

    }

    static{

        SUBJECT.add(new Subject("Lengua", "2º ESO", "A101"));
        SUBJECT.add(new Subject("Matemáticas", "1º ESO", "A103"));
        SUBJECT.add(new Subject("Filosofía", "2º ESO", "A106"));

    }
}
