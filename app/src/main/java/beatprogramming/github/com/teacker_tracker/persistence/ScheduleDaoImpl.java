package beatprogramming.github.com.teacker_tracker.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.database.BDHelper;
import beatprogramming.github.com.teacker_tracker.database.ProviderDB;
import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;
import beatprogramming.github.com.teacker_tracker.domain.Schedule;
import beatprogramming.github.com.teacker_tracker.domain.Subject;

/**
 * - Implementación en SQLite del acceso a base de datos para manejar datos de horarios
 */
public class ScheduleDaoImpl implements ScheduleDao {

    private static String TAG = ScheduleDaoImpl.class.getName();

    // Aliases
    private final static String SCHEDULE_ID_ALIAS = "scheduleId";
    private final static String SUBJECT_NAME_ALIAS = "subjectName";

    private static final String FINDQUERY =
        "SELECT " + ProviderDB.SCHEDULE_TABLE + "." + ProviderDB.SCHEDULE_ID + " AS " + SCHEDULE_ID_ALIAS + "," +
            ProviderDB.SCHEDULE_TABLE + "." + ProviderDB.SCHEDULE_SUBJECT_ID + "," + ProviderDB.SCHEDULE_TABLE + "." +
            ProviderDB.SCHEDULE_DATE + "," + ProviderDB.SCHEDULE_TABLE + "." + ProviderDB.SCHEDULE_CLASSROOM + "," +
            ProviderDB.SCHEDULE_TABLE + "." + ProviderDB.SCHEDULE_DAYS + "," + ProviderDB.SUBJECT_TABLE + "." +
            ProviderDB.SUBJECT_NAME + " AS " + SUBJECT_NAME_ALIAS + "," + ProviderDB.SUBJECT_TABLE + "." +
            ProviderDB.SUBJECT_DESCRIPTION + "," + ProviderDB.SUBJECT_TABLE + "." + ProviderDB.SUBJECT_COURSE +
            " FROM " + ProviderDB.SCHEDULE_TABLE + " LEFT JOIN " + ProviderDB.SUBJECT_TABLE + " ON " +
            ProviderDB.SCHEDULE_TABLE + "." + ProviderDB.SCHEDULE_SUBJECT_ID + "=" + ProviderDB.SUBJECT_TABLE + "." +
            ProviderDB.SUBJECT_ID;

    private static final String FINDQUERYBYSUBJECT = FINDQUERY + " WHERE " + ProviderDB.SCHEDULE_TABLE + "." + ProviderDB.SCHEDULE_SUBJECT_ID + " = ?";


    private static SQLiteDatabase sqldb;
    private static Cursor c;
    private final BDHelper db;

    public ScheduleDaoImpl() {
        db = BDHelper.getInstance();
    }

    /**
     * Metodo que devuelve todos los horarios de la base de datos.
     *
     * @param filterDateTime fecha a filtrar
     * @param listener       instancia del listener
     */
    @Override
    public void findSchedule(DateTime filterDateTime, OnLoadFinishListener listener) {
        //- Buscar todos los horarios
        sqldb = db.getReadableDatabase();
        c = sqldb.rawQuery(FINDQUERY, null);

        //Lista de schedules
        List schedules = new ArrayList<Schedule>();
        if (c.moveToFirst()) {
            do {

                Subject subject =
                    new Subject(c.getString(c.getColumnIndex(SUBJECT_NAME_ALIAS)), c.getString(c.getColumnIndex(ProviderDB.SUBJECT_DESCRIPTION)),
                        c.getString(c.getColumnIndex(ProviderDB.SUBJECT_COURSE)));
                subject.setId(c.getColumnIndex(ProviderDB.SCHEDULE_SUBJECT_ID));
                Schedule sc = new Schedule(subject, c.getString(c.getColumnIndex(ProviderDB.SCHEDULE_DATE)),
                    crearBooleanDias(c.getString(c.getColumnIndex(ProviderDB.SCHEDULE_DAYS))), c.getString(c.getColumnIndex(ProviderDB.SCHEDULE_CLASSROOM)));
                sc.setId(c.getInt(c.getColumnIndex(SCHEDULE_ID_ALIAS)));
                Boolean[] dias = sc.getDias();
                if (dias[filterDateTime.getDayOfWeek() - 1]) {
                    schedules.add(sc);
                }
            }
            while (c.moveToNext());
        }
        listener.onLoadFinish(schedules);
    }

