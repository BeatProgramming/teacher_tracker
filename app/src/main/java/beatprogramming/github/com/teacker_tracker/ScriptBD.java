package beatprogramming.github.com.teacker_tracker;

import android.provider.BaseColumns;

public class ScriptBD {

    //- Tablas de la BD
    public static final String ASIGNATURA = "Subject";
    public static final String ALUMNO = "Student";
    public static final String EVALUACION = "Review";
    public static final String CALIFICACION = "Score";
    public static final String TAREA = "Task";
    public static final String HORARIO = "Schedule";

    //- Campos tabla ASIGNATURA
    public static final String ID_ASIGNATURA = BaseColumns._ID;
    public static final String NOMBRE_ASIGNATURA = "name";
    public static final String DESCRIPCION_ASIGNATURA = "description";
    public static final String CURSO_ASIGNATURA = "course";

    //- Campos tabla ALUMNO
    public static final String ID_ALUMNO = BaseColumns._ID;
    public static final String NOMBRE_ALUMNO = "name";
    public static final String APELLIDO_ALUMNO = "surname";
    public static final String ICONO_ALUMNO = "iconPath";

    //- Campos tabla EVALUACION
    public static final String ID_EVALUACION = BaseColumns._ID;
    public static final String NOMBRE_EVALUACION = "name";
    public static final String ID_ASIGNATURA_EVALUACION = "subjectId";
    public static final String FECHA_EVALUACION = "dateTime";
    public static final String TIPO_EVALUACION = "type";

    //- Campos tabla CALIFICACION
    public static final String ID_CALIFICACION = BaseColumns._ID;
    public static final String CALIFICACION_CALIFICACION = "calification";
    public static final String COMENTARIO_CALIFICACION = "comment";
    public static final String ID_EVALUACION_CALIFICACION = "reviewId";
    public static final String ID_ALUMNO_CALIFICACION = "studentId";

    //- Campo tabla TAREA
    public static final String ID_TAREA = BaseColumns._ID;
    public static final String NOMBRE_TAREA = "name";
    public static final String ID_ASIGNATURA_TAREA = "subjectId";
    public static final String FECHA_TAREA = "dateTime";
    public static final String NOTA_TAREA = "note";

    //- Campo tabla HORARIO
    public static final String ID_HORARIO = BaseColumns._ID;
    public static final String ID_ASIGNATURA_HORARIO = "subjectId";
    public static final String FECHA_HORARIO = "dateTime";
    public static final String CLASE_HORARIO = "classroom";
    public static final String DIAS_HORARIO = "days";


    //- Creacion tabla ASIGNATURA
    public static final String ASIGNATURA_SCRIPT =
            "create table " + ASIGNATURA + "(" +
                    ID_ASIGNATURA + " integer primary key autoincrement," +
                    NOMBRE_ASIGNATURA + " text not null," +
                    DESCRIPCION_ASIGNATURA + " text," +
                    CURSO_ASIGNATURA + " text not null)";

    //- Creacion tabla ALUMNO
    public static final String ALUMNO_SCRIPT =
            "create table " + ALUMNO + "(" +
                    ID_ALUMNO + " integer primary key autoincrement," +
                    NOMBRE_ALUMNO + " text not null," +
                    APELLIDO_ALUMNO + " text not null," +
                    ICONO_ALUMNO + " text null)";

    //- Creacion tabla EVALUACION
    public static final String EVALUACION_SCRIPT =
            "create table " + EVALUACION + "(" +
                    ID_EVALUACION + " integer primary key autoincrement," +
                    NOMBRE_EVALUACION + " text not null," +
                    ID_ASIGNATURA_EVALUACION + " integer," +
                    FECHA_EVALUACION + " integer," +
                    TIPO_EVALUACION + " text not null," +
                    "foreign key (" + ID_ASIGNATURA_EVALUACION + ") references " + ASIGNATURA + "(" + ID_ASIGNATURA + "))";

    //- Creacion tabla CALIFICACION
    public static final String CALIFICACION_SCRIPT =
            "create table " + CALIFICACION + "(" +
                    ID_CALIFICACION + " integer primary key autoincrement," +
                    CALIFICACION_CALIFICACION + " real," +
                    COMENTARIO_CALIFICACION + " text," +
                    ID_EVALUACION_CALIFICACION + " integer," +
                    ID_ALUMNO_CALIFICACION + " integer," +
                    "foreign key (" + ID_EVALUACION_CALIFICACION + ") references " + EVALUACION + "(" + ID_EVALUACION + ")," +
                    "foreign key (" + ID_ALUMNO_CALIFICACION + ") references " + ALUMNO + "(" + ID_ALUMNO_CALIFICACION + "))";

