package beatprogramming.github.com.teacker_tracker.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.BDHelper;
import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;
import beatprogramming.github.com.teacker_tracker.domain.Exam;
import beatprogramming.github.com.teacker_tracker.domain.Project;
import beatprogramming.github.com.teacker_tracker.domain.Review;
import beatprogramming.github.com.teacker_tracker.domain.Subject;

/**
 * Implementación en SQLite del acceso a base de datos para manejar datos de Prueba, ya sea Examen o Práctica.
 */
public class ReviewDaoImpl implements ReviewDao {

    private static String TAG = ReviewDaoImpl.class.getName();

    //Contantes de los campos de la tabla review
    private static final String REVIEW = "review";
    private static final String FINDQUERY = "SELECT * FROM Review LEFT JOIN Subject " +
            "ON Review.subjectId = Subject._id;";
    private static final String NAME = "name";
    private static final String TYPE = "type";
    private static final String DESCRIPTION = "description";
    private static final String COURSE = "course";
    private static final String DATETIME = "dateTime";
    private static final String SUBJECTID = "subjectId";
    private static final String CAMPOID = "_id=?";

    public static final String EXAM = "Exam";
    public static final String PROJECT = "Project";

    //Variables sql
    private static SQLiteDatabase sqldb;
    private static Cursor c;
    private final BDHelper db;

    public ReviewDaoImpl() {
        db = BDHelper.getInstance();
    }

    /**
     * Metodo que recupera todas las reviews de la base de datos
     *
     * @param listener
     */
    @Override
    public void findReviews(OnLoadFinishListener listener) {
        sqldb = db.getReadableDatabase();
        c = sqldb.rawQuery(FINDQUERY, null);

        //Lista de reviews
        List reviews = new ArrayList<Review>();
        if(c.moveToFirst()){
            do{
                if (c.getString(c.getColumnIndex(TYPE)) == PROJECT){
                    Subject s =  new Subject(c.getString(c.getColumnIndex(NAME)),
                            c.getString(c.getColumnIndex(DESCRIPTION)),
                            c.getString(c.getColumnIndex(COURSE)));
                    Project p = new Project(c.getString(c.getColumnIndex(NAME)),
                            s, new DateTime(c.getInt(c.getColumnIndex(DATETIME))));
                    reviews.add(p);
                } else{
                    Subject s =  new Subject(c.getString(c.getColumnIndex(NAME)),
                            c.getString(c.getColumnIndex(DESCRIPTION)),
                            c.getString(c.getColumnIndex(COURSE)));
                    Exam e = new Exam(c.getString(c.getColumnIndex(NAME)),
                            s, new DateTime(c.getInt(c.getColumnIndex(DATETIME))));
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
            sqldb = db.getWritableDatabase();

            ContentValues reviews = new ContentValues();
            reviews.put(SUBJECTID, subjectId);
            reviews.put(DATETIME, dateTime.getMillis());
            reviews.put(TYPE, type);
            if(id == 0) {
                sqldb.insert(REVIEW, null, reviews);

            } else {
                String[] x = new String[]{String.valueOf(id)};
                sqldb.update(REVIEW, reviews, CAMPOID , x);
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

        sqldb = db.getWritableDatabase();
        if(id > 0) {
            //- Borrar review
            String[] value = new String[]{String.valueOf(id)};
            sqldb.delete(REVIEW, CAMPOID, value);
        }
        listener.onDeleteFinish();

    }

}
