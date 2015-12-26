package beatprogramming.github.com.teacker_tracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class BDHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TEACHER_TRACKER.db";
    private static final int DATABASE_VERSION = 1;

    private static BDHelper bdHelper;

    public BDHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public static void init(Context context) {
        bdHelper = new BDHelper(context);
    }

    public static BDHelper getInstance() {
        return bdHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //- Crear BD
        db.execSQL(ScriptBD.ASIGNATURA_SCRIPT);
        db.execSQL(ScriptBD.ALUMNO_SCRIPT);
        db.execSQL(ScriptBD.EVALUACION_SCRIPT);
        db.execSQL(ScriptBD.CALIFICACION_SCRIPT);
        db.execSQL(ScriptBD.TAREA_SCRIPT);
        db.execSQL(ScriptBD.HORARIO_SCRIPT);

        //- Inserts predefinidos
        db.execSQL(ScriptBD.INSERT_ASIGNATURA1_SCRIPT);
        db.execSQL(ScriptBD.INSERT_ASIGNATURA2_SCRIPT);
        db.execSQL(ScriptBD.INSERT_TAREA1_SCRIPT);
        db.execSQL(ScriptBD.INSERT_TAREA2_SCRIPT);
        db.execSQL(ScriptBD.INSERT_TAREA3_SCRIPT);
        db.execSQL(ScriptBD.INSERT_REVIEW1_SCRIPT);
        db.execSQL(ScriptBD.INSERT_REVIEW2_SCRIPT);
        db.execSQL(ScriptBD.INSERT_ALUMNO1_SCRIPT);
        db.execSQL(ScriptBD.INSERT_ALUMNO2_SCRIPT);
        db.execSQL(ScriptBD.INSERT_CALIFICACION1_SCRIPT);
        db.execSQL(ScriptBD.INSERT_CALIFICACION2_SCRIPT);
        db.execSQL(ScriptBD.INSERT_CALIFICACION3_SCRIPT);
        db.execSQL(ScriptBD.INSERT_CALIFICACION4_SCRIPT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Actualizacion base de datos
    }

}
