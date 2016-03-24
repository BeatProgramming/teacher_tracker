package beatprogramming.github.com.teacker_tracker.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.database.BDHelper;
import beatprogramming.github.com.teacker_tracker.database.ProviderDB;
import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;
import beatprogramming.github.com.teacker_tracker.domain.Student;
import beatprogramming.github.com.teacker_tracker.domain.Subject;

/**
 * - Implementación en SQLite del acceso a base de datos para manejar datos de estudiantes
 */
public class StudentDaoImpl implements StudentDao {

    private final String TAG = StudentDaoImpl.class.getName();

    // Aliases
    private static final String STUDENT_ID_ALIAS = "studentId";
    private static final String STUDENT_NAME_ALIAS = "studentName";

    private static final String FINDQUERY =
        "SELECT * FROM " + ProviderDB.STUDENT_TABLE + " ORDER BY " + ProviderDB.STUDENT_SURNAME + " ASC";

    private static final String FINDBYSUBJECT =
        "SELECT " + ProviderDB.STUDENT_TABLE + "." + ProviderDB.STUDENT_ID + " AS " +

            STUDENT_ID_ALIAS +
            "," + ProviderDB.STUDENT_TABLE + "." + ProviderDB.STUDENT_NAME + " AS " + STUDENT_NAME_ALIAS + "," +
            ProviderDB.STUDENT_SURNAME + "," + ProviderDB.STUDENT_ICON + " FROM " + ProviderDB.STUDENT_TABLE +
            " LEFT JOIN " + ProviderDB.ENROLLMENT_TABLE + " ON " + ProviderDB.STUDENT_TABLE + "." +
            ProviderDB.STUDENT_ID + "=" + ProviderDB.ENROLLMENT_TABLE + "." + ProviderDB.ENROLLMENT_STUDENT_ID +
            " LEFT JOIN " + ProviderDB.SUBJECT_TABLE + " ON " + ProviderDB.ENROLLMENT_TABLE + "." +
            ProviderDB.ENROLLMENT_SUBJECT_ID + "=" + ProviderDB.SUBJECT_TABLE + "." + ProviderDB.SUBJECT_ID +
            " WHERE " + ProviderDB.SUBJECT_TABLE + "." + ProviderDB.SUBJECT_ID + "= ? ORDER BY " +
            ProviderDB.STUDENT_TABLE + "." + ProviderDB.STUDENT_SURNAME + " ASC";

    private static final String FIND_ID =
        "SELECT " + ProviderDB.STUDENT_ID + " FROM " + ProviderDB.STUDENT_TABLE + " WHERE " + ProviderDB.STUDENT_NAME +
            "=? AND " + ProviderDB.STUDENT_SURNAME + "=?";

    private static SQLiteDatabase sqldb;
    private static Cursor c;
    private final BDHelper db;

    public StudentDaoImpl() {
        db = BDHelper.getInstance();
    }

    /**
     * Metodo que devuelve todos los estudiantes de la base de datos
     *
     * @param listener instancia del listener
     */
    @Override
    public void findStudents(OnLoadFinishListener listener) {
        //- Buscar todos los alumnos
        sqldb = db.getReadableDatabase();
        c = sqldb.rawQuery(FINDQUERY, null);

        //Lista de student
        List students = new ArrayList<Student>();
        if (c.moveToFirst()) {
            do {
                Student s = new Student(c.getString(c.getColumnIndex(ProviderDB.STUDENT_NAME)),
                    c.getString(c.getColumnIndex(ProviderDB.STUDENT_SURNAME)));
                s.setIconPath(c.getString(c.getColumnIndex(ProviderDB.STUDENT_ICON)));
                s.setId(c.getInt(c.getColumnIndex(ProviderDB.STUDENT_ID)));
                students.add(s);
            }
            while (c.moveToNext());
        }
        listener.onLoadFinish(students);
    }

