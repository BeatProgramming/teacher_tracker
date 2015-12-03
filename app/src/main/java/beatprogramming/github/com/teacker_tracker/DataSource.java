package beatprogramming.github.com.teacker_tracker;

/** Clase que simula una base de datos para las asignaturas creadas por un usuario
 *
 */
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.domain.Task;


public class DataSource {

    public final static List TASK = new ArrayList<Task>();
    public final static String ID = "_id";

    static{


        TASK.add(new Task("Lengua", "2º ESO","08:00", "A101"));
        TASK.add(new Task("Lengua", "2º ESO","10:00", "A003"));
        TASK.add(new Task("Matemáticas", "1º ESO","13:00", "A104"));
        TASK.add(new Task("Filosofía", "2º ESO","16:00", "A102"));
        TASK.add(new Task("Filosofía", "2º ESO","17:00", "A102"));

    }


}
