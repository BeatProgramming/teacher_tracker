package beatprogramming.github.com.teacker_tracker.persistence;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import beatprogramming.github.com.teacker_tracker.database.BDHelper;
import beatprogramming.github.com.teacker_tracker.database.ProviderDB;
import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;
import beatprogramming.github.com.teacker_tracker.domain.Subject;

/**
 * - Implementación en SQLite del acceso a base de datos para manejar datos de asignaturas
 */
public class SubjectDaoImpl implements SubjectDao {

    private static String TAG = SubjectDaoImpl.class.getName();

    private static final String FINDQUERY = "SELECT * FROM " + ProviderDB.SUBJECT_TABLE;

    private final BDHelper db;
    private static SQLiteDatabase sqldb;
    private static Cursor c;

    public SubjectDaoImpl() {
        db = BDHelper.getInstance();
    }

    /**
     * Metodo que recupera todas las subjects de la base de datos
     * @param listener instancia del listener
     */
    @Override
    public void findSubjects(OnLoadFinishListener listener) {

        sqldb = db.getWritableDatabase();
        c = sqldb.rawQuery(FINDQUERY, null);

        //Lista de subjects
        List subjects = new ArrayList<Subject>();
        if(c.moveToFirst()){
            do{
                Subject s = new Subject(c.getString(c.getColumnIndex(ProviderDB.SUBJECT_NAME)),
                        c.getString(c.getColumnIndex(ProviderDB.SUBJECT_DESCRIPTION)),
                        c.getString(c.getColumnIndex(ProviderDB.SUBJECT_COURSE)));
                s.setId(c.getInt(c.getColumnIndex(ProviderDB.SUBJECT_ID)));
                subjects.add(s);
            }while(c.moveToNext());
        }
        listener.onLoadFinish(subjects);
    }

    /**
     * Metodo que actualiza una subject de la base de datos.
     * Si el id=0, quiere decir que no esta creada en la base de datos y en lugar de actualizar
     * se crea una nueva subject
     * @param id id de la asignatura
     * @param name nombre de la asignatura
     * @param description descripcion de la asignatura
     * @param course curso de la asignatura
     * @param listener instancia del listener
     */
    @Override
    public int updateSubject(int id, String name, String description, String course, OnUpdateFinishListener listener) {

        try{
            sqldb = db.getWritableDatabase();

            ContentValues subjects = new ContentValues();
            subjects.put(ProviderDB.SUBJECT_NAME, name);
            subjects.put(ProviderDB.SUBJECT_DESCRIPTION, description);
            subjects.put(ProviderDB.SUBJECT_COURSE, course);

            if(id == 0) {
                id = (int) sqldb.insert(ProviderDB.SUBJECT_TABLE, null, subjects);
            } else {
                String[] x = new String[]{String.valueOf(id)};
                sqldb.update(ProviderDB.SUBJECT_TABLE, subjects, ProviderDB.SUBJECT_ID + "=?", x);
            }
            listener.onSuccess();

        } catch (Exception e) {
            id = -1;
            listener.onError(e.getMessage());
        }

        return id;

    }

    /**
     * Metodo que actualiza una subject de la base de datos.
     * Si el id=0, quiere decir que no esta creada en la base de datos y en lugar de actualizar
     * se crea una nueva subject
     * @param subject
     */
    @Override
    public int updateSubject(Subject subject, OnUpdateFinishListener listener) {

        return updateSubject(subject.getId(), subject.getNombre(), subject.getDescripcion(), subject.getCurso(),
            listener);

//        sqldb = db.getWritableDatabase();
//        for(Student student : subject.getStudentList()) {
//
//            try {
//                ContentValues enrollments = new ContentValues();
//                enrollments.put(ENROLLMENT_SUBJECT, subject.getId());
//                enrollments.put(ENROLLMENT_STUDENT, student.getId());
//
//                sqldb.insert(ENROLLMENT_TABLE, null, enrollments);
//
//            } catch (Exception e) {
//                continue;
//            }
//        }

    }

    /**
     * Metodo que borra una subject de la base de datos
     * @param id id de la asignatura a borrar
     * @param listener instancia del listener
     */
    @Override
    public void deleteSubject(int id, OnDeleteFinishListener listener) {

        //IMPORTANTE BORRAR EN CASCADA
        sqldb = db.getWritableDatabase();
        if(id > 0) {
            String[] value = new String[]{String.valueOf(id)};
            sqldb.delete(ProviderDB.SUBJECT_TABLE, ProviderDB.SUBJECT_ID + "=?", value);
        }
        listener.onDeleteFinish();

    }

}
