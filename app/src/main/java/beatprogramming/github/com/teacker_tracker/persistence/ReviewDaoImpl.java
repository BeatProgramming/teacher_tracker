package beatprogramming.github.com.teacker_tracker.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.EditText;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.BDHelper;
import beatprogramming.github.com.teacker_tracker.DataSource;
import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;
import beatprogramming.github.com.teacker_tracker.domain.Exam;
import beatprogramming.github.com.teacker_tracker.domain.Project;
import beatprogramming.github.com.teacker_tracker.domain.Review;
import beatprogramming.github.com.teacker_tracker.domain.Subject;
import beatprogramming.github.com.teacker_tracker.presenter.TaskUpdatePresenter;

/**
 * Implementación en SQLite del acceso a base de datos para manejar datos de Prueba, ya sea Examen o Práctica.
 */
public class ReviewDaoImpl implements ReviewDao {

    private static String TAG = ReviewDaoImpl.class.getName();

    public static final String EXAM = "Exam";
    public static final String PROJECT = "Project";

    public static BDHelper db;
    private static SQLiteDatabase sqldb;
    private static Cursor c;

    private final BDHelper databaseHelper = BDHelper.getInstance();

    /**
     * Metodo que recupera todas las reviews de la base de datos
     *
     * @param listener
     */
    @Override
    public void findReviews(OnLoadFinishListener listener) {
        db = BDHelper.getInstance();
        sqldb = db.getReadableDatabase();
        c = sqldb.rawQuery("SELECT * FROM Review LEFT JOIN Subject " +
                "ON Review.subjectId = Subject._id;", null);

        //Lista de reviews
        List reviews = new ArrayList<Review>();
        if(c.moveToFirst()){
            do{
                if (c.getString(c.getColumnIndex("type")) == "project"){
                    Subject s =  new Subject(c.getString(c.getColumnIndex("name")),
                            c.getString(c.getColumnIndex("description")),
                            c.getString(c.getColumnIndex("curse")));
                    Project p = new Project(c.getString(c.getColumnIndex("name")),
                            s, new DateTime(c.getInt(c.getColumnIndex("dateTime"))));
                    reviews.add(p);
                } else{
                    Subject s =  new Subject(c.getString(c.getColumnIndex("name")),
                            c.getString(c.getColumnIndex("description")),
                            c.getString(c.getColumnIndex("curse")));
                    Exam e = new Exam(c.getString(c.getColumnIndex("name")),
                            s, new DateTime(c.getInt(c.getColumnIndex("dateTime"))));
                    reviews.add(e);
                }

            }while(c.moveToNext());
        }
        listener.onLoadFinish(reviews);

    }

    /**
     * Metodo que actualiza una review de la base de datos.
     * Si el id=0, quiere decir que no esta creada en la base de datos y en lugar de actualizar
     * se crea una nueva review
     * @param id
     * @param name
     * @param subjectId
     * @param dateTime
     * @param type
     * @param listener
     */
    @Override
    public void updateReview(int id, String name, int subjectId, DateTime dateTime, String type,
                             OnUpdateFinishListener listener) {

        try{
            db = BDHelper.getInstance();
            sqldb = databaseHelper.getWritableDatabase();

            ContentValues reviews = new ContentValues();
            reviews.put("subjectId", subjectId);
            reviews.put("dateTime", dateTime.getMillis());
            reviews.put("type", type);
            if(id == 0) {
                sqldb.insert("Review", null, reviews);

            } else {
                String[] x = new String[]{String.valueOf(id)};
                sqldb.update("Review", reviews, "_id=?" , x);
            }
            listener.onSuccess();

        } catch (Exception e) {
            listener.onError(e.getMessage());
        }

    }

    /**
     *Metodo que borra una review de la base de datos
     * @param id
     * @param listener
     */
    @Override
    public void deleteReview(int id, OnDeleteFinishListener listener) {

        sqldb = databaseHelper.getWritableDatabase();
        if(id > 0) {
            String[] value = new String[]{String.valueOf(id)};
            sqldb.delete("Review", "_id=?", value);
        }
        listener.onDeleteFinish();

    }

}
