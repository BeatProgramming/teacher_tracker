package beatprogramming.github.com.teacker_tracker.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.BDHelper;
import beatprogramming.github.com.teacker_tracker.ScriptBD;
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

    //Tabla objetivo
    private static final String REVIEW = "review";

    //Consultas sql
    private static final String FINDQUERY = "SELECT Review._id AS ReviewId, Review.name AS nameReview, Review.subjectId," +
            " Review.dateTime, Review.type, Subject.name AS nameSubject, Subject.description, Subject.course" +
            " FROM Review LEFT JOIN Subject " +
            " ON Review.subjectId = Subject._id;";

    //Campos de la tabla Review
    private static final String REVIEWID = "ReviewId";
    private static final String NAMEREVIEW = "nameReview";
    private static final String TYPE = "type";
    private static final String SUBJECTID = "subjectId";
    private static final String DATETIME = "dateTime";

    //Campos de la tabla Subject
    private static final String NAMESUBJECT = "nameSubject";
    private static final String COURSE = "course";
    private static final String DESCRIPTION = "description";

    //Tipos de review
    public static final String EXAM = "Exam";
    public static final String PROJECT = "Project";

    //Variables sql
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
        if(c.moveToFirst()){
            do{
                Review r = null;
                if (c.getString(c.getColumnIndex(TYPE)).equals(PROJECT)){
                    Subject s =  new Subject(c.getString(c.getColumnIndex(NAMESUBJECT)),
                            c.getString(c.getColumnIndex(DESCRIPTION)),
                            c.getString(c.getColumnIndex(COURSE)));
                    fechaReview = fechaReview.withMillis(c.getLong(c.getColumnIndex(DATETIME)));
                    r = new Project(c.getString(c.getColumnIndex(NAMEREVIEW)),
                            s, fechaReview);
                } else{
                    Subject s =  new Subject(c.getString(c.getColumnIndex(NAMESUBJECT)),
                            c.getString(c.getColumnIndex(DESCRIPTION)),
                            c.getString(c.getColumnIndex(COURSE)));
                    fechaReview = fechaReview.withMillis(c.getLong(c.getColumnIndex(DATETIME)));
                    r= new Exam(c.getString(c.getColumnIndex(NAMEREVIEW)),
                            s, fechaReview);
                }
                r.setId(c.getInt(c.getColumnIndex(REVIEWID)));
                reviews.add(r);

            }while(c.moveToNext());
        }
        listener.onLoadFinish(reviews);

    }

    /**
     * Metodo que actualiza una review de la base de datos.
     * Si el id=0, quiere decir que no esta creada en la base de datos y en lugar de actualizar
     * se crea una nueva review
     * @param id id de la evaluacion
     * @param name nombre de la evaluacion
     * @param subjectId id de la asignatura asociada a la evaluacion
     * @param dateTime dateTime de la evaluacion
     * @param type tipo de la evaluacion, pueden ser exam o project
     * @param listener instancia del listener
     */
    @Override
    public void updateReview(int id, String name, int subjectId, DateTime dateTime, String type,
                             OnUpdateFinishListener listener) {

        try{
            sqldb = db.getWritableDatabase();

            ContentValues review = new ContentValues();
            review.put("name", name);
            review.put(SUBJECTID, subjectId);
            review.put(DATETIME, dateTime.getMillis());
            review.put(TYPE, type);
            if(id == 0) {
                sqldb.insert(REVIEW, null, review);

            } else {
                String[] x = new String[]{String.valueOf(id)};
                sqldb.update(REVIEW, review, ScriptBD.ID_EVALUACION + "=?", x);
            }
            listener.onSuccess();

        } catch (Exception e) {
            listener.onError(e.getMessage());
        }

    }

    /**
     *Metodo que borra una review de la base de datos
     * @param id id de la evaluacion a borrar
     * @param listener instancia del listener
     */
    @Override
    public void deleteReview(int id, OnDeleteFinishListener listener) {

        sqldb = db.getWritableDatabase();
        if(id > 0) {
            //- Borrar review
            String[] value = new String[]{String.valueOf(id)};
            sqldb.delete(REVIEW, ScriptBD.ID_EVALUACION + "=?", value);
        }
        listener.onDeleteFinish();

    }

}
