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
        db.execSQL(ScriptSQL.CREATE_ASIGNATURA_SCRIPT);
        db.execSQL(ScriptSQL.CREATE_EVALUACION_SCRIPT);
        db.execSQL(ScriptSQL.CREATE_TAREA_SCRIPT);

        //- Insertar datos por defecto
        db.execSQL(ScriptSQL.INSERT_ASIGNATURA_SCRIPT);
        db.execSQL(ScriptSQL.INSERT_REVIEW_SCRIPT);
        db.execSQL(ScriptSQL.INSERT_TAREA_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Actualizacion base de datos
    }

}
