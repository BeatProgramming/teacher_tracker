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

public class ScoreDaoImpl implements ScoreDao {

    //Contantes de los campos de la tabla review
    private static final String SCORE = "Score";
    private static final String FINDQUERY = "SELECT * FROM Review LEFT JOIN Subject " +
            "ON Review.subjectId = Subject._id;";
    private static final String CALIFICATION = "calification";
    private static final String COMMENT = "comment";
    private static final String REVIEWID = "reviewId";
    private static final String STUDENTID = "studentId";

    //Variables sql
    private static SQLiteDatabase sqldb;
    private static Cursor c;
    private final BDHelper db = BDHelper.getInstance();

    /**
     * Metodo que devuelve todas las calificaciones de la base de datos.
     * @param listener
     */
    @Override
    public void findScore(OnLoadFinishListener listener) {
        //- Buscar todas las calificaciones
        sqldb = db.getReadableDatabase();
        c = sqldb.rawQuery(FINDQUERY, null);//Lista de reviews
        List scores = new ArrayList<Score>();
        if(c.moveToFirst()){
            do{
                Student st =  new Student();

                Score s =  new Score();
                    scores.add(s);

            }while(c.moveToNext());
        }
        listener.onLoadFinish(scores);

    }

    /**
     * Metodo que actualiza una calificacion en la base de datos.
     * Si el id=0, quiere decir que no esta creada en la base de datos y en lugar de actualizar
     * se crea una nueva calificacion
     * @param id
     * @param calification
     * @param comment
     * @param reviewId
     * @param studentId
     * @param listener
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
     * @param id
     * @param listener
     */
    @Override
    public void deleteScore(int id, OnDeleteFinishListener listener) {
        BDHelper bd = BDHelper.getInstance();
        SQLiteDatabase sql = bd.getWritableDatabase();
        if(id > 0) {
            //- Borrar alumno
            String[] selectionArgs = new String[]{Integer.toString(id)};
            sql.delete(SCORE,ScriptBD.ID_CALIFICACION + "=?",selectionArgs);
        }
        listener.onDeleteFinish();
    }
}
