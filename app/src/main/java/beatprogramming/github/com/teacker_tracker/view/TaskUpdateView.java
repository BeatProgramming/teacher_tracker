package beatprogramming.github.com.teacker_tracker.view;
import android.support.v4.app.DialogFragment;

import java.util.List;

import beatprogramming.github.com.teacker_tracker.domain.Subject;

/**
 * - Define los métodos  sobre los que tiene control el presentador que conecta
 *   la capa de persistencia con la vista.
 */
public interface TaskUpdateView {

    void goBack();

    void setError(String message);

    void setSubjectItems(List<Subject> items);

    void setSubjectId(int id);

    void showDialog(DialogFragment fragment);

    void setTaskDate(String dateString);

    void setTaskTime(String timeString);

    void setTaskId(int id);

    void setTaskName(String name);

    void setSubject(int subjectId);
}
