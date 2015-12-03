package beatprogramming.github.com.teacker_tracker.persistence;

import java.util.GregorianCalendar;

import beatprogramming.github.com.teacker_tracker.DataSource;
import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;

/**
 * Implementación en SQLite del acceso a base de datos para manejar datos de Tarea.
 */
public class TaskDaoImpl implements TaskDao {

    @Override
    public void findTasks(OnLoadFinishListener listener) {

        // Here goes GET operation

        listener.onLoadFinish(DataSource.TASK);

    }

    @Override
    public void updateTask(int id, int subjectId, GregorianCalendar dateTime, String course, String classRoom, OnUpdateFinishListener listener) {

        try{
            if(id == 0) {

                // Here goes INSERT operation

            } else {

                // Here goes UPDATE operation

            }
            listener.onSuccess();

        } catch (Exception e) {
            listener.onError(e.getMessage());
        }

    }

    @Override
    public void deleteTask(int id, OnDeleteFinishListener listener) {

        if(id > 0) {

            // Here goes DELETE operation

        }
        listener.onDeleteFinish();

    }

}
