package beatprogramming.github.com.teacker_tracker.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.BDHelper;
import beatprogramming.github.com.teacker_tracker.DataSource;
import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;
import beatprogramming.github.com.teacker_tracker.domain.Subject;

/**
 * Implementación en SQLite del acceso a base de datos para manejar datos de Asignatura.
 */
public class SubjectDaoImpl implements SubjectDao {

    private static String TAG = SubjectDaoImpl.class.getName();

    //Contantes de los campos de la tabla subject
    private static final String SUBJECT = "Subject";
    private static final String FINDQUERY = "SELECT * FROM Subject";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String COURSE = "course";
    private static final String CAMPOID = "_id=?";

    private final BDHelper db = BDHelper.getInstance();
    private static SQLiteDatabase sqldb;
    private static Cursor c;

    /**
     * Metodo que recupera todas las subjects de la base de datos
     * @param listener
     */
    @Override
    public void findSubjects(OnLoadFinishListener listener) {

        sqldb = db.getWritableDatabase();
        c = sqldb.rawQuery(FINDQUERY, null);

        //Lista de subjects
        List subjects = new ArrayList<Subject>();
        if(c.moveToFirst()){
            do{
                Subject s = new Subject(c.getString(c.getColumnIndex(NAME)),
                        c.getString(c.getColumnIndex(DESCRIPTION)),
                        c.getString(c.getColumnIndex(COURSE)));
                subjects.add(s);
            }while(c.moveToNext());
        }
        listener.onLoadFinish(subjects);
    }

    /**
     * Metodo que actualiza una subject de la base de datos.
     * Si el id=0, quiere decir que no esta creada en la base de datos y en lugar de actualizar
     * se crea una nueva subject
     * @param id
     * @param name
     * @param description
     * @param course
     * @param classRoom
     * @param listener
     */
    @Override
    public void updateSubject(int id, String name, String description, String course, String classRoom, OnUpdateFinishListener listener) {

        try{
            sqldb = db.getWritableDatabase();

            ContentValues subjects = new ContentValues();
            subjects.put(NAME, name);
            subjects.put(DESCRIPTION, description);
            subjects.put(COURSE, course);

            if(id == 0) {
                sqldb.insert(SUBJECT, null, subjects);
            } else {
                String[] x = new String[]{String.valueOf(id)};
                sqldb.update(SUBJECT, subjects, CAMPOID, x);
            }
            listener.onSuccess();

        } catch (Exception e) {
            listener.onError(e.getMessage());
        }

    }

    /**
     * Metodo que borra una subject de la base de datos
     * @param id
     * @param listener
     */
    @Override
    public void deleteSubject(int id, OnDeleteFinishListener listener) {

        sqldb = db.getWritableDatabase();
        if(id > 0) {
            String[] value = new String[]{String.valueOf(id)};
            sqldb.delete(SUBJECT, CAMPOID, value);
        }
        listener.onDeleteFinish();

    }
}
