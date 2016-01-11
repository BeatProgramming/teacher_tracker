package beatprogramming.github.com.teacker_tracker.view;

import java.io.Serializable;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.domain.Task;

/** Interfaz
 * Created by malkomich on 01/12/2015.
 */
public interface TaskView {

    void setItems(List<Serializable> items);

    void loadTaskUpdateFragment(Task task);

    Serializable getTaskFromAdapter(int position);

    void showLoading();

    void hideLoading();

    void makeToast();
}
