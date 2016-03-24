package beatprogramming.github.com.teacker_tracker.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.database.BDHelper;
import beatprogramming.github.com.teacker_tracker.database.ProviderDB;
import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;
import beatprogramming.github.com.teacker_tracker.domain.Exam;
import beatprogramming.github.com.teacker_tracker.domain.Project;
import beatprogramming.github.com.teacker_tracker.domain.Review;
import beatprogramming.github.com.teacker_tracker.domain.Subject;

/**
 * - Implementación en SQLite del acceso a base de datos para manejar datos de Prueba, ya sea Examen o Práctica.
 */
public class ReviewDaoImpl implements ReviewDao {

    // Aliases
    private static final String REVIEW_ID_ALIAS = "reviewId";
    private static final String REVIEW_NAME_ALIAS = "reviewName";
    private static final String SUBJECT_NAME_ALIAS = "subjectName";

    private static final String FINDQUERY =
        "SELECT " + ProviderDB.REVIEW_TABLE + "." + ProviderDB.REVIEW_ID + " AS " + REVIEW_ID_ALIAS + "," +
            ProviderDB.REVIEW_TABLE + "." + ProviderDB.REVIEW_NAME + " AS " + REVIEW_NAME_ALIAS + "," +
            ProviderDB.REVIEW_SUBJECT_ID + "," + ProviderDB.REVIEW_DATE + "," + ProviderDB.REVIEW_TYPE + "," +
            ProviderDB.SUBJECT_TABLE + "." + ProviderDB.SUBJECT_NAME + " AS " + SUBJECT_NAME_ALIAS + "," +
            ProviderDB.SUBJECT_TABLE + "." + ProviderDB.SUBJECT_DESCRIPTION + "," + ProviderDB.SUBJECT_TABLE + "." +
            ProviderDB.SUBJECT_COURSE + " FROM " + ProviderDB.REVIEW_TABLE + " LEFT JOIN " + ProviderDB.SUBJECT_TABLE +
            " ON " + ProviderDB.REVIEW_TABLE + "." + ProviderDB.REVIEW_SUBJECT_ID + " = " + ProviderDB.SUBJECT_TABLE +
            "." + ProviderDB.SUBJECT_ID + " ORDER BY " + ProviderDB.REVIEW_DATE;

    // Review Types
    public static final String EXAM = "Exam";
    public static final String PROJECT = "Project";

    private static SQLiteDatabase sqldb;
    private final BDHelper db;

    public ReviewDaoImpl() {
        db = BDHelper.getInstance();
    }

    /**
     * Metodo que recupera todas las reviews de la base de datos
     *
     * @param listener instancia del listener
     */
    @Override
    public void findReviews(OnLoadFinishListener listener) {
        sqldb = db.getReadableDatabase();
        Cursor c = sqldb.rawQuery(FINDQUERY, null);

        //Lista de reviews
        List reviews = new ArrayList<>();
        DateTime fechaReview = new DateTime();
        if (c.moveToFirst()) {
            do {
                Review r = null;
                if (c.getString(c.getColumnIndex(ProviderDB.REVIEW_TYPE)).equals(PROJECT)) {
                    Subject s = new Subject(c.getString(c.getColumnIndex(SUBJECT_NAME_ALIAS)),
                        c.getString(c.getColumnIndex(ProviderDB.SUBJECT_DESCRIPTION)), c.getString(c.getColumnIndex(ProviderDB.SUBJECT_COURSE)));
                    fechaReview = fechaReview.withMillis(c.getLong(c.getColumnIndex(ProviderDB.REVIEW_DATE)));
                    r = new Project(c.getString(c.getColumnIndex(REVIEW_NAME_ALIAS)), s, fechaReview);
                } else {
                    Subject s = new Subject(c.getString(c.getColumnIndex(SUBJECT_NAME_ALIAS)),
                        c.getString(c.getColumnIndex(ProviderDB.SUBJECT_DESCRIPTION)), c.getString(c.getColumnIndex(ProviderDB.SUBJECT_COURSE)));
                    fechaReview = fechaReview.withMillis(c.getLong(c.getColumnIndex(ProviderDB.REVIEW_DATE)));
                    r = new Exam(c.getString(c.getColumnIndex(REVIEW_NAME_ALIAS)), s, fechaReview);
                }
                r.setId(c.getInt(c.getColumnIndex(REVIEW_ID_ALIAS)));
                reviews.add(r);

            }
            while (c.moveToNext());
        }
        listener.onLoadFinish(reviews);

    }

    /**
     * Metodo que actualiza una review de la base de datos.
     * Si el id=0, quiere decir que no esta creada en la base de datos y en lugar de actualizar
     * se crea una nueva review
     *
     * @param id        id de la evaluacion
     * @param name      nombre de la evaluacion
     * @param subjectId id de la asignatura asociada a la evaluacion
     * @param dateTime  dateTime de la evaluacion
     * @param type      tipo de la evaluacion, pueden ser exam o project
     * @param listener  instancia del listener
     */
    @Override
    public void updateReview(int id, String name, int subjectId, DateTime dateTime, String type,
        OnUpdateFinishListener listener) {

        try {
            sqldb = db.getWritableDatabase();

            ContentValues review = new ContentValues();
            review.put("name", name);
            review.put(ProviderDB.REVIEW_SUBJECT_ID, subjectId);
            review.put(ProviderDB.REVIEW_DATE, dateTime.getMillis());
            review.put(ProviderDB.REVIEW_TYPE, type);
            if (id == 0) {
                sqldb.insert(ProviderDB.REVIEW_TABLE, null, review);

            } else {
                String[] x = new String[]{ String.valueOf(id) };
                sqldb.update(ProviderDB.REVIEW_TABLE, review, ProviderDB.REVIEW_ID + "=?", x);
            }
            listener.onSuccess();

        } catch (Exception e) {
            listener.onError(e.getMessage());
        }

    }

    /**
     * Metodo que borra una review de la base de datos
     *
     * @param id       id de la evaluacion a borrar
     * @param listener instancia del listener
     */
    @Override
    public void deleteReview(int id, OnDeleteFinishListener listener) {

        sqldb = db.getWritableDatabase();
        if (id > 0) {
            //- Borrar review
            String[] value = new String[]{ String.valueOf(id) };
            sqldb.delete(ProviderDB.REVIEW_TABLE, ProviderDB.REVIEW_ID + "=?", value);
        }
        listener.onDeleteFinish();

    }

}
