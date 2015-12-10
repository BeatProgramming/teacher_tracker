package beatprogramming.github.com.teacker_tracker.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.EditText;

import beatprogramming.github.com.teacker_tracker.BDHelper;
import beatprogramming.github.com.teacker_tracker.DataSource;
import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;

/**
 * Implementación en SQLite del acceso a base de datos para manejar datos de Asignatura.
 */
public class SubjectDaoImpl implements SubjectDao {

    private static String TAG = SubjectDaoImpl.class.getName();

    private final BDHelper databaseHelper = BDHelper.getInstance();

    @Override
    public void findSubjects(OnLoadFinishListener listener) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        //- Campos que queremos obtener al realizar la query
        String[] campos = new String[] {"nombre"};

        //- Ejecución de la query
//        Cursor c = db.query("Asignatura", campos, null, null, null, null, null);
//
//        if(c.moveToFirst()){
//            do{
//                String id = c.getString(0);
//                String nombre = c.getString(1);
//                String curso = c.getString(2);
//                Log.i("Clase", "NOMBRE: " + nombre + " CURSO: " + curso + " ID: " + id);
//            }while(c.moveToNext());
//        }

        listener.onLoadFinish(DataSource.SUBJECT);
    }

    @Override
    public void updateSubject(int id, String name, String description, String course, String classRoom, OnUpdateFinishListener listener) {

        try{
            if(id == 0) {

//                SQLiteDatabase db = databaseHelper.getWritableDatabase();
//
//                ContentValues asignatura = new ContentValues();
//                asignatura.put(NOMBRE,name);
//                asignatura.put(CURSO,course);
//                asignatura.put(DESCRIPCION,description);
//
//                //- Creación de la asignatura
//                db.insert(ASIGNATURA, null, asignatura);
//                db.close();

            } else {

//                SQLiteDatabase db = databaseHelper.getWritableDatabase();
//
//                ContentValues values = new ContentValues();
//                values.put("nombre",name);
//                values.put("descripcion",description);
//                values.put("curso",course);
//                String[] x = new String[]{name};
//
//                db.update("Asignatura",values,"nombre=?",x);
//                db.close();

            }
            throw new Exception("HOLA QUE TAL");
//            listener.onSuccess();

        } catch (Exception e) {
            listener.onError(e.getMessage());
        }

    }

    @Override
    public void deleteSubject(int id, OnDeleteFinishListener listener) {

        if(id > 0) {

            SQLiteDatabase db = databaseHelper.getWritableDatabase();

//            String[] values = new String[]{};

//            db.delete("Asignatura", "nombre=?", values);
            db.close();

        }
        listener.onDeleteFinish();

    }
}
