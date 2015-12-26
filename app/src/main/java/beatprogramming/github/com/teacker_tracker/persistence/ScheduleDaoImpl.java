package beatprogramming.github.com.teacker_tracker.persistence;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.BDHelper;
import beatprogramming.github.com.teacker_tracker.ScriptBD;
import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;
import beatprogramming.github.com.teacker_tracker.domain.Schedule;
import beatprogramming.github.com.teacker_tracker.domain.Subject;

public class ScheduleDaoImpl implements ScheduleDao {

    private static String TAG = ScheduleDaoImpl.class.getName();

    //Tabla objetivo
    private static String SCHEDULE = "Schedule";

    //Consultas sql
    private static final String FINDQUERY = "SELECT Schedule._id AS scheduleId, Schedule.subjectId, Schedule.dateTime, Schedule.classroom, Schedule.days," +
            " Subject.name AS nameSubject, Subject.description, Subject.course " +
            " FROM Schedule LEFT JOIN Subject " +
            "ON Schedule.subjectId = Subject._id;";
    private static final String FINDQUERYBYSUBJECT = "SELECT Schedule._id AS scheduleId, Schedule.subjectId, Schedule.dateTime, Schedule.classroom, Schedule.days," +
            " Subject.name AS nameSubject, Subject.description, Subject.course " +
            " FROM Schedule LEFT JOIN Subject " +
            "ON Schedule.subjectId = Subject._id WHERE Schedule.subjectId = ?;";


    //Campos de la tabla schedule
    private static final String SCHEDULEID = "scheduleId";
    private static final String SUBJECTID = "subjectId";
    private static final String DATETIME = "dateTime";
    private static final String CLASSROOM = "classroom";
    private static final String DAYS = "days";


    //Campos de la tabla subject
    private static final String NAMESUBJECT = "nameSubject";
    private static final String DESCRIPTION = "description";
    private static final String COURSE = "course";

    //Variables sql
    private static SQLiteDatabase sqldb;
    private static Cursor c;
    private final BDHelper db;

    /**
     * Constructor que inicia el DBHelper
     */
    public ScheduleDaoImpl() {
        db = BDHelper.getInstance();
    }

    /**
     * Metodo que devuelve todos los horarios de la base de datos.
     * @param listener instancia del listener
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
               Subject s =  new Subject(c.getString(c.getColumnIndex(NAMESUBJECT)),
                       c.getString(c.getColumnIndex(DESCRIPTION)),
                       c.getString(c.getColumnIndex(COURSE)));
                Schedule sc = new Schedule(s, c.getString(c.getColumnIndex(DATETIME)),
                        crearBooleanDias(c.getString(c.getColumnIndex(DAYS))),
                        c.getString(c.getColumnIndex(CLASSROOM)));
                sc.setId(c.getInt(c.getColumnIndex(SCHEDULEID)));
                schedules.add(sc);
            }while(c.moveToNext());
        }
        listener.onLoadFinish(schedules);
    }

    /**
     * Metodo que genera un conjunto de 7 valor booleanos que representan los dias de la semana.
     * Si el valor es true significa que el horario es valido para ese dia de la semana, y si es
     * false no es valido.
     * @param d valor de entrada
     * @return dias valor resultado
     */
    private Boolean[] crearBooleanDias(String d) {
        Boolean [] dias = new Boolean[7];
        dias[0] = d.contains("L");
        dias[1] = d.contains("M");
        dias[2] = d.contains("X");
        dias[3] = d.contains("J");
        dias[4] = d.contains("V");
        dias[5] = d.contains("S");
        dias[6] = d.contains("D");
        Log.d(TAG,"Modificado" + d + " a " + dias[0] + dias[1] + dias[2] + dias[3] + dias[4] + dias[5] +dias[6]);
        return dias;
    }

    /**
     * Metodo que genera un conjunto de 7 valor booleanos que representan los dias de la semana.
     * Si el valor es true significa que el horario es valido para ese dia de la semana, y si es
     * false no es valido.
     * @param d valor de entrada
     * @return dias valor resultado
     */
    private String crearStringDias(Boolean[] d) {
        String dias = "";
        if (d[0]){ dias=dias+"L";}
        if (d[1]){ dias=dias+"M";}
        if (d[2]){ dias=dias+"X";}
        if (d[3]){ dias=dias+"J";}
        if (d[4]){ dias=dias+"V";}
        if (d[5]){ dias=dias+"S";}
        if (d[6]){ dias=dias+"D";}
        Log.d(TAG,"Transformado: " + dias + " de " + d[0] + d[1] + d[2] + d[3] + d[4] + d[5] +d[6]);
        return dias;
    }

    /**
     * Metodo que actualiza un horario en la base de datos.
     * Si el id=0, quiere decir que no esta creada en la base de datos y en lugar de actualizar
     * se crea un nuevo horario
     * @param id id del horario
     * @param subjectId id de la asignatura asociada al horario
     * @param dateTime dateTime del horario
     * @param classroom aula del horario
     * @param listener instancia del listener
     */
    @Override
    public void updateSchedule(int id, int subjectId, String dateTime, String classroom, Boolean[] days, OnUpdateFinishListener listener) {
        sqldb = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SUBJECTID,subjectId);
        values.put(DATETIME,dateTime);
        values.put(CLASSROOM,classroom);
        values.put(DAYS, crearStringDias(days));
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
     * @param id id del horario a borrar
     * @param listener instancia del listener
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

    @Override
    public Schedule findScheduleBySubjectId(int id) { //- Buscar todos los horarios
        sqldb = db.getReadableDatabase();
        c = sqldb.rawQuery(FINDQUERYBYSUBJECT, new String[]{Integer.toString(id)});
        Schedule schedule = null;
        if(c.moveToFirst()){
            do{
                Subject subject =  new Subject(c.getString(c.getColumnIndex(NAMESUBJECT)),
                        c.getString(c.getColumnIndex(DESCRIPTION)),
                        c.getString(c.getColumnIndex(COURSE)));
                subject.setId(c.getInt(c.getColumnIndex(SUBJECTID)));
                schedule = new Schedule(subject, c.getString(c.getColumnIndex(DATETIME)),
                        crearBooleanDias(c.getString(c.getColumnIndex(DAYS))),
                        c.getString(c.getColumnIndex(CLASSROOM)));
                schedule.setId(c.getInt(c.getColumnIndex(SCHEDULEID)));
                Log.d(TAG, "findScheduleBySubjectId, Schedule: " + schedule.toString());
                Log.d(TAG, "findScheduleBySubjectId, Subject: " + subject.toString());
            }while(c.moveToNext());
        }
        return schedule;
    }
}
