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
import beatprogramming.github.com.teacker_tracker.domain.Score;
import beatprogramming.github.com.teacker_tracker.domain.Student;
import beatprogramming.github.com.teacker_tracker.domain.Subject;
import beatprogramming.github.com.teacker_tracker.util.SecureSetter;

public class ScoreDaoImpl implements ScoreDao {

    //Tabla objetivo
    private static final String SCORE = "Score";

    //Consultas sql
    private static final String FINDQUERY = "SELECT T.*, Student.name AS nameStudent, Student.surname, Student.iconPath FROM " +
            "(SELECT Review._id AS reviewId, Review.name AS nameReview, Review.subjectId, Review.dateTime, Review.type," +
            "Score.calification, Score.comment, Score.stundentId, Score._id AS scoreId" +
            "FROM Review LEFT JOIN Score ON Score.studentid  = Review._id) AS T " +
            "LEFT JOIN Student ON T.studentId = Student._id;";
    private static final String FINDBYREVIEW = "SELECT T.*, Student.name AS nameStudent, Student.surname, Student.iconPath FROM " +
            "(SELECT Review._id AS reviewId, Review.name AS nameReview, Review.subjectId, Review.dateTime, Review.type," +
            "Score.calification, Score.comment, Score.stundentId, Score._id AS scoreId" +
            "FROM Review LEFT JOIN Score ON Score.studentid  = Review._id) AS T " +
            "LEFT JOIN Student ON T.studentId = Student._id WHERE T.reviewId = ?;";
    private static final String FINDBYSTUDENT = "SELECT T.*, Student.name AS nameStudent, Student.surname, Student.iconPath FROM " +
            "(SELECT Review._id AS reviewId, Review.name AS nameReview, Review.subjectId, Review.dateTime, Review.type," +
            "Score.calification, Score.comment, Score.stundentId, Score._id AS scoreId" +
            "FROM Review LEFT JOIN Score ON Score.studentid  = Review._id) AS T " +
            "LEFT JOIN Student ON T.studentId = Student._id WHERE Student._id = ?;";

    //Campos de la tabla review
    private static final String NAMEREVIEW = "nameReview";
    private static final String DATETIME = "dateTime";
    private static final String TYPE = "type";
    private static final String REVIEWID = "reviewId";
    private static final String SUBJECTID="subjectId";

    //Campos de la tabla student
    private static final String NAMESTUDENT = "nameStudent";
    private static final String SURNAME = "surname";
    private static final String ICONPATH="iconPath";
    private static final String STUDENTID = "studentId";

    //Campos de la tabla score
    private static final String SCOREID = "scoreId";
    private static final String CALIFICATION = "calification";
    private static final String COMMENT = "comment";

    //Tipos de review
    public static final String EXAM = "Exam";
    public static final String PROJECT = "Project";

    //Variables sql
    private static SQLiteDatabase sqldb;
    private static Cursor c;
    private final BDHelper db;

    /**
     * Constructor que inicia el DBHelper
     */
    public ScoreDaoImpl() {
        db = BDHelper.getInstance();
    }

    /**
     * Metodo que devuelve todas las calificaciones de la base de datos.
     * @param listener instancia del listener
     */
    @Override
    public void findScore(OnLoadFinishListener listener) {
        //- Buscar todas las calificaciones
        sqldb = db.getReadableDatabase();
        c = sqldb.rawQuery(FINDQUERY, null);
        //Lista de reviews
        List scores = new ArrayList<Score>();
        if(c.moveToFirst()){
            do{
                Student st =  new Student(c.getString(c.getColumnIndex(NAMESTUDENT)), c.getString(c.getColumnIndex(SURNAME)));
                SecureSetter.setId(st, c.getInt(c.getColumnIndex(STUDENTID)));
                Review r;
                if (c.getString(c.getColumnIndex(TYPE)) == PROJECT){
                    r = new Project(c.getString(c.getColumnIndex(NAMEREVIEW)),
                            new Subject(),
                            new DateTime(c.getInt(c.getColumnIndex(DATETIME))));
                } else{
                    r = new Exam(c.getString(c.getColumnIndex(NAMEREVIEW)),
                            new Subject(),
                            new DateTime(c.getInt(c.getColumnIndex(DATETIME))));
                }
                Score s =  new Score(c.getFloat(c.getColumnIndex(CALIFICATION)),
                        c.getString(c.getColumnIndex(COMMENT)), st, r);
                SecureSetter.setId(s, c.getInt(c.getColumnIndex(REVIEWID)));
                scores.add(s);
            }while(c.moveToNext());
        }
        listener.onLoadFinish(scores);
    }

