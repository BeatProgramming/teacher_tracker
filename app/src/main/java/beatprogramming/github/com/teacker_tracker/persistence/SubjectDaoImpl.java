package beatprogramming.github.com.teacker_tracker.persistence;

import beatprogramming.github.com.teacker_tracker.DataSource;
import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;

/**
 * Implementación en SQLite del acceso a base de datos para manejar datos de Asignatura.
 */
public class SubjectDaoImpl implements SubjectDao {

    @Override
    public void findSubjects(OnLoadFinishListener listener) {

        // Here goes GET operation

        listener.onLoadFinish(DataSource.SUBJECT);
    }

    @Override
    public void updateSubject(int id, String name, String description, String course, String classRoom, OnUpdateFinishListener listener) {

        try{
            if(id == 0) {

                // Here goes INSERT operation

            } else {

                // Here goes UPDATE operation

            }
            throw new Exception("HOLA QUE TAL");
//            listener.onSuccess();

        } catch (Exception e) {
            listener.onError(e.getMessage());
        }

    }

    @Override
    public void deleteSubject(int id, OnDeleteFinishListener listener) {

        if(id > 0) {

            // Here goes DELETE operation

        }
        listener.onDeleteFinish();

    }
}
