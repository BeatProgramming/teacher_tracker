package beatprogramming.github.com.teacker_tracker.callback;

import java.io.Serializable;
import java.util.List;

/**
 * Delegates the actions to happen in the view after a data GET operation has been done.
 */
public interface OnLoadFinishListener {

    void onLoadFinish(List<? extends Serializable> items);
}
