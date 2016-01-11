package beatprogramming.github.com.teacker_tracker.callback;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

/**
 * - Define los métodos para cambiar un fragmento
 */
public interface FragmentCallback {

    void replaceFragment(Fragment fragment);

    void goBack();

    void showDialog(DialogFragment fragment);

}
