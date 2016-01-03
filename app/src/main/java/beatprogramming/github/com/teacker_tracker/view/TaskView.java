package beatprogramming.github.com.teacker_tracker.view;

import java.util.List;

import beatprogramming.github.com.teacker_tracker.domain.Task;

/** Interfaz
 * Created by malkomich on 01/12/2015.
 */
public interface TaskView {

    void setItems(List<Task> items);

    void loadTaskUpdateFragment(Task task);

    Task getTaskFromAdapter(int position);

    void showLoading();

    void hideLoading();

}