    /**
     * Metodo que actualiza una calificacion en la base de datos.
     * Si el id=0, quiere decir que no esta creada en la base de datos y en lugar de actualizar
     * se crea una nueva calificacion
     * @param id id de calificacion
     * @param calification calificacion numerica de la calificacion
     * @param comment comentario de la calificacion
     * @param reviewId id del review asociado a la calificacion
     * @param studentId id del estudiante asociado a la calificacion
     * @param listener instancia del listener
     */
    @Override
    public void updateScore(int id, double calification, String comment, int reviewId, int studentId, OnUpdateFinishListener listener) {
        sqldb = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CALIFICATION,calification);
        values.put(COMMENT,comment);
        values.put(REVIEWID,reviewId);
        values.put(STUDENTID,studentId);

        try{
            if(id == 0) {
                //- Insertar calificacion
                sqldb.insert(SCORE,null,values);
            } else {
                //- Actualizar calificacion
                String[] selectionArgs = new String[]{Integer.toString(id)};
                sqldb.update(SCORE,values, ScriptBD.ID_CALIFICACION + "=?",selectionArgs);
            }
            listener.onSuccess();

        } catch (Exception e) {
            listener.onError(e.getMessage());
        }
    }

    /**
     * Metodo que borra una calificacion de la base de datos.
     * @param id id de la calificacion a borrar
     * @param listener instancia del listener
     */
    @Override
    public void deleteScore(int id, OnDeleteFinishListener listener) {
        sqldb = db.getWritableDatabase();
        if(id > 0) {
            //- Borrar alumno
            String[] selectionArgs = new String[]{Integer.toString(id)};
            sqldb.delete(SCORE,ScriptBD.ID_CALIFICACION + "=?",selectionArgs);
        }
        listener.onDeleteFinish();
    }

    /**
     * Metodo que busca todas las score de un review concreto
     * @param review review por el que se realiza la busqueda
     * @param listener instancia del listener
     */
    @Override
    public void findScoreByReview(Review review, OnLoadFinishListener listener) {
        sqldb = db.getReadableDatabase();
        c = sqldb.rawQuery(FINDBYREVIEW, new String[] {Integer.toString(review.getId())});
        //Lista de reviews
        List scores = new ArrayList<Score>();
        if(c.moveToFirst()){
            do{
                Student st =  new Student(c.getString(c.getColumnIndex(NAMESTUDENT)), c.getString(c.getColumnIndex(SURNAME)));
                SecureSetter.setId(st, c.getInt(c.getColumnIndex(STUDENTID)));
                Review r;
                if (c.getString(c.getColumnIndex(TYPE)) == PROJECT){
                    r = new Project(c.getString(c.getColumnIndex(NAMEREVIEW)),
                            new Subject(),
                            new DateTime(c.getInt(c.getColumnIndex(DATETIME))));
                } else{
                    r = new Exam(c.getString(c.getColumnIndex(NAMEREVIEW)),
                            new Subject(),
                            new DateTime(c.getInt(c.getColumnIndex(DATETIME))));
                }
                Score s =  new Score(c.getFloat(c.getColumnIndex(CALIFICATION)),
                        c.getString(c.getColumnIndex(COMMENT)), st, r);
                SecureSetter.setId(s, c.getInt(c.getColumnIndex(REVIEWID)));
                scores.add(s);

            }while(c.moveToNext());
        }
        listener.onLoadFinish(scores);
    }
}
