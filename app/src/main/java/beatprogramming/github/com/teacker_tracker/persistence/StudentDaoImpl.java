package beatprogramming.github.com.teacker_tracker.persistence;

import beatprogramming.github.com.teacker_tracker.DataSource;
import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;

/**
 * Implementación en SQLite del acceso a base de datos para manejar datos de Alumno.
 */
public class StudentDaoImpl implements StudentDao {

    private static String TAG = StudentDaoImpl.class.getName();

    @Override
    public void findStudents(OnLoadFinishListener listener) {

        // Here goes GET operation

        listener.onLoadFinish(DataSource.STUDENT);

    }

    @Override
    public void updateStudent(int id, String name, String surname, String iconPath,
                              OnUpdateFinishListener listener) {

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
    public void deleteStudent(int id, OnDeleteFinishListener listener) {

        if(id > 0) {

            // Here goes DELETE operation

        }
        listener.onDeleteFinish();

    }
}
