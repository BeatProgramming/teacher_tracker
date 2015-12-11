package beatprogramming.github.com.teacker_tracker.persistence;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import beatprogramming.github.com.teacker_tracker.BDHelper;
import beatprogramming.github.com.teacker_tracker.DataSource;
import beatprogramming.github.com.teacker_tracker.ScriptBD;
import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;

/**
 * Implementación en SQLite del acceso a base de datos para manejar datos de Alumno.
 */
public class StudentDaoImpl implements StudentDao {

    private static String TAG = StudentDaoImpl.class.getName();
    private static String STUDENT = "Student";

    @Override
    public void findStudents(OnLoadFinishListener listener) {
        //- Buscar todos los alumnos
        BDHelper bd = BDHelper.getInstance();
        SQLiteDatabase sql = bd.getReadableDatabase();

        sql.query(STUDENT,null,null,null,null,null,null);

        listener.onLoadFinish(DataSource.STUDENT);
    }

    @Override
    public void updateStudent(int id, String name, String surname, String iconPath,
                              OnUpdateFinishListener listener) {
        String NAME = "name";
        String SURNAME = "surname";
        String ICONPATH = "iconPath";

        BDHelper bd = BDHelper.getInstance();
        SQLiteDatabase sql = bd.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME,name);
        values.put(SURNAME,surname);
        values.put(ICONPATH,iconPath);

        try{
            if(id == 0) {
                //- Insertar alumno
                sql.insert(STUDENT,null,values);
            } else {
                //- Actualizar alumno
                String[] selectionArgs = new String[]{Integer.toString(id)};
                sql.update(STUDENT,values, ScriptBD.ID_ALUMNO + "=?",selectionArgs);
            }
            listener.onSuccess();

        } catch (Exception e) {
            listener.onError(e.getMessage());
        }

    }

    @Override
    public void deleteStudent(int id, OnDeleteFinishListener listener) {
        BDHelper bd = BDHelper.getInstance();
        SQLiteDatabase sql = bd.getWritableDatabase();
        if(id > 0) {
            //- Borrar alumno
            String[] selectionArgs = new String[]{Integer.toString(id)};
            sql.delete(STUDENT,ScriptBD.ID_ALUMNO + "=?",selectionArgs);
        }
        listener.onDeleteFinish();

    }
}
