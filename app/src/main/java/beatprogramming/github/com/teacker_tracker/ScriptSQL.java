package beatprogramming.github.com.teacker_tracker;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;


public class ScriptSQL {

    //- Tablas de la BD
    public static final String ASIGNATURA = "Asignatura";
    public static final String EXAMEN = "Examen";
    public static final String TAREA = "Tarea";

    //- Campos de la tabla ASIGNATURA
    public static class ColumnasAsignatura{
        public static final String ID_ASIGNATURA = BaseColumns._ID;
        public static final String NOMBRE_ASIGNATURA = "nombre";
        public static final String CURSO_ASIGNATURA = "curso";
        public static final String DESCRIPCION_ASIGNATURA = "descripcion";
    }

    //- Campos de la tabla EXAMEN
    public static class ColumnasExamen{
        public static final String ID_EXAMEN = BaseColumns._ID;
        public static final String NOMBRE_EXAMEN = "nombre";
        public static final String FECHA_EXAMEN = "fecha";
        public static final String NOMBRE_ASIGNATURA = "nombreAsignatura";
    }

    //- Campos de la tabla TAREA
    public static class ColumnasTarea{
        public static final String ID_TAREA = BaseColumns._ID;
        public static final String HORA_TAREA = "hora";
        public static final String DESCRIPCION_TAREA = "descripcion";
        public static final String AULA_TAREA = "aula";
        public static final String ASIGNATURA_TAREA = "nombreAsignatura";
        public static final String NOTA = "nota";
    }

    //- Creacion tabla ASIGNATURA
    public static final String CREATE_ASIGNATURA_SCRIPT =
            "create table " + ASIGNATURA + "(" +
                    ColumnasAsignatura.ID_ASIGNATURA + " integer primary key autoincrement," +
                    ColumnasAsignatura.NOMBRE_ASIGNATURA + " text not null," +
                    ColumnasAsignatura.CURSO_ASIGNATURA + " text not null," +
                    ColumnasAsignatura.DESCRIPCION_ASIGNATURA + " text not null)";

    //- Creacion tabla Examen
    public static final String CREATE_EXAMEN_SCRIPT =
            "create table " + EXAMEN + "(" +
                    ColumnasExamen.ID_EXAMEN + " integer primary key autoincrement," +
                    ColumnasExamen.NOMBRE_EXAMEN + " text not null," +
                    ColumnasExamen.FECHA_EXAMEN + " text not null," +
                    ColumnasExamen.NOMBRE_ASIGNATURA + " text not null)";

    //- Creación tabla Tarea
    public static final String CREATE_TAREA_SCRIPT =
            "create table " + TAREA + "(" +
                    ColumnasTarea.ID_TAREA + " integer primary key autoincrement," +
                    ColumnasTarea.HORA_TAREA + " text not null," +
                    ColumnasTarea.DESCRIPCION_TAREA + " text not null," +
                    ColumnasTarea.AULA_TAREA + " text not null," +
                    ColumnasTarea.ASIGNATURA_TAREA + " text not null," +
                    ColumnasTarea.NOTA + " text not null)";

    //- Creacion insercion datos asignatura por defecto
    public static final String INSERT_ASIGNATURA_SCRIPT =
            "insert into " + ASIGNATURA + " values (" +
                    "null," +
                    "\"Lengua\"," +
                    "\"1 ESO\"," +
                    "\"Asignatura básica Lengua\")";

    //- Creacion insercion datos examen por defecto
    public static final String INSERT_EXAMEN_SCRIPT =
            "insert into " + EXAMEN + " values (" +
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
                    "\"A120\"," +
                    "\"Lengua\"," +
                    "\"Hoy impartimos la clase de Lengua\")";


    private BDHelper bdHelper;
    private SQLiteDatabase db;

    public ScriptSQL(Context context){
        bdHelper =  new BDHelper(context);
        db = bdHelper.getWritableDatabase();
    }


}
