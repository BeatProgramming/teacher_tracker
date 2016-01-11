package beatprogramming.github.com.teacker_tracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * - Crea la BD con el script creado en ScriptBD
 */
public class BDHelper extends SQLiteOpenHelper {

    private static final String TAG = BDHelper.class.getName();
    private static final String DATABASE_NAME = "TEACHER_TRACKER.db";
    private static final int DATABASE_VERSION = 2;
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

    //- Crea la BDD
    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d(TAG, "onCreate");

        //- Limpiar BD
        db.execSQL(ScriptBD.DROP_MATRICULA);
        db.execSQL(ScriptBD.DROP_HORARIO);
        db.execSQL(ScriptBD.DROP_TAREA);
        db.execSQL(ScriptBD.DROP_CALIFICACION);
        db.execSQL(ScriptBD.DROP_EVALUACION);
        db.execSQL(ScriptBD.DROP_ASIGNATURA);
        db.execSQL(ScriptBD.DROP_ALUMNO);

        //- Crear BD
        db.execSQL(ScriptBD.ASIGNATURA_SCRIPT);
        db.execSQL(ScriptBD.ALUMNO_SCRIPT);
        db.execSQL(ScriptBD.EVALUACION_SCRIPT);
        db.execSQL(ScriptBD.CALIFICACION_SCRIPT);
        db.execSQL(ScriptBD.TAREA_SCRIPT);
        db.execSQL(ScriptBD.HORARIO_SCRIPT);
        db.execSQL(ScriptBD.MATRICULA_SCRIPT);

        //- Inserts predefinidos
        db.execSQL(ScriptBD.INSERT_ASIGNATURA1_SCRIPT);
        db.execSQL(ScriptBD.INSERT_ASIGNATURA2_SCRIPT);
        db.execSQL(ScriptBD.INSERT_ALUMNO1_SCRIPT);
        db.execSQL(ScriptBD.INSERT_ALUMNO2_SCRIPT);
        db.execSQL(ScriptBD.INSERT_TAREA1_SCRIPT);
        db.execSQL(ScriptBD.INSERT_TAREA2_SCRIPT);
        db.execSQL(ScriptBD.INSERT_TAREA3_SCRIPT);
        db.execSQL(ScriptBD.INSERT_REVIEW1_SCRIPT);
        db.execSQL(ScriptBD.INSERT_REVIEW2_SCRIPT);
        db.execSQL(ScriptBD.INSERT_CALIFICACION1_SCRIPT);
        db.execSQL(ScriptBD.INSERT_CALIFICACION2_SCRIPT);
        db.execSQL(ScriptBD.INSERT_CALIFICACION3_SCRIPT);
        db.execSQL(ScriptBD.INSERT_MATRICULA1_SCRIPT);
        db.execSQL(ScriptBD.INSERT_MATRICULA2_SCRIPT);
        db.execSQL(ScriptBD.INSERT_MATRICULA3_SCRIPT);
        db.execSQL(ScriptBD.INSERT_SCHEDULE1_SCRIPT);
        db.execSQL(ScriptBD.INSERT_SCHEDULE2_SCRIPT);

    }

    //- Actualiza la BDD
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.d(TAG, "onUpgrade");
        onCreate(db);

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

}