    //- Creacion tabla TAREA
    public static final String TAREA_SCRIPT =
            "create table " + TAREA + "(" +
                    ID_TAREA + " integer primary key autoincrement," +
                    NOMBRE_TAREA + " text not null," +
                    ID_ASIGNATURA_TAREA + " integer," +
                    FECHA_TAREA + " integer," +
                    NOTA_TAREA + " text," +
                    "foreign key (" + ID_ASIGNATURA_TAREA + ") references " + ASIGNATURA + "(" + ID_ASIGNATURA + "))";

    //- Creacion tabla HORARIO
    public static final String HORARIO_SCRIPT =
            "create table " + HORARIO + "(" +
                    ID_HORARIO + " integer primary key autoincrement," +
                    ID_ASIGNATURA_HORARIO + " integer," +
                    FECHA_HORARIO + " text," +
                    CLASE_HORARIO + " text null," +
                    DIAS_HORARIO + " text null," +
                    "foreign key (" + ID_ASIGNATURA_HORARIO + ") references " + ASIGNATURA + "(" + ID_ASIGNATURA + "))";

    //- Creacion insercion datos asignatura por defecto
    public static final String INSERT_ASIGNATURA1_SCRIPT =
            "insert into " + ASIGNATURA + " values (" +
                    "null," +
                    "\"Lengua\"," +
                    "\"Asignatura básica Lengua\"," +
                    "\"1 ESO\")";

    public static final String INSERT_ASIGNATURA2_SCRIPT =
            "insert into " + ASIGNATURA + " values (" +
                    "null," +
                    "\"Matematicas\"," +
                    "\"Asignatura básica Matematicas\"," +
                    "\"1 ESO\")";

    //- Creacion insercion datos examen por defecto
    public static final String INSERT_REVIEW1_SCRIPT =
            "insert into " + EVALUACION + " values (" +
                    "null," +
                    "\"Primer parcial\"," +
                    "1," +
                    "\"1451474455580\"," +
                    "\"Exam\")";

    public static final String INSERT_REVIEW2_SCRIPT =
            "insert into " + EVALUACION + " values (" +
                    "null," +
                    "\"Segundo parcial\"," +
                    "1," +
                    "1451474455501," +
                    "\"Exam\")";

    //- Creación insercion datos tarea por defecto
    public static final String INSERT_TAREA1_SCRIPT =
            "insert into " + TAREA + " values (" +
                    "null," +
                    "\"A101\"," +
                    "1," +
                    "1451474455520," +
                    "\"Nueva nota\")";

    //- Creación insercion datos tarea por defecto
    public static final String INSERT_TAREA2_SCRIPT =
            "insert into " + TAREA + " values (" +
                    "null," +
                    "\"A102\"," +
                    "2," +
                    "1451474455300," +
                    "\"Nueva nota\")";

    //- Creación insercion datos tarea por defecto
    public static final String INSERT_TAREA3_SCRIPT =
            "insert into " + TAREA + " values (" +
                    "null," +
                    "\"A101\"," +
                    "1," +
                    "1451474455501," +
                    "null)";

    //- Creación insercion datos alumno por defecto
    public static final String INSERT_ALUMNO1_SCRIPT =
            "insert into " + ALUMNO + " values (" +
                    "null," +
                    "\"Adrian\"," +
                    "\"Martin\"," +
                    "null)";

    public static final String INSERT_ALUMNO2_SCRIPT =
            "insert into " + ALUMNO + " values (" +
                    "null," +
                    "\"Oscar\"," +
                    "\"Fernandez\"," +
                    "null)";

    //- Creación insercion datos calificaciones por defecto
    public static final String INSERT_CALIFICACION1_SCRIPT =
            "insert into " + CALIFICACION + " values (" +
                "null," +
                "7.5," +
                "\"Muchas faltas de ortografia\"," +
                "1," +
                "1)";

    public static final String INSERT_CALIFICACION2_SCRIPT =
            "insert into " + CALIFICACION + " values (" +
                    "null," +
                    "4," +
                    "\"NOOOOOOB\"," +
                    "1," +
                    "2)";

    public static final String INSERT_CALIFICACION3_SCRIPT =
            "insert into " + CALIFICACION + " values (" +
                    "null," +
                    "1," +
                    "\"NOOOOOOB\"," +
                    "2," +
                    "1)";

    public static final String INSERT_CALIFICACION4_SCRIPT =
            "insert into " + CALIFICACION + " values (" +
                    "null," +
                    "8," +
                    "\"NOOOOOOB\"," +
                    "2," +
                    "2)";

    //- Creación insercion datos horario por defecto
    public static final String INSERT_SCHEDULE1_SCRIPT =
            "insert into " + HORARIO + " values (" +
                    "null," +
                    "1," +
                    "\"8:00\"," +
                    "\"A101\"," +
                    "\"LXJV\")";

    public static final String INSERT_SCHEDULE2_SCRIPT =
            "insert into " + HORARIO + " values (" +
                    "null," +
                    "2," +
                    "\"10:00\"," +
                    "\"A101\"," +
                    "\"LXJV\")";

}