    /**
     * Metodo que genera un conjunto de 7 valor booleanos que representan los dias de la semana.
     * Si el valor es true significa que el horario es valido para ese dia de la semana, y si es
     * false no es valido.
     *
     * @param d valor de entrada
     * @return dias valor resultado
     */
    private Boolean[] crearBooleanDias(String d) {
        Boolean[] dias = new Boolean[7];
        dias[0] = d.contains("L");
        dias[1] = d.contains("M");
        dias[2] = d.contains("X");
        dias[3] = d.contains("J");
        dias[4] = d.contains("V");
        dias[5] = d.contains("S");
        dias[6] = d.contains("D");
        return dias;
    }

    /**
     * Metodo que genera un conjunto de 7 valor booleanos que representan los dias de la semana.
     * Si el valor es true significa que el horario es valido para ese dia de la semana, y si es
     * false no es valido.
     *
     * @param d valor de entrada
     * @return dias valor resultado
     */
    private String crearStringDias(Boolean[] d) {
        String dias = "";
        if (d[0]) {
            dias = dias + "L";
        }
        if (d[1]) {
            dias = dias + "M";
        }
        if (d[2]) {
            dias = dias + "X";
        }
        if (d[3]) {
            dias = dias + "J";
        }
        if (d[4]) {
            dias = dias + "V";
        }
        if (d[5]) {
            dias = dias + "S";
        }
        if (d[6]) {
            dias = dias + "D";
        }
        return dias;
    }

    /**
     * Metodo que actualiza un horario en la base de datos.
     * Si el id=0, quiere decir que no esta creada en la base de datos y en lugar de actualizar
     * se crea un nuevo horario
     *
     * @param id        id del horario
     * @param subjectId id de la asignatura asociada al horario
     * @param dateTime  dateTime del horario
     * @param classroom aula del horario
     * @param listener  instancia del listener
     */
    @Override
    public void updateSchedule(int id, int subjectId, String dateTime, String classroom, Boolean[] days,
        OnUpdateFinishListener listener) {
        sqldb = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ProviderDB.SCHEDULE_SUBJECT_ID, subjectId);
        values.put(ProviderDB.SCHEDULE_DATE, dateTime);
        values.put(ProviderDB.SCHEDULE_CLASSROOM, classroom);
        values.put(ProviderDB.SCHEDULE_DAYS, crearStringDias(days));
        try {
            if (id == 0) {
                //- Insertar horario
                sqldb.insert(ProviderDB.SCHEDULE_TABLE, null, values);
            } else {
                //- Actualizar alumno
                String[] selectionArgs = new String[]{ Integer.toString(id) };
                sqldb.update(ProviderDB.SCHEDULE_TABLE, values, ProviderDB.SCHEDULE_ID + "=?", selectionArgs);
            }
            listener.onSuccess();

        } catch (Exception e) {
            listener.onError(e.getMessage());
        }
    }

    /**
     * Metodo que borra un horario de la base de datos.
     *
     * @param id       id del horario a borrar
     * @param listener instancia del listener
     */
    @Override
    public void deleteShedule(int id, OnDeleteFinishListener listener) {
        BDHelper bd = BDHelper.getInstance();
        SQLiteDatabase sql = bd.getWritableDatabase();
        if (id > 0) {
            //- Borrar tarea
            String[] selectionArgs = new String[]{ Integer.toString(id) };
            sql.delete(ProviderDB.SCHEDULE_TABLE, ProviderDB.SCHEDULE_ID + "=?", selectionArgs);
        }
        listener.onDeleteFinish();
    }

    @Override
    public Schedule findScheduleBySubjectId(int id) { //- Buscar todos los horarios
        sqldb = db.getReadableDatabase();
        c = sqldb.rawQuery(FINDQUERYBYSUBJECT, new String[]{ Integer.toString(id) });
        Schedule schedule = null;
        if (c.moveToFirst()) {
            do {
                Subject subject =
                    new Subject(c.getString(c.getColumnIndex(SUBJECT_NAME_ALIAS)), c.getString(c.getColumnIndex(ProviderDB.SUBJECT_DESCRIPTION)),
                        c.getString(c.getColumnIndex(ProviderDB.SUBJECT_COURSE)));
                subject.setId(c.getInt(c.getColumnIndex(ProviderDB.SCHEDULE_SUBJECT_ID)));
                schedule = new Schedule(subject, c.getString(c.getColumnIndex(ProviderDB.SCHEDULE_DATE)),
                    crearBooleanDias(c.getString(c.getColumnIndex(ProviderDB.SCHEDULE_DAYS))), c.getString(c.getColumnIndex(ProviderDB.SCHEDULE_CLASSROOM)));
                schedule.setId(c.getInt(c.getColumnIndex(SCHEDULE_ID_ALIAS)));
                Log.d(TAG, "findScheduleBySubjectId, Schedule: " + schedule.toString());
                Log.d(TAG, "findScheduleBySubjectId, Subject: " + subject.toString());
            }
            while (c.moveToNext());
        }
        return schedule;
    }
}
