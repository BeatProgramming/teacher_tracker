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
                    ICONO_ALUMNO + " text not null)";

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
                    FECHA_HORARIO + " integer," +
                    CLASE_HORARIO + " text not null," +
                    "foreign key (" + ID_ASIGNATURA_HORARIO + ") references " + ASIGNATURA + "(" + ID_ASIGNATURA + "))";
}
