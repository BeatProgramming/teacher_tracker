package beatprogramming.github.com.teacker_tracker.callback;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

/**
 * Handler for the fragment transactions. It delegates the creation, replacement and other
 * actions of the fragments to a unique class implementing it.
 */
public interface FragmentCallback {

    void replaceFragment(Fragment fragment);
    void goBack();
    void showDialog(DialogFragment fragment);

}
