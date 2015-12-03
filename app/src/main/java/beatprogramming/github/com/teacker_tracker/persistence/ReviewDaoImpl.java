package beatprogramming.github.com.teacker_tracker.persistence;

import beatprogramming.github.com.teacker_tracker.DataSource;
import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;
import beatprogramming.github.com.teacker_tracker.presenter.TaskUpdatePresenter;

/**
 * Implementación en SQLite del acceso a base de datos para manejar datos de Prueba, ya sea Examen o Práctica.
 */
public class ReviewDaoImpl implements ReviewDao {

    private static String TAG = ReviewDaoImpl.class.getName();

    @Override
    public void findReviews(OnLoadFinishListener listener) {

        // Here goes GET operation

        listener.onLoadFinish(DataSource.REVIEW);

    }

    @Override
    public void updateReview(int id, String name, int subjectId, String type, OnUpdateFinishListener listener) {

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
    public void deleteReview(int id, OnDeleteFinishListener listener) {

        if(id > 0) {

            // Here goes DELETE operation

        }
        listener.onDeleteFinish();

    }

}
