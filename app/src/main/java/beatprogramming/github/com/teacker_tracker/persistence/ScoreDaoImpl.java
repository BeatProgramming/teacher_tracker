package beatprogramming.github.com.teacker_tracker.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.database.BDHelper;
import beatprogramming.github.com.teacker_tracker.database.ProviderDB;
import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.domain.Review;
import beatprogramming.github.com.teacker_tracker.domain.Score;
import beatprogramming.github.com.teacker_tracker.domain.Student;

/**
 * - Implementación en SQLite del acceso a base de datos para manejar datos de calificaciones
 */
public class ScoreDaoImpl implements ScoreDao {

    private static String TAG = ScoreDaoImpl.class.getName();

    // Aliases
    private static final String STUDENT_NAME_ALIAS = "nameStudent";
    private static final String STUDENT_ID_ALIAS = "theStudent";
    private static final String SUBJECT_ID_ALIAS = "theSubject";

    // Review Types
    public static final String EXAM = "Exam";
    public static final String PROJECT = "Project";

    private static final String ENROLLMENT_QUERY =
        "SELECT " + ProviderDB.SUBJECT_TABLE + "." + ProviderDB.SUBJECT_ID + " AS " + SUBJECT_ID_ALIAS + "," +
            ProviderDB.ENROLLMENT_TABLE + "." + ProviderDB.ENROLLMENT_STUDENT_ID + " AS " + STUDENT_ID_ALIAS +
            " FROM " + ProviderDB.ENROLLMENT_TABLE + " LEFT JOIN " + ProviderDB.SUBJECT_TABLE + " ON " +
            ProviderDB.ENROLLMENT_TABLE + "." + ProviderDB.ENROLLMENT_SUBJECT_ID + "=" + ProviderDB.SUBJECT_TABLE +
            "." + ProviderDB.SUBJECT_ID;

    private static final String STUDENT_QUERY =
        "SELECT " + ProviderDB.STUDENT_TABLE + "." + ProviderDB.STUDENT_NAME + " AS " + STUDENT_NAME_ALIAS + "," +
            ProviderDB.STUDENT_SURNAME + ", " + ProviderDB.STUDENT_ICON + ", enrollmentJoin.* FROM " +
            ProviderDB.STUDENT_TABLE + " LEFT JOIN (" + ENROLLMENT_QUERY + ") AS enrollmentJoin ON " +
            ProviderDB.STUDENT_TABLE + "." + ProviderDB.STUDENT_ID + "=enrollmentJoin." + STUDENT_ID_ALIAS;

    private static final String SCORE_QUERY = "SELECT " + ProviderDB.SCORE_TABLE + "." + ProviderDB.SCORE_VALUE + "," +
        ProviderDB.SCORE_TABLE + "." + ProviderDB.SCORE_COMMENT + "," + ProviderDB.SCORE_STUDENT_ID + " FROM " +
        ProviderDB.SCORE_TABLE + " WHERE " + ProviderDB.SCORE_REVIEW_ID + "=?";

    private static final String FIND_BY_REVIEW = "SELECT * FROM (" + STUDENT_QUERY + ") AS studentQuery " +
        " LEFT JOIN (" + SCORE_QUERY + ") AS scoreJoin ON studentQuery." + STUDENT_ID_ALIAS + "=scoreJoin." +
        ProviderDB.SCORE_STUDENT_ID + " WHERE studentQuery." + SUBJECT_ID_ALIAS + "= ? ORDER BY studentQuery." +
        ProviderDB.STUDENT_SURNAME;

    private static SQLiteDatabase sqldb;
    private static Cursor cursor;
    private final BDHelper db;

    public ScoreDaoImpl() {
        db = BDHelper.getInstance();
    }

    /**
     * Metodo que actualiza una calificacion en la base de datos.
     * Si el id=0, quiere decir que no esta creada en la base de datos y en lugar de actualizar
     * se crea una nueva calificacion
     *
     * @param calification calificacion numerica de la calificacion
     * @param comment      comentario de la calificacion
     * @param reviewId     id del review asociado a la calificacion
     * @param studentId    id del estudiante asociado a la calificacion
     */
    @Override
    public void updateScore(float calification, String comment, int reviewId, int studentId) {

        sqldb = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        float calificationRounded = (float) (Math.round(calification * 100.0) / 100.0);
        values.put(ProviderDB.SCORE_VALUE, calificationRounded);
        values.put(ProviderDB.SCORE_COMMENT, comment);
        values.put(ProviderDB.SCORE_REVIEW_ID, reviewId);
        values.put(ProviderDB.SCORE_STUDENT_ID, studentId);

        sqldb.replace(ProviderDB.SCORE_TABLE, ProviderDB.SCORE_VALUE, values);
    }

    /**
     * Metodo que borra una calificacion de la base de datos.
     *
     * @param id       id de la calificacion a borrar
     * @param listener instancia del listener
     */
    @Override
    public void deleteScore(int id, OnDeleteFinishListener listener) {
        sqldb = db.getWritableDatabase();
        if (id > 0) {
            //- Borrar alumno
            String[] selectionArgs = new String[]{ Integer.toString(id) };
            sqldb.delete(ProviderDB.SCORE_TABLE, ProviderDB.SCORE_ID + "=?", selectionArgs);
        }
        listener.onDeleteFinish();
    }

    /**
     * Metodo que busca todas las score de un review concreto
     *
     * @param review   review por el que se realiza la busqueda
     * @param listener instancia del listener
     */
    @Override
    public void findScoreByReview(Review review, OnLoadFinishListener listener) {

        sqldb = db.getReadableDatabase();

        cursor = sqldb.rawQuery(FIND_BY_REVIEW,
            new String[]{ Integer.toString(review.getId()), Integer.toString(review.getSubject().getId()) });
        //Lista de reviews
        List scores = new ArrayList<Score>();
        if (cursor.moveToFirst()) {
            do {
                Student student = new Student(cursor.getString(cursor.getColumnIndex(STUDENT_NAME_ALIAS)),
                    cursor.getString(cursor.getColumnIndex(ProviderDB.STUDENT_SURNAME)));
                student.setIconPath(cursor.getString(cursor.getColumnIndex(ProviderDB.STUDENT_ICON)));
                student.setId(cursor.getInt(cursor.getColumnIndex(STUDENT_ID_ALIAS)));

                int calificationIndex = cursor.getColumnIndex(ProviderDB.SCORE_VALUE);
                Float scoreValue = !cursor.isNull(calificationIndex) ? cursor.getFloat(calificationIndex) : null;
                Log.d(TAG, "findScoreByReview, scoreValue: " + scoreValue);
                Score score =
                    new Score(scoreValue, cursor.getString(cursor.getColumnIndex(ProviderDB.SCORE_COMMENT)), student,
                        review);
                scores.add(score);

            }
            while (cursor.moveToNext());
        }


        Log.d(TAG, "findScoreByReview, numero de scores: " + scores.size());

        listener.onLoadFinish(scores);
    }

    @Override
    public void updateScore(Score score) {
        updateScore(score.getCalificacion(), score.getComentario(), score.getReviewId(), score.getStudentId());
    }
}
