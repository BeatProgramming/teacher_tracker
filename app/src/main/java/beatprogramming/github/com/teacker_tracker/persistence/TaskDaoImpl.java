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
import beatprogramming.github.com.teacker_tracker.domain.Subject;
import beatprogramming.github.com.teacker_tracker.domain.Task;

/**
 * - Implementación en SQLite del acceso a base de datos para manejar datos de tareas
 */
public class TaskDaoImpl implements TaskDao {

    private static String TAG = TaskDaoImpl.class.getName();

    // Aliases
    private final static String TASK_ID_ALIAS = "taskId";
    private final static String TASK_NAME_ALIAS = "taskName";
    private final static String SUBJECT_NAME_ALIAS = "subjectName";

    private static final String FINDQUERY =
        "SELECT " + ProviderDB.TASK_TABLE + "." + ProviderDB.TASK_ID + " AS " + TASK_ID_ALIAS + "," +
            ProviderDB.TASK_TABLE + "." + ProviderDB.TASK_SUBJECT_ID + "," + ProviderDB.TASK_TABLE + "." +
            ProviderDB.TASK_NAME + " AS " + TASK_NAME_ALIAS + "," + ProviderDB.TASK_TABLE + "." + ProviderDB.TASK_DATE +
            "," + ProviderDB.TASK_TABLE + "." + ProviderDB.TASK_NOTE + "," + ProviderDB.SUBJECT_TABLE + "." +
            ProviderDB.SUBJECT_NAME + " AS " + SUBJECT_NAME_ALIAS + "," + ProviderDB.SUBJECT_TABLE + "." +
            ProviderDB.SUBJECT_DESCRIPTION + "," + ProviderDB.SUBJECT_TABLE + "." + ProviderDB.SUBJECT_COURSE +
            " FROM " + ProviderDB.TASK_TABLE + " LEFT JOIN " + ProviderDB.SUBJECT_TABLE + " ON " +
            ProviderDB.TASK_TABLE + "." + ProviderDB.TASK_SUBJECT_ID + "=" + ProviderDB.SUBJECT_TABLE + "." +
            ProviderDB.SUBJECT_ID + " ORDER BY " + ProviderDB.TASK_TABLE + "." + ProviderDB.TASK_DATE;

    private final BDHelper db;
    private static SQLiteDatabase sqldb;
    private static Cursor c;

    public TaskDaoImpl() {
        db = BDHelper.getInstance();
    }

    /**
     * Metodo que devuelte todas las tareas de la base de datos.
     *
     * @param filterDateTime fecha a filtrar
     * @param listener       instancia del listener
     */
    @Override
    public void findTasks(DateTime filterDateTime, OnLoadFinishListener listener) {
        //- Buscar todas las tareas
        sqldb = db.getReadableDatabase();
        c = sqldb.rawQuery(FINDQUERY, null);

        DateTime fechaTarea = new DateTime();
        //Lista de tareas
        List tasks = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                fechaTarea = fechaTarea.withMillis(c.getLong(c.getColumnIndex(ProviderDB.TASK_DATE)));
                if (fechaTarea.getDayOfWeek() == filterDateTime.getDayOfWeek() &&
                    fechaTarea.getYear() == filterDateTime.getYear() &&
                    fechaTarea.getMonthOfYear() == filterDateTime.getMonthOfYear()) {
                    Subject s = new Subject(c.getString(c.getColumnIndex(SUBJECT_NAME_ALIAS)),
                        c.getString(c.getColumnIndex(ProviderDB.SUBJECT_DESCRIPTION)),
                        c.getString(c.getColumnIndex(ProviderDB.SUBJECT_COURSE)));
                    s.setId(c.getInt(c.getColumnIndex(ProviderDB.TASK_SUBJECT_ID)));
                    fechaTarea = fechaTarea.withMillis(c.getLong(c.getColumnIndex(ProviderDB.TASK_DATE)));
                    Task t = new Task(c.getString(c.getColumnIndex(TASK_NAME_ALIAS)), s, fechaTarea);
                    t.setId(c.getInt(c.getColumnIndex(TASK_ID_ALIAS)));
                    t.setNote(c.getString(c.getColumnIndex(ProviderDB.TASK_NOTE)));
                    tasks.add(t);
                    Log.d(TAG, "findTasks, " + t.toString());
                }
            }
            while (c.moveToNext());
        }
        listener.onLoadFinish(tasks);
    }


    /**
     * Metodo que actualiza una tarea de la base de datos.
     * Si el id=0, quiere decir que no esta creada en la base de datos y en lugar de actualizar
     * se crea una nueva tarea
     *
     * @param id        id de la tarea
     * @param name      nombre de la tarea
     * @param subjectId id de asignatura asociada a la tarea
     * @param dateTime  dateTime de la tarea
     * @param listener  instancia del listener
     */
    @Override
    public void updateTask(int id, String name, int subjectId, DateTime dateTime, String note,
        OnUpdateFinishListener listener) {

        sqldb = db.getWritableDatabase();
        //Valores para la busqueda en la base de datos
        Log.d(TAG, "Valor de fecha cambio / creacion: " + dateTime.toString());
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put(ProviderDB.TASK_SUBJECT_ID, subjectId);
        values.put(ProviderDB.TASK_DATE, dateTime.getMillis());
        values.put(ProviderDB.TASK_NOTE, note);

        Log.d(TAG, "updateTask, id: " + id + ", name: " + name + ", subjectId: " + subjectId + ", dateTime: " +
            dateTime.toString() + ",note: " + note);
        try {
            if (id == 0) {
                //- Insertar tarea
                sqldb.insert(ProviderDB.TASK_TABLE, null, values);

            } else {
                //- Actualizar tarea
                String[] selectionArgs = new String[]{ Integer.toString(id) };
                sqldb.update(ProviderDB.TASK_TABLE, values, ProviderDB.TASK_ID + "=?", selectionArgs);
            }
            listener.onSuccess();

        } catch (Exception e) {
            listener.onError(e.getMessage());
        }
    }

    /**
     * Metodo que borra una tarea de la base de datos
     *
     * @param id       id de la tarea a borrar
     * @param listener instancia del listener
     */
    @Override
    public void deleteTask(int id, OnDeleteFinishListener listener) {
        sqldb = db.getWritableDatabase();

        if (id > 0) {
            //- Borrar tarea
            String[] selectionArgs = new String[]{ Integer.toString(id) };
            sqldb.delete(ProviderDB.TASK_TABLE, ProviderDB.TASK_ID + "=?", selectionArgs);
        }
        listener.onDeleteFinish();
    }
}
