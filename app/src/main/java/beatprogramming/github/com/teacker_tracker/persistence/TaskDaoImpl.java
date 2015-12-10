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
import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;
import beatprogramming.github.com.teacker_tracker.domain.Task;

/**
 * Implementación en SQLite del acceso a base de datos para manejar datos de Tarea.
 */
public class TaskDaoImpl implements TaskDao {

    private static String TAG = TaskDaoImpl.class.getName();

    private final BDHelper databaseHelper = BDHelper.getInstance();

    @Override
    public void findTasks(OnLoadFinishListener listener) {

        List<Task> tasks = new ArrayList<Task>();

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

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
    public void updateTask(int id, String description, DateTime dateTime, int subjectId,
                           OnUpdateFinishListener listener) {

        try{
            if(id == 0) {

                SQLiteDatabase db = databaseHelper.getWritableDatabase();

                ContentValues tarea = new ContentValues();
//                tarea.put("hora", dateTime);
                tarea.put("descripcion", description);
                tarea.put("idAsignatura", subjectId);

                long num = db.insert("Tarea", null, tarea);
                db.close();

            } else {

                // Here goes UPDATE operation

            }
            listener.onSuccess();

        } catch (Exception e) {
            listener.onError(e.getMessage());
        }

    }

    @Override
    public void deleteTask(int id, OnDeleteFinishListener listener) {

        if(id > 0) {

            // Here goes DELETE operation

        }
        listener.onDeleteFinish();

    }

}
