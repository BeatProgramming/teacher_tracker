package beatprogramming.github.com.teacker_tracker.view;
import android.support.v4.app.DialogFragment;

import java.util.List;

import beatprogramming.github.com.teacker_tracker.domain.Subject;

/**
 * - Define los métodos  sobre los que tiene control el presentador que conecta
 *   la capa de persistencia con la vista.
 */
public interface ReviewUpdateView {

    void loadReviewFragment();

    void setError(String message);

    void setSubjectItems(List<Subject> items);

    void setSubjectId(int id);

    void setTypeValue(String type);

    void showDialog(DialogFragment fragment);

    void setReviewDate(String dateString);

    void setReviewTime(String timeString);

    void setReviewId(int id);

    void setReviewName(String name);

    void setSubject(int subjectId);

    void selectType(int examType);

}
