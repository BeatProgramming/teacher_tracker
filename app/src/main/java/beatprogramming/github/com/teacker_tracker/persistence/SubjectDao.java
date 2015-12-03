package beatprogramming.github.com.teacker_tracker.persistence;

import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;

/**
 * Abstracción del manejo de datos de persistencia de Asignatura.
 */
public interface SubjectDao {

    void findSubjects(OnLoadFinishListener listener);

    void updateSubject(int id, String name, String description, String course, String classRoom,
                       OnUpdateFinishListener listener);

    void deleteSubject(int id, OnDeleteFinishListener listener);

}
