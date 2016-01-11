package beatprogramming.github.com.teacker_tracker.callback;
import java.io.Serializable;
import java.util.List;

/**
 * - Define los métodos del evento de actualización de registros
 */
public interface OnLoadFinishListener {

    void onLoadFinish(List<? extends Serializable> items);
}
