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
import beatprogramming.github.com.teacker_tracker.domain.Subject;
import beatprogramming.github.com.teacker_tracker.domain.Task;

/**
 * - Implementación en SQLite del acceso a base de datos para manejar datos de tareas
 */
public class TaskDaoImpl implements TaskDao {

    private static String TAG = TaskDaoImpl.class.getName();

    //Tabla objetivo
    private static final String TASK = "Task";

    //Consultas sql
    private static final String FINDQUERY = "SELECT Task._id AS taskId, Task.subjectId, Task.name AS nameTask, Task.dateTime," +
            " Task.note, Subject.name AS nameSubject, Subject.description, Subject.course " +
            " FROM Task LEFT JOIN Subject ON Task.subjectId = Subject._id ORDER BY Task.dateTime;";

    //Campos de la tabla Task
    private static final String TASKID = "taskId";
    private static final String NAMETASK = "nameTask";
    private static final String NOTE = "note";
    private static final String DATETIME = "dateTime";
    private static final String SUBJECTID = "subjectId";

    //Campos de la tabla Subject
    private static final String NAMESUBJECT = "nameSubject";
    private static final String DESCRIPTION = "description";
    private static final String COURSE = "course";

    //Variables sql
    private final BDHelper db;
    private static SQLiteDatabase sqldb;
    private static Cursor c;

    private static List<Task> listaFinalTask;
    /**
     * Contructor que inicializa el DBHelper
     */
    public TaskDaoImpl() {
        db = BDHelper.getInstance();
        listaFinalTask = new ArrayList<>();
    }

    /**
     * Metodo que devuelte todas las tareas de la base de datos.
     * @param listener instancia del listener
     */
    @Override
    public void findTasks(DateTime filterDateTime, OnLoadFinishListener listener) {
        //- Buscar todas las tareas
        sqldb = db.getReadableDatabase();
        c = sqldb.rawQuery(FINDQUERY, null);

        DateTime fechaTarea = new DateTime();
        //Lista de tareas
        List tasks = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                fechaTarea = fechaTarea.withMillis(c.getLong(c.getColumnIndex(DATETIME)));
                if (fechaTarea.getDayOfWeek() == filterDateTime.getDayOfWeek() &&
                        fechaTarea.getYear() == filterDateTime.getYear() &&
                        fechaTarea.getMonthOfYear() == filterDateTime.getMonthOfYear() ) {
                    Subject s = new Subject(c.getString(c.getColumnIndex(NAMESUBJECT)),
                            c.getString(c.getColumnIndex(DESCRIPTION)),
                            c.getString(c.getColumnIndex(COURSE)));
                    s.setId(c.getInt(c.getColumnIndex(SUBJECTID)));
                    fechaTarea = fechaTarea.withMillis(c.getLong(c.getColumnIndex(DATETIME)));
                    Task t = new Task(c.getString(c.getColumnIndex(NAMETASK)), s, fechaTarea);
                    t.setId(c.getInt(c.getColumnIndex(TASKID)));
                    t.setNote(c.getString(c.getColumnIndex(NOTE)));
                    tasks.add(t);
                    Log.d(TAG, "findTasks, " + t.toString());
                }
            }while(c.moveToNext());
        }
        listener.onLoadFinish(tasks);
    }


    /**
     * Metodo que actualiza una tarea de la base de datos.
     * Si el id=0, quiere decir que no esta creada en la base de datos y en lugar de actualizar
     * se crea una nueva tarea
     * @param id id de la tarea
     * @param name nombre de la tarea
     * @param subjectId id de asignatura asociada a la tarea
     * @param dateTime dateTime de la tarea
     * @param listener instancia del listener
     */
    @Override
    public void updateTask(int id, String name, int subjectId, DateTime dateTime, String note,
                           OnUpdateFinishListener listener) {

        sqldb = db.getWritableDatabase();
        //Valores para la busqueda en la base de datos
        Log.d(TAG,"Valor de fecha cambio / creacion: " + dateTime.toString());
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put(SUBJECTID,subjectId);
        values.put(DATETIME,dateTime.getMillis());
        values.put(NOTE, note);

        Log.d(TAG, "updateTask, id: " + id + ", name: " + name + ", subjectId: " + subjectId + ", dateTime: "
                + dateTime.toString() + ",note: " + note);
        try{
            if(id == 0) {
                //- Insertar tarea
                sqldb.insert(TASK, null, values);

            } else {
                //- Actualizar tarea
                String[] selectionArgs = new String[]{Integer.toString(id)};
                sqldb.update(TASK, values, ScriptBD.ID_TAREA + "=?",selectionArgs);
            }
            listener.onSuccess();

        } catch (Exception e) {
            listener.onError(e.getMessage());
        }
    }

    /**
     * Metodo que borra una tarea de la base de datos
     * @param id id de la tarea a borrar
     * @param listener instancia del listener
     */
    @Override
    public void deleteTask(int id, OnDeleteFinishListener listener) {
        sqldb = db.getWritableDatabase();

        if(id > 0) {
            //- Borrar tarea
            String[] selectionArgs = new String[]{Integer.toString(id)};
            sqldb.delete(TASK, ScriptBD.ID_TAREA + "=?",selectionArgs);
        }
        listener.onDeleteFinish();
    }
}