    /**
     * Metodo que devuelve todos los estudiantes matriculados en una determinada asignatura
     *
     * @param listener instancia del listener
     * @param subject  asignatura a filtrar
     */
    @Override
    public void findStudentsBySubject(Subject subject, OnLoadFinishListener listener) {

        sqldb = db.getReadableDatabase();
        c = sqldb.rawQuery(FINDBYSUBJECT, new String[]{ Integer.toString(subject.getId()) });

        //Lista de student
        List students = new ArrayList<Student>();
        if (c.moveToFirst()) {
            do {
                Student s = new Student(c.getString(c.getColumnIndex(STUDENT_NAME_ALIAS)),
                    c.getString(c.getColumnIndex(ProviderDB.STUDENT_SURNAME)));
                s.setIconPath(c.getString(c.getColumnIndex(ProviderDB.STUDENT_ICON)));
                s.setId(c.getInt(c.getColumnIndex(STUDENT_ID_ALIAS)));
                students.add(s);
            }
            while (c.moveToNext());
        }
        subject.addStudents((Student[]) students.toArray(new Student[students.size()]));
        listener.onLoadFinish(students);
    }

    /**
     * Metodo que actualiza un estudiante en la base de datos.
     * Si el id=0, quiere decir que no esta creada en la base de datos y en lugar de actualizar
     * se crea un nuevo estudiante
     *
     * @param id       id del estudiante
     * @param name     nombre del estudiante
     * @param surname  apellido del estudiante
     * @param iconPath iconPath del estudiante
     * @param listener instancia del listener
     */
    @Override
    public int updateStudent(int id, String name, String surname, String iconPath, int subjectId,
        OnUpdateFinishListener listener) {

        sqldb = db.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ProviderDB.STUDENT_NAME, name);
        values.put(ProviderDB.STUDENT_SURNAME, surname);
        values.put(ProviderDB.STUDENT_ICON, iconPath);

        try {
            if (id == 0) {
                //- Insertar alumno
                id = (int) sqldb.insertOrThrow(ProviderDB.STUDENT_TABLE, null, values);
            } else {
                //- Actualizar alumno
                String[] selectionArgs = new String[]{ Integer.toString(id) };
                sqldb.update(ProviderDB.STUDENT_TABLE, values, ProviderDB.STUDENT_ID + "=?", selectionArgs);
            }

        } catch (SQLiteConstraintException e) {

            sqldb = db.getReadableDatabase();
            c = sqldb.rawQuery(FIND_ID, new String[]{ name, surname });
            if (c.moveToFirst()) {
                id = c.getInt(c.getColumnIndex(ProviderDB.STUDENT_ID));
            }

        }

        sqldb = db.getWritableDatabase();
        ContentValues enrollments = new ContentValues();
        enrollments.put(ProviderDB.ENROLLMENT_SUBJECT_ID, subjectId);
        enrollments.put(ProviderDB.ENROLLMENT_STUDENT_ID, id);

        Log.d(TAG, "updateStudent, enrollment: Subject " + subjectId + ", Student " + id);
        sqldb.insertWithOnConflict(ProviderDB.ENROLLMENT_TABLE, null, enrollments, SQLiteDatabase.CONFLICT_IGNORE);

        listener.onSuccess();

        return id;
    }

    /**
     * Metodo que borra un estudiante de la base de datos.
     *
     * @param id       id del estudiante a borrar
     * @param listener instancia del listener
     */
    @Override
    public void deleteStudent(int id, OnDeleteFinishListener listener) {
        BDHelper bd = BDHelper.getInstance();
        SQLiteDatabase sql = bd.getWritableDatabase();
        if (id > 0) {
            //- Borrar alumno
            String[] selectionArgs = new String[]{ Integer.toString(id) };
            sql.delete(ProviderDB.STUDENT_TABLE, ProviderDB.STUDENT_ID + "=?", selectionArgs);
            Log.d(TAG, "bea tontina");
        }
        listener.onDeleteFinish();

    }
}
