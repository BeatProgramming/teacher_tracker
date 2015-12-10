package beatprogramming.github.com.teacker_tracker.persistence;

import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;

/**
 * Abstracción del manejo de datos de persistencia de Alumno.
 */
public interface StudentDao {

    void findStudents(OnLoadFinishListener listener);

    void updateStudent(int id, String name, String surname, String iconPath,
                       OnUpdateFinishListener listener);

    void deleteStudent(int id, OnDeleteFinishListener listener);

}
