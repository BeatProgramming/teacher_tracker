package beatprogramming.github.com.teacker_tracker.database;

import android.provider.BaseColumns;

/**
 * Clase que contiene los scripts necesarios para la creacion de la base de datos
 * de la aplicación. Contiene valor por defecto para las tablas
 */
public class ProviderDB {

    //- BD Table names
    public static final String SUBJECT_TABLE = "subject";
    public static final String STUDENT_TABLE = "student";
    public static final String REVIEW_TABLE = "review";
    public static final String SCORE_TABLE = "score";
    public static final String TASK_TABLE = "task";
    public static final String SCHEDULE_TABLE = "schedule";
    public static final String ENROLLMENT_TABLE = "enrollment";

    //- SUBJECT table fields
    public static final String SUBJECT_ID = BaseColumns._ID;
    public static final String SUBJECT_NAME = "name";
    public static final String SUBJECT_DESCRIPTION = "description";
    public static final String SUBJECT_COURSE = "course";

    //- STUDENT table fields
    public static final String STUDENT_ID = BaseColumns._ID;
    public static final String STUDENT_NAME = "name";
    public static final String STUDENT_SURNAME = "surname";
    public static final String STUDENT_ICON = "iconPath";

    //- REVIEW table fields
    public static final String REVIEW_ID = BaseColumns._ID;
    public static final String REVIEW_NAME = "name";
    public static final String REVIEW_SUBJECT_ID = "subjectId";
    public static final String REVIEW_DATE = "dateTime";
    public static final String REVIEW_TYPE = "type";

    //- SCORE table fields
    public static final String SCORE_ID = BaseColumns._ID;
    public static final String SCORE_VALUE = "value";
    public static final String SCORE_COMMENT = "comment";
    public static final String SCORE_REVIEW_ID = "reviewId";
    public static final String SCORE_STUDENT_ID = "studentId";

    //- TASK table fields
    public static final String TASK_ID = BaseColumns._ID;
    public static final String TASK_NAME = "name";
    public static final String TASK_SUBJECT_ID = "subjectId";
    public static final String TASK_DATE = "dateTime";
    public static final String TASK_NOTE = "note";

    //- SCHEDULE table fields
    public static final String SCHEDULE_ID = BaseColumns._ID;
    public static final String SCHEDULE_SUBJECT_ID = "subjectId";
    public static final String SCHEDULE_DATE = "dateTime";
    public static final String SCHEDULE_CLASSROOM = "classroom";
    public static final String SCHEDULE_DAYS = "days";

    //- ENROLLMENT table fields
    public static final String ENROLLMENT_SUBJECT_ID = "subjectId";
    public static final String ENROLLMENT_STUDENT_ID = "studentId";

    //- Creation of SUBJECT table
    public static final String SUBJECT_CREATE =
            "CREATE TABLE " + SUBJECT_TABLE + "(" +
                    SUBJECT_ID + " integer primary key autoincrement," +
                    SUBJECT_NAME + " text not null," +
                    SUBJECT_DESCRIPTION + " text," +
                    SUBJECT_COURSE + " text not null)";

    //- Creation of STUDENT table
    public static final String STUDENT_CREATE =
            "CREATE TABLE " + STUDENT_TABLE + "(" +
                    STUDENT_ID + " integer primary key autoincrement," +
                    STUDENT_NAME + " text not null," +
                    STUDENT_SURNAME + " text not null," +
                    STUDENT_ICON + " text null," +
                    "unique(" + STUDENT_NAME + "," + STUDENT_SURNAME + "))";

    //- Creation of REVIEW table
    public static final String REVIEW_CREATE =
            "CREATE TABLE " + REVIEW_TABLE + "(" +
                    REVIEW_ID + " integer primary key autoincrement," +
                    REVIEW_NAME + " text not null," +
                    REVIEW_SUBJECT_ID + " integer," +
                    REVIEW_DATE + " integer," +
                    REVIEW_TYPE + " text not null," +
                    "foreign key (" + REVIEW_SUBJECT_ID + ") references " + SUBJECT_TABLE + "(" + SUBJECT_ID + ") ON DELETE CASCADE)";

