package beatprogramming.github.com.teacker_tracker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * - Crea la BD con el script creado en ProviderDB
 */
public class BDHelper extends SQLiteOpenHelper {

    private static final String TAG = BDHelper.class.getName();

    private static final String DB_NAME = "TEACHER_TRACKER.db";
    private static final int DB_VERSION = 1;

    private static BDHelper bdHelper;

    public BDHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static void init(Context context) {
        bdHelper = new BDHelper(context);
    }

    public static BDHelper getInstance() {
        return bdHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d(TAG, "onCreate");

        //- Create the DB
        db.execSQL(ProviderDB.SUBJECT_CREATE);
        db.execSQL(ProviderDB.STUDENT_CREATE);
        db.execSQL(ProviderDB.REVIEW_CREATE);
        db.execSQL(ProviderDB.SCORE_CREATE);
        db.execSQL(ProviderDB.TASK_CREATE);
        db.execSQL(ProviderDB.SCHEDULE_CREATE);
        db.execSQL(ProviderDB.ENROLLMENT_CREATE);

        //- Inserts predefinidos
        db.execSQL(ProviderDB.INSERT_ASIGNATURA1_SCRIPT);
        db.execSQL(ProviderDB.INSERT_ASIGNATURA2_SCRIPT);
        db.execSQL(ProviderDB.INSERT_ASIGNATURA3_SCRIPT);
        db.execSQL(ProviderDB.INSERT_ASIGNATURA4_SCRIPT);
        db.execSQL(ProviderDB.INSERT_ALUMNO1_SCRIPT);
        db.execSQL(ProviderDB.INSERT_ALUMNO2_SCRIPT);
        db.execSQL(ProviderDB.INSERT_TAREA1_SCRIPT);
        db.execSQL(ProviderDB.INSERT_TAREA2_SCRIPT);
        db.execSQL(ProviderDB.INSERT_TAREA3_SCRIPT);
        db.execSQL(ProviderDB.INSERT_REVIEW1_SCRIPT);
        db.execSQL(ProviderDB.INSERT_REVIEW2_SCRIPT);
        db.execSQL(ProviderDB.INSERT_REVIEW3_SCRIPT);
        db.execSQL(ProviderDB.INSERT_CALIFICACION1_SCRIPT);
        db.execSQL(ProviderDB.INSERT_CALIFICACION2_SCRIPT);
        db.execSQL(ProviderDB.INSERT_CALIFICACION3_SCRIPT);
        db.execSQL(ProviderDB.INSERT_MATRICULA1_SCRIPT);
        db.execSQL(ProviderDB.INSERT_MATRICULA2_SCRIPT);
        db.execSQL(ProviderDB.INSERT_MATRICULA3_SCRIPT);
        db.execSQL(ProviderDB.INSERT_SCHEDULE1_SCRIPT);
        db.execSQL(ProviderDB.INSERT_SCHEDULE2_SCRIPT);
        db.execSQL(ProviderDB.INSERT_SCHEDULE3_SCRIPT);
        db.execSQL(ProviderDB.INSERT_SCHEDULE4_SCRIPT);

    }

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
