package beatprogramming.github.com.teacker_tracker.view;


import android.support.v4.app.DialogFragment;

/**
 * Created by malkomich on 02/12/2015.
 */
public interface TaskUpdateView {

    void goBack();

    void setError(String message);

    void showDialog(DialogFragment fragment);

    void setTaskDate(String dateString);

    void setTaskTime(String timeString);

}
