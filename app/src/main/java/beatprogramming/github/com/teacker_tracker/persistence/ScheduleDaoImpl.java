package beatprogramming.github.com.teacker_tracker.persistence;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import org.joda.time.DateTime;
import beatprogramming.github.com.teacker_tracker.BDHelper;
import beatprogramming.github.com.teacker_tracker.ScriptBD;
import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;

public class ScheduleDaoImpl implements ScheduleDao {

    private static String TAG = ScheduleDaoImpl.class.getName();
    private static String SCHEDULE = "Schedule";

    @Override
    public void findSchedule(OnLoadFinishListener listener) {
        //- Buscar todos los horarios
        BDHelper bd = BDHelper.getInstance();
        SQLiteDatabase sql = bd.getReadableDatabase();

        sql.query(SCHEDULE,null,null,null,null,null,null);
    }

    @Override
    public void updateSchedule(int id, String name, int subjectId, DateTime dateTime, String classroom, OnUpdateFinishListener listener) {
        String NAME = "name";
        String SUBJECTID = "subjectId";
        String DATETIME = "dateTime";
        String CLASSROOM = "classroom";

        BDHelper bd = BDHelper.getInstance();
        SQLiteDatabase sql = bd.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME,name);
        values.put(SUBJECTID,subjectId);
        values.put(DATETIME,dateTime.getMillis());
        values.put(CLASSROOM,classroom);

        try{
            if(id == 0) {
                //- Insertar horario
                sql.insert(SCHEDULE,null,values);
            } else {
                //- Actualizar alumno
                String[] selectionArgs = new String[]{Integer.toString(id)};
                sql.update(SCHEDULE,values, ScriptBD.ID_HORARIO + "=?",selectionArgs);
            }
            listener.onSuccess();

        } catch (Exception e) {
            listener.onError(e.getMessage());
        }
    }

    @Override
    public void deleteShedule(int id, OnDeleteFinishListener listener) {
        BDHelper bd = BDHelper.getInstance();
        SQLiteDatabase sql = bd.getWritableDatabase();
        if(id > 0) {
            //- Borrar tarea
            String[] selectionArgs = new String[]{Integer.toString(id)};
            sql.delete(SCHEDULE,ScriptBD.ID_HORARIO + "=?",selectionArgs);
        }
        listener.onDeleteFinish();
    }
}
