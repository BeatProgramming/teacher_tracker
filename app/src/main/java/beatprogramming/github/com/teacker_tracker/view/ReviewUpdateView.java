package beatprogramming.github.com.teacker_tracker.view;

import android.support.v4.app.DialogFragment;

import java.util.List;

import beatprogramming.github.com.teacker_tracker.domain.Subject;

/**
 * Created by malkomich on 02/12/2015.
 */
public interface ReviewUpdateView {

    void loadReviewFragment();

    void setError(String message);

    void setSubjectItems(List<Subject> items);

    void setSubjectId(int id);

    void setTypeValue(String type);

    void showDialog(DialogFragment fragment);

    void setTaskDate(String dateString);

    void setTaskTime(String timeString);
}