    //- Creation of SCORE table
    public static final String SCORE_CREATE =
            "CREATE TABLE " + SCORE_TABLE + "(" +
                    SCORE_VALUE + " real," +
                    SCORE_COMMENT + " text," +
                    SCORE_REVIEW_ID + " integer," +
                    SCORE_STUDENT_ID + " integer," +
                    "foreign key (" + SCORE_REVIEW_ID + ") references " + REVIEW_TABLE + "(" + REVIEW_ID + ") ON DELETE CASCADE," +
                    "foreign key (" + SCORE_STUDENT_ID + ") references " + STUDENT_TABLE + "(" + STUDENT_ID + ") ON DELETE CASCADE," +
                    "primary key (" + SCORE_REVIEW_ID + "," + SCORE_STUDENT_ID + ") )";

    //- Creation of TASK table
    public static final String TASK_CREATE =
            "CREATE TABLE " + TASK_TABLE + "(" +
                    TASK_ID + " integer primary key autoincrement," +
                    TASK_NAME + " text not null," +
                    TASK_SUBJECT_ID + " integer," +
                    TASK_DATE + " integer," +
                    TASK_NOTE + " text," +
                    "foreign key (" + TASK_SUBJECT_ID + ") references " + SUBJECT_TABLE + "(" + SUBJECT_ID + ") ON DELETE CASCADE)";

    //- Creation of SCHEDULE table
    public static final String SCHEDULE_CREATE =
            "CREATE TABLE " + SCHEDULE_TABLE + "(" +
                    SCHEDULE_ID + " integer primary key autoincrement," +
                    SCHEDULE_SUBJECT_ID + " integer," +
                    SCHEDULE_DATE + " text," +
                    SCHEDULE_CLASSROOM + " text null," +
                    SCHEDULE_DAYS + " text null," +
                    "foreign key (" + SCHEDULE_SUBJECT_ID + ") references " + SUBJECT_TABLE + "(" + SUBJECT_ID + ") ON DELETE CASCADE)";

    //- Creation of ENROLLMENT table
    public static final String ENROLLMENT_CREATE =
            "CREATE TABLE " + ENROLLMENT_TABLE + "(" +
                    ENROLLMENT_SUBJECT_ID + " integer," +
                    ENROLLMENT_STUDENT_ID + " integer," +
                    "foreign key (" + ENROLLMENT_SUBJECT_ID + ") references " + SUBJECT_TABLE + "(" + SUBJECT_ID + ") ON DELETE CASCADE," +
                    "foreign key (" + ENROLLMENT_STUDENT_ID + ") references " + STUDENT_TABLE + "(" + STUDENT_ID + ") ON DELETE CASCADE," +
                    "primary key (" + ENROLLMENT_SUBJECT_ID + ", " + ENROLLMENT_STUDENT_ID + ") )";

    //- Deletion of the SUBJECT table
    public static final String DROP_ASIGNATURA =
            "DROP TABLE IF EXISTS " + SUBJECT_TABLE;

    //- Deletion of the STUDENT table
    public static final String DROP_ALUMNO =
            "DROP TABLE IF EXISTS " + STUDENT_TABLE;

    //- Deletion of the REVIEW table
    public static final String DROP_EVALUACION =
            "DROP TABLE IF EXISTS " + REVIEW_TABLE;

    //- Deletion of the SCORE table
    public static final String DROP_CALIFICACION =
            "DROP TABLE IF EXISTS " + SCORE_TABLE;

    //- Deletion of the TASK table
    public static final String DROP_TAREA =
            "DROP TABLE IF EXISTS " + TASK_TABLE;

    //- Deletion of the SCHEDULE table
    public static final String DROP_HORARIO =
            "DROP TABLE IF EXISTS " + SCHEDULE_TABLE;

    //- Deletion of the ENROLLMENT table
    public static final String DROP_MATRICULA =
            "DROP TABLE IF EXISTS " + ENROLLMENT_TABLE;

    //-  Inserccion de datos de asignaturas
    public static final String INSERT_ASIGNATURA1_SCRIPT =
            "INSERT INTO " + SUBJECT_TABLE + " values (" +
                    "null," +
                    "\"Lengua\"," +
                    "\"Asignatura básica Lengua\"," +
                    "\"1 ESO\")";

