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
import beatprogramming.github.com.teacker_tracker.domain.Task;

/**
 * Implementación en SQLite del acceso a base de datos para manejar datos de Tarea.
 */
public class TaskDaoImpl implements TaskDao {

    private static String TAG = TaskDaoImpl.class.getName();
    private static String TASK = "Task";

    private final BDHelper databaseHelper = BDHelper.getInstance();

    @Override
    public void findTasks(OnLoadFinishListener listener) {
        //- Buscar todas las tareas
        BDHelper bd = BDHelper.getInstance();
        SQLiteDatabase sql = bd.getReadableDatabase();

        sql.query(TASK,null,null,null,null,null,null);

        //- ELIMINAR
        List<Task> tasks = new ArrayList<Task>();
        //- Campos que queremos obtener al realizar la query
        String[] campos = new String[] {"_id","hora","descripcion","idAsignatura", "nota"};
        //- Ejecución de la query
//        Cursor c = db.query("Tarea", campos, null, null, null, null, null);
//
//        if(c.moveToFirst()){
//            do{
//                Task task = new Task();
//                task.setDateTime(c.getString(0));
//                task.setDescription(c.getString(1));
//                task.setSubject(c.getString(3));
//                task.setNote(c.getString(2));
//                Log.d(TAG, "findTasks, " + c.getString(0) + ", " + c.getString(1) + ", " + c
//                        .getString(2) + ", " + c.getInt(3) + ", " + c.getString(4));
//
//                tasks.add(task);
//            }while(c.moveToNext());
//        }

        listener.onLoadFinish(DataSource.TASK);

    }

    @Override
    public void updateTask(int id, String name,int subjectId,DateTime dateTime,String note,
                           OnUpdateFinishListener listener) {
        String NAME = "name";
        String SUBJECTID = "subjectId";
        String DATETIME = "dateTime";
        String NOTE = "note";

        BDHelper bd = BDHelper.getInstance();
        SQLiteDatabase sql = bd.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME,name);
        values.put(SUBJECTID,subjectId);
        values.put(DATETIME,dateTime.getMillis());
        values.put(NOTE, note);

        try{
            if(id == 0) {
                //- Insertar tarea
                sql.insert(TASK,null,values);

                //-ELIMINAR
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                ContentValues tarea = new ContentValues();
//              tarea.put("hora", dateTime);
                tarea.put("descripcion", note);
                tarea.put("idAsignatura", subjectId);
                long num = db.insert("Tarea", null, tarea);
                db.close();

            } else {
                //- Actualizar tarea
                String[] selectionArgs = new String[]{Integer.toString(id)};
                sql.update(TASK,values, ScriptBD.ID_TAREA + "=?",selectionArgs);
            }
            listener.onSuccess();

        } catch (Exception e) {
            listener.onError(e.getMessage());
        }

    }

    @Override
    public void deleteTask(int id, OnDeleteFinishListener listener) {
        BDHelper bd = BDHelper.getInstance();
        SQLiteDatabase sql = bd.getWritableDatabase();

        if(id > 0) {
            //- Borrar tarea
            String[] selectionArgs = new String[]{Integer.toString(id)};
            sql.delete(TASK,ScriptBD.ID_TAREA + "=?",selectionArgs);
        }
        listener.onDeleteFinish();

    }

}
