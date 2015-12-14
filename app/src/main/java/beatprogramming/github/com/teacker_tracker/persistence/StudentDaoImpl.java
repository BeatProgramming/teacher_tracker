package beatprogramming.github.com.teacker_tracker.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.BDHelper;
import beatprogramming.github.com.teacker_tracker.ScriptBD;
import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;
import beatprogramming.github.com.teacker_tracker.domain.Student;

/**
 * Implementación en SQLite del acceso a base de datos para manejar datos de Alumno.
 */
public class StudentDaoImpl implements StudentDao {

    private static String TAG = StudentDaoImpl.class.getName();
    private static String STUDENT = "Student";
    private static final String FINDQUERY = "SELECT * FROM Student";
    private static String NAME = "name";
    private static String SURNAME = "surname";
    private static String ICONPATH = "iconPath";

    //Variables sql
    private static SQLiteDatabase sqldb;
    private static Cursor c;
    private final BDHelper db;

    public StudentDaoImpl() {
        db = BDHelper.getInstance();
    }

    /**
     * Metodo que devuelve todos los estudiantes de la base de datos
     * @param listener
     */
    @Override
    public void findStudents(OnLoadFinishListener listener) {
        //- Buscar todos los alumnos
        sqldb = db.getReadableDatabase();
        c = sqldb.rawQuery(FINDQUERY,null);

        //Lista de student
        List students = new ArrayList<Student>();
        if(c.moveToFirst()){
            do{
                Student s =  new Student(c.getString(c.getColumnIndex(NAME)),
                            c.getString(c.getColumnIndex(SURNAME)));
                s.setIconPath(c.getString(c.getColumnIndex(ICONPATH)));
                students.add(s);
            }while(c.moveToNext());
        }
        listener.onLoadFinish(students);
        //listener.onLoadFinish(DataSource.STUDENT);
    }

    /**
     * Metodo que actualiza un estudiante en la base de datos.
     * Si el id=0, quiere decir que no esta creada en la base de datos y en lugar de actualizar
     * se crea un nuevo estudiante
     * @param id
     * @param name
     * @param surname
     * @param iconPath
     * @param listener
     */
    @Override
    public void updateStudent(int id, String name, String surname, String iconPath,
                              OnUpdateFinishListener listener) {

        sqldb = db.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME,name);
        values.put(SURNAME,surname);
        values.put(ICONPATH,iconPath);

        try{
            if(id == 0) {
                //- Insertar alumno
                sqldb.insert(STUDENT,null,values);
            } else {
                //- Actualizar alumno
                String[] selectionArgs = new String[]{Integer.toString(id)};
                sqldb.update(STUDENT,values, ScriptBD.ID_ALUMNO + "=?",selectionArgs);
            }
            listener.onSuccess();

        } catch (Exception e) {
            listener.onError(e.getMessage());
        }

    }

    /**
     * Metodo que borra un estudiante de la base de datos.
     * @param id
     * @param listener
     */
    @Override
    public void deleteStudent(int id, OnDeleteFinishListener listener) {
        BDHelper bd = BDHelper.getInstance();
        SQLiteDatabase sql = bd.getWritableDatabase();
        if(id > 0) {
            //- Borrar alumno
            String[] selectionArgs = new String[]{Integer.toString(id)};
            sql.delete(STUDENT, ScriptBD.ID_ALUMNO + "=?", selectionArgs);
        }
        listener.onDeleteFinish();

    }
}
