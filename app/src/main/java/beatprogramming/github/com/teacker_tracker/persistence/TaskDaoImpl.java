package beatprogramming.github.com.teacker_tracker.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.BDHelper;
import beatprogramming.github.com.teacker_tracker.DataSource;
import beatprogramming.github.com.teacker_tracker.ScriptBD;
import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;
import beatprogramming.github.com.teacker_tracker.domain.Exam;
import beatprogramming.github.com.teacker_tracker.domain.Project;
import beatprogramming.github.com.teacker_tracker.domain.Review;
import beatprogramming.github.com.teacker_tracker.domain.Subject;
import beatprogramming.github.com.teacker_tracker.domain.Task;

/**
 * Implementación en SQLite del acceso a base de datos para manejar datos de Tarea.
 */
public class TaskDaoImpl implements TaskDao {

    private static String TAG = TaskDaoImpl.class.getName();

    //Contantes de los campos de la tabla task
    private static final String TASK = "Task";
    private static final String FINDQUERY = "SELECT * FROM Task LEFT JOIN Subject " +
            "ON Task.subjectId = Subject._id;";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String COURSE = "course";
    private static final String DATETIME = "dateTime";
    private static final String SUBJECTID = "subjectId";
    private static final String NOTE = "note";

    private final BDHelper db = BDHelper.getInstance();
    private static SQLiteDatabase sqldb;
    private static Cursor c;

    /**
     * Metodo que devuelte todas las tareas de la base de datos.
     * @param listener
     */
    @Override
    public void findTasks(OnLoadFinishListener listener) {
        //- Buscar todas las tareas
        sqldb = db.getReadableDatabase();
        c = sqldb.rawQuery(FINDQUERY, null);

        //Lista de tareas
        List tasks = new ArrayList<Task>();
        if(c.moveToFirst()){
            do{
                Subject s =  new Subject(c.getString(c.getColumnIndex(NAME)),
                    c.getString(c.getColumnIndex(DESCRIPTION)),
                    c.getString(c.getColumnIndex(COURSE)));
                Task t = new Task(s,new DateTime(c.getInt(c.getColumnIndex(DATETIME))));
                tasks.add(t);
            }while(c.moveToNext());
        }
        listener.onLoadFinish(tasks);
        //listener.onLoadFinish(DataSource.TASK);
    }

    /**
     * Metodo que actualiza una tarea de la base de datos.
     * Si el id=0, quiere decir que no esta creada en la base de datos y en lugar de actualizar
     * se crea una nueva tarea
     * @param id
     * @param name
     * @param subjectId
     * @param dateTime
     * @param note
     * @param listener
     */
    @Override
    public void updateTask(int id, String name,int subjectId,DateTime dateTime,String note,
                           OnUpdateFinishListener listener) {

        sqldb = db.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME,name);
        values.put(SUBJECTID,subjectId);
        values.put(DATETIME,dateTime.getMillis());
        values.put(NOTE, note);

        try{
            if(id == 0) {
                //- Insertar tarea
                sqldb.insert(TASK, null, values);

            } else {
                //- Actualizar tarea
                String[] selectionArgs = new String[]{Integer.toString(id)};
                sqldb.update(TASK,values, ScriptBD.ID_TAREA + "=?",selectionArgs);
            }
            listener.onSuccess();

        } catch (Exception e) {
            listener.onError(e.getMessage());
        }

    }

    /**
     * Metodo que borra una tarea de la base de datos
     * @param id
     * @param listener
     */
    @Override
    public void deleteTask(int id, OnDeleteFinishListener listener) {
        sqldb = db.getWritableDatabase();

        if(id > 0) {
            //- Borrar tarea
            String[] selectionArgs = new String[]{Integer.toString(id)};
            sqldb.delete(TASK,ScriptBD.ID_TAREA + "=?",selectionArgs);
        }
        listener.onDeleteFinish();

    }

}
