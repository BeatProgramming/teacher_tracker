package beatprogramming.github.com.teacker_tracker.persistence;

import java.util.GregorianCalendar;

import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;

/**
 * Abstracción del manejo de datos de persistencia de Tarea.
 */
public interface TaskDao {

    void findTasks(OnLoadFinishListener listener);

    void updateTask(int id, int subjectId, GregorianCalendar dateTime, String course, String
            classRoom,
                       OnUpdateFinishListener listener);

    void deleteTask(int id, OnDeleteFinishListener listener);

}