package beatprogramming.github.com.teacker_tracker.view;
import android.support.v4.app.DialogFragment;

/**
 * - Define los métodos  sobre los que tiene control el presentador que conecta
 *   la capa de persistencia con la vista.
 */
public interface SubjectUpdateView {

    void setTaskTime(String timeString);

    void loadSubjectFragment();

    void showDialog(DialogFragment fragment);

    void setError(String message);

}