    public static final String INSERT_ASIGNATURA2_SCRIPT =
            "INSERT INTO " + SUBJECT_TABLE + " values (" +
                    "null," +
                    "\"Matematicas\"," +
                    "\"Asignatura básica Matematicas\"," +
                    "\"1 ESO\")";

    //-  Inserccion de datos de evaluación
    public static final String INSERT_REVIEW1_SCRIPT =
            "INSERT INTO " + REVIEW_TABLE + " values (" +
                    "null," +
                    "\"Primer parcial\"," +
                    "1," +
                    "\"1451474455580\"," +
                    "\"Exam\")";

    public static final String INSERT_REVIEW2_SCRIPT =
            "INSERT INTO " + REVIEW_TABLE + " values (" +
                    "null," +
                    "\"Segundo parcial\"," +
                    "1," +
                    "1451474455501," +
                    "\"Exam\")";

    //-  Inserccion de datos de tareas
    public static final String INSERT_TAREA1_SCRIPT =
            "INSERT INTO " + TASK_TABLE + " values (" +
                    "null," +
                    "\"A101\"," +
                    "1," +
                    "1451474455520," +
                    "\"Nueva nota\")";

    public static final String INSERT_TAREA2_SCRIPT =
            "INSERT INTO " + TASK_TABLE + " values (" +
                    "null," +
                    "\"A102\"," +
                    "2," +
                    "1451474455300," +
                    "\"Nueva nota\")";

    public static final String INSERT_TAREA3_SCRIPT =
            "INSERT INTO " + TASK_TABLE + " values (" +
                    "null," +
                    "\"A101\"," +
                    "1," +
                    "1451474455501," +
                    "null)";

    //-  Inserccion de datos de alumnos
    public static final String INSERT_ALUMNO1_SCRIPT =
            "INSERT INTO " + STUDENT_TABLE + " values (" +
                    "null," +
                    "\"Adrian\"," +
                    "\"Martin\"," +
                    "null)";

    public static final String INSERT_ALUMNO2_SCRIPT =
            "INSERT INTO " + STUDENT_TABLE + " values (" +
                    "null," +
                    "\"Oscar\"," +
                    "\"Fernandez\"," +
                    "null)";

    //-  Inserccion de datos de calificaciones
    public static final String INSERT_CALIFICACION1_SCRIPT =
            "INSERT INTO " + SCORE_TABLE + " values (" +
                    "7.5," +
                    "\"Muchas faltas de ortografia\"," +
                    "1," +
                    "1)";

    public static final String INSERT_CALIFICACION2_SCRIPT =
            "INSERT INTO " + SCORE_TABLE + " values (" +
                    "4," +
                    "\"NOOOOOOB\"," +
                    "1," +
                    "2)";

    public static final String INSERT_CALIFICACION3_SCRIPT =
            "INSERT INTO " + SCORE_TABLE + " values (" +
                    "8," +
                    "\"NOOOOOOB\"," +
                    "2," +
                    "2)";

    //- Inserccion de datos de matriculas
    public static final String INSERT_MATRICULA1_SCRIPT =
            "INSERT INTO " + ENROLLMENT_TABLE + " values (" +
                    "1," +
                    "1)";

    public static final String INSERT_MATRICULA2_SCRIPT =
            "INSERT INTO " + ENROLLMENT_TABLE + " values (" +
                    "1," +
                    "2)";

    public static final String INSERT_MATRICULA3_SCRIPT =
            "INSERT INTO " + ENROLLMENT_TABLE + " values (" +
                    "2," +
                    "2)";

    //- Inserccion de datos de horarios
    public static final String INSERT_SCHEDULE1_SCRIPT =
            "INSERT INTO " + SCHEDULE_TABLE + " values (" +
                    "null," +
                    "1," +
                    "\"8:00\"," +
                    "\"A101\"," +
                    "\"LMXJV\")";

    public static final String INSERT_SCHEDULE2_SCRIPT =
            "INSERT INTO " + SCHEDULE_TABLE + " values (" +
                    "null," +
                    "2," +
                    "\"10:00\"," +
                    "\"A101\"," +
                    "\"LMXJVSD\")";

}
