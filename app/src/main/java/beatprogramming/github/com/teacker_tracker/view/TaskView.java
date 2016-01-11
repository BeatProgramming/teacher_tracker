package beatprogramming.github.com.teacker_tracker.view;
import java.io.Serializable;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.domain.Task;

/**
 * - Define los métodos  sobre los que tiene control el presentador que conecta
 *   la capa de persistencia con la vista.
 */
public interface TaskView {

    void setItems(List<Serializable> items);

    void loadTaskUpdateFragment(Task task);

    Serializable getTaskFromAdapter(int position);

    void showLoading();

    void hideLoading();

    void makeToast();
}
