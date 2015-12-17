package beatprogramming.github.com.teacker_tracker.persistence;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.BDHelper;
import beatprogramming.github.com.teacker_tracker.ScriptBD;
import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;
import beatprogramming.github.com.teacker_tracker.domain.Schedule;
import beatprogramming.github.com.teacker_tracker.domain.Subject;
import beatprogramming.github.com.teacker_tracker.util.SecureSetter;

public class ScheduleDaoImpl implements ScheduleDao {

    private static String TAG = ScheduleDaoImpl.class.getName();

    //Contantes de los campos de la tabla schedule
    private static String SCHEDULE = "Schedule";
    private static final String FINDQUERY = "SELECT * FROM Task LEFT JOIN Subject " +
            "ON Task.subjectId = Subject._id;";
    private static final String ID = "_id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String COURSE = "course";
    private static final String SUBJECTID = "subjectId";
    private static final String DATETIME = "dateTime";
    private static final String CLASSROOM = "classroom";

    //Variables sql
    private static SQLiteDatabase sqldb;
    private static Cursor c;
    private final BDHelper db;

    public ScheduleDaoImpl() {
        db = BDHelper.getInstance();
    }

    /**
     * Metodo que devuelve todos los horarios de la base de datos.
     * @param listener
     */
    @Override
    public void findSchedule(OnLoadFinishListener listener) {
        //- Buscar todos los horarios
        sqldb = db.getReadableDatabase();
        c = sqldb.rawQuery(FINDQUERY, null);

        //Lista de schedules
        List schedules = new ArrayList<Schedule>();
        if(c.moveToFirst()){
            do{
               Subject s =  new Subject(c.getString(c.getColumnIndex(NAME)),
                       c.getString(c.getColumnIndex(DESCRIPTION)),
                       c.getString(c.getColumnIndex(COURSE)));
                Schedule sc = new Schedule(s, new DateTime(c.getString(c.getColumnIndex(DATETIME))),
                        c.getString(c.getColumnIndex(CLASSROOM)));
                SecureSetter.setId((Serializable) sc, c.getInt(c.getColumnIndex(ID)));
                schedules.add(sc);
            }while(c.moveToNext());
        }
        listener.onLoadFinish(schedules);
    }

    /**
     * Metodo que actualiza un horario en la base de datos.
     * Si el id=0, quiere decir que no esta creada en la base de datos y en lugar de actualizar
     * se crea un nuevo horario
     * @param id
     * @param name
     * @param subjectId
     * @param dateTime
     * @param classroom
     * @param listener
     */
    @Override
    public void updateSchedule(int id, String name, int subjectId, DateTime dateTime, String classroom, OnUpdateFinishListener listener) {
        sqldb = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME,name);
        values.put(SUBJECTID,subjectId);
        values.put(DATETIME,dateTime.getMillis());
        values.put(CLASSROOM,classroom);
        try{
            if(id == 0) {
                //- Insertar horario
                sqldb.insert(SCHEDULE,null,values);
            } else {
                //- Actualizar alumno
                String[] selectionArgs = new String[]{Integer.toString(id)};
                sqldb.update(SCHEDULE,values, ScriptBD.ID_HORARIO + "=?",selectionArgs);
            }
            listener.onSuccess();

        } catch (Exception e) {
            listener.onError(e.getMessage());
        }
    }

    /**
     * Metodo que borra un horario de la base de datos.
     * @param id
     * @param listener
     */
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
