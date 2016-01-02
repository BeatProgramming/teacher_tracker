package beatprogramming.github.com.teacker_tracker.view;

import android.support.v4.app.DialogFragment;

/**
 * Created by malkomich on 02/12/2015.
 */
public interface SubjectUpdateView {

    void loadSubjectFragment();

    void showDialog(DialogFragment fragment);

    void setError(String message);

}
