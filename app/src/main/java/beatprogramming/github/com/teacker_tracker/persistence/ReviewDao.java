package beatprogramming.github.com.teacker_tracker.persistence;

import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;

/**
 * Abstracción del manejo de datos de persistencia de Prueba, ya sea Examen o Práctica.
 */
public interface ReviewDao {

    void findReviews(OnLoadFinishListener listener);

    void updateReview(int id, String name, int subjectId, String type,
                       OnUpdateFinishListener listener);

    void deleteReview(int id, OnDeleteFinishListener listener);

}
