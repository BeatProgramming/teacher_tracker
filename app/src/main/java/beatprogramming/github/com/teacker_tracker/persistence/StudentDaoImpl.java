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
import beatprogramming.github.com.teacker_tracker.domain.Subject;
import beatprogramming.github.com.teacker_tracker.util.SecureSetter;

/**
 * Implementación en SQLite del acceso a base de datos para manejar datos de Alumno.
 */
public class StudentDaoImpl implements StudentDao {

    //Tabla objetivo
    private static String STUDENT = "Student";

    //Campos de la tabla student
    private static final String ID = "_id";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String ICONPATH = "iconPath";

    private static final String ALIAS_ID = "studentId";
    private static final String ALIAS_NAME = "studentName";

    //Consultas sql
    private static final String FINDQUERY = "SELECT * FROM " + STUDENT + " ORDER BY surname ASC";

    private static final String FINDBYSUBJECT = "SELECT " + STUDENT + "." + ID + " AS " + ALIAS_ID +
            "," + STUDENT + "." + NAME + " AS " + ALIAS_NAME + "," + SURNAME + "," + ICONPATH +
            " FROM " + STUDENT + " LEFT JOIN Enrollment ON Student._id = Enrollment.studentId LEFT JOIN " +
            "Subject ON Enrollment.subjectId = Subject._id WHERE Subject._id = ? ORDER BY Student.surname ASC";

    //Variables sql
    private static SQLiteDatabase sqldb;
    private static Cursor c;
    private final BDHelper db;

    /**
     * Constructor que inicia el DBHelper
     */
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
                Student s = new Student(c.getString(c.getColumnIndex(NAME)),
                        c.getString(c.getColumnIndex(SURNAME)));
                s.setIconPath(c.getString(c.getColumnIndex(ICONPATH)));
                s.setId(c.getInt(c.getColumnIndex(ID)));
                students.add(s);
            } while (c.moveToNext());
        }
        listener.onLoadFinish(students);
    }

    /**
     * Metodo que devuelve todos los estudiantes matriculados en una determinada asignatura
     *
     * @param listener instancia del listener
     * @param subject asignatura a filtrar
     */
    @Override
    public void findStudentsBySubject(Subject subject, OnLoadFinishListener listener) {

        sqldb = db.getReadableDatabase();
        c = sqldb.rawQuery(FINDBYSUBJECT, new String[]{Integer.toString(subject.getId())});

        //Lista de student
        List students = new ArrayList<Student>();
        if (c.moveToFirst()) {
            do {
                Student s = new Student(c.getString(c.getColumnIndex(ALIAS_NAME)),
                        c.getString(c.getColumnIndex(SURNAME)));
                s.setIconPath(c.getString(c.getColumnIndex(ICONPATH)));
                s.setId(c.getInt(c.getColumnIndex(ALIAS_ID)));
                students.add(s);
            } while (c.moveToNext());
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
    public void updateStudent(int id, String name, String surname, String iconPath,
                              OnUpdateFinishListener listener) {

        sqldb = db.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME, name);
        values.put(SURNAME, surname);
        values.put(ICONPATH, iconPath);

        try {
            if (id == 0) {
                //- Insertar alumno
                sqldb.insert(STUDENT, null, values);
            } else {
                //- Actualizar alumno
                String[] selectionArgs = new String[]{Integer.toString(id)};
                sqldb.update(STUDENT, values, ScriptBD.ID_ALUMNO + "=?", selectionArgs);
            }
            listener.onSuccess();

        } catch (Exception e) {
            listener.onError(e.getMessage());
        }

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
            String[] selectionArgs = new String[]{Integer.toString(id)};
            sql.delete(STUDENT, ScriptBD.ID_ALUMNO + "=?", selectionArgs);
        }
        listener.onDeleteFinish();

    }
}
