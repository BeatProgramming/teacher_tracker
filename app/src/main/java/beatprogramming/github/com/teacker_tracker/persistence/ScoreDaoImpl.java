package beatprogramming.github.com.teacker_tracker.persistence;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import beatprogramming.github.com.teacker_tracker.BDHelper;
import beatprogramming.github.com.teacker_tracker.ScriptBD;
import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;

public class ScoreDaoImpl implements ScoreDao {

    private static final String SCORE = "score";

    @Override
    public void findScore(OnLoadFinishListener listener) {
        //- Buscar todas las calificaciones
        BDHelper bd = BDHelper.getInstance();
        SQLiteDatabase sql = bd.getReadableDatabase();

        sql.query(SCORE, null, null, null, null, null, null);
    }

    @Override
    public void updateScore(int id, double calification, String comment, int reviewId, int studentId, OnUpdateFinishListener listener) {
        String CALIFICATION = "calification";
        String COMMENT = "comment";
        String REVIEWID = "reviewId";
        String STUDENTID = "studentId";

        BDHelper bd = BDHelper.getInstance();
        SQLiteDatabase sql = bd.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CALIFICATION,calification);
        values.put(COMMENT,comment);
        values.put(REVIEWID,reviewId);
        values.put(STUDENTID,studentId);

        try{
            if(id == 0) {
                //- Insertar calificacion
                sql.insert(SCORE,null,values);
            } else {
                //- Actualizar calificacion
                String[] selectionArgs = new String[]{Integer.toString(id)};
                sql.update(SCORE,values, ScriptBD.ID_CALIFICACION + "=?",selectionArgs);
            }
            listener.onSuccess();

        } catch (Exception e) {
            listener.onError(e.getMessage());
        }

    }

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
