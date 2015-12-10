package beatprogramming.github.com.teacker_tracker.persistence;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;

import org.joda.time.DateTime;

import beatprogramming.github.com.teacker_tracker.BDHelper;
import beatprogramming.github.com.teacker_tracker.DataSource;
import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;
import beatprogramming.github.com.teacker_tracker.presenter.TaskUpdatePresenter;

/**
 * Implementación en SQLite del acceso a base de datos para manejar datos de Prueba, ya sea Examen o Práctica.
 */
public class ReviewDaoImpl implements ReviewDao {

    private static String TAG = ReviewDaoImpl.class.getName();

    public static final String EXAM = "Exam";
    public static final String PROJECT = "Project";

    private final BDHelper databaseHelper = BDHelper.getInstance();

    @Override
    public void findReviews(OnLoadFinishListener listener) {

        // Here goes GET operation

        listener.onLoadFinish(DataSource.REVIEW);

    }

    @Override
    public void updateReview(int id, String name, int subjectId, DateTime dateTime, String type,
                             OnUpdateFinishListener listener) {

        try{
            if(id == 0) {

                SQLiteDatabase db = databaseHelper.getWritableDatabase();

//                ContentValues examen = new ContentValues();
//                examen.put("nombre",name);
//                examen.put("fecha",dateTime);
//                examen.put("idAsignatura", subjectId);
//
//                db.insert(EVALUACION, null, examen);
//                db.close();

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
