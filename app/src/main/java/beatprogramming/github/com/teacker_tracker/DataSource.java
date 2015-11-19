package beatprogramming.github.com.teacker_tracker;

/**
 * Created by Adrian on 07/11/2015.
 */
import java.util.ArrayList;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.domain.Task;


public class DataSource {
    final static List TASK = new ArrayList<Task>();

    static{

        TASK.add(new Task("Lengua 1º ESO","08:00", "A101"));
        TASK.add(new Task("Lengua 2º ESO","10:00", "A003"));
        TASK.add(new Task("Matematica 4º ESO","13:00", "A104"));
        TASK.add(new Task("Ciencias sociales 1º ESO","16:00", "A102"));
        TASK.add(new Task("Ciencias sociales 1º ESO","17:00", "A102"));

    }
}
