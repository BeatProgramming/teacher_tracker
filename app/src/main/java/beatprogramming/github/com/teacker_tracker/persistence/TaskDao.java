package beatprogramming.github.com.teacker_tracker.persistence;

import org.joda.time.DateTime;

import java.util.GregorianCalendar;

import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;

/**
 * Abstracción del manejo de datos de persistencia de Tarea.
 */
public interface TaskDao {

    void findTasks(OnLoadFinishListener listener);

    void updateTask(int id, String name,int subjectId,DateTime dateTime,String note,
                    OnUpdateFinishListener listener);

    void deleteTask(int id, OnDeleteFinishListener listener);

}
