package beatprogramming.github.com.teacker_tracker.callback;

/**
 * Delegates the actions to happen in the view after a data UPDATE operation has been done.
 */
public interface OnUpdateFinishListener {

    void onError(String message);

    void onSuccess();

}
