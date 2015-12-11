package beatprogramming.github.com.teacker_tracker;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;


public class ScriptSQL {

    //- Tablas de la BD
    public static final String ASIGNATURA = "Subject";
    public static final String ALUMNO = "Student";
    public static final String EVALUACION = "Review";
    public static final String CALIFICACION = "Score";
    public static final String TAREA = "Task";
    public static final String HORARIO = "Schedule";

    //- Campos de la tabla ASIGNATURA
    public static class ColumnasAsignatura{
        public static final String ID_ASIGNATURA = BaseColumns._ID;
        public static final String NOMBRE_ASIGNATURA = "nombre";
        public static final String CURSO_ASIGNATURA = "curso";
        public static final String DESCRIPCION_ASIGNATURA = "descripcion";
    }

    //- Campos de la tabla EVALUACION
    public static class ColumnasEvaluacion {
        public static final String ID_REVIEW = BaseColumns._ID;
        public static final String NOMBRE_REVIEW = "nombre";
        public static final String FECHA_REVIEW = "fecha";
        public static final String NOMBRE_ASIGNATURA = "nombreAsignatura";
    }

    //- Campos de la tabla TAREA
    public static class ColumnasTarea{
        public static final String ID_TAREA = BaseColumns._ID;
        public static final String HORA_TAREA = "hora";
        public static final String DESCRIPCION_TAREA = "descripcion";
        public static final String ASIGNATURA_TAREA = "idAsignatura";
        public static final String NOTA = "nota";
    }

    //- Creacion tabla ASIGNATURA
    public static final String CREATE_ASIGNATURA_SCRIPT =
            "create table " + ASIGNATURA + "(" +
                    ColumnasAsignatura.ID_ASIGNATURA + " integer primary key autoincrement," +
                    ColumnasAsignatura.NOMBRE_ASIGNATURA + " text not null," +
                    ColumnasAsignatura.CURSO_ASIGNATURA + " text not null," +
                    ColumnasAsignatura.DESCRIPCION_ASIGNATURA + " text not null)";

    //- Creacion tabla Review
    public static final String CREATE_EVALUACION_SCRIPT =
            "create table " + EVALUACION + "(" +
                    ColumnasEvaluacion.ID_REVIEW + " integer primary key autoincrement," +
                    ColumnasEvaluacion.NOMBRE_REVIEW + " text not null," +
                    ColumnasEvaluacion.FECHA_REVIEW + " text not null," +
                    ColumnasEvaluacion.NOMBRE_ASIGNATURA + " text not null)";

    //- Creación tabla Tarea
    public static final String CREATE_TAREA_SCRIPT =
            "create table " + TAREA + "(" +
                    ColumnasTarea.ID_TAREA + " integer primary key autoincrement," +
                    ColumnasTarea.HORA_TAREA + " text not null," +
                    ColumnasTarea.DESCRIPCION_TAREA + " text not null," +
                    ColumnasTarea.ASIGNATURA_TAREA + " integer not null," +
                    ColumnasTarea.NOTA + " text not null)";

    //- Creacion insercion datos asignatura por defecto
    public static final String INSERT_ASIGNATURA_SCRIPT =
            "insert into " + ASIGNATURA + " values (" +
                    "null," +
                    "\"Lengua\"," +
                    "\"1 ESO\"," +
                    "\"Asignatura básica Lengua\")";

    //- Creacion insercion datos examen por defecto
    public static final String INSERT_REVIEW_SCRIPT =
            "insert into " + EVALUACION + " values (" +
                    "null," +
                    "\"Primer parcial\"," +
                    "\"20151010\"," +
                    "\"Lengua\")";

    //- Creación insercion datos tarea por defecto
    public static final String INSERT_TAREA_SCRIPT =
            "insert into " + TAREA + " values (" +
                    "null," +
                    "\"8:00\"," +
                    "\"Tema 2\"," +
                    "0," +
                    "\"Hoy impartimos la clase de Lengua\")";


    private BDHelper bdHelper;
    private SQLiteDatabase db;

    public ScriptSQL(Context context){
        bdHelper =  new BDHelper(context);
        db = bdHelper.getWritableDatabase();

    }


}
