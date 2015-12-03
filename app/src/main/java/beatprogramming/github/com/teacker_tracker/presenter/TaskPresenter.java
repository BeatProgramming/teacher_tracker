package beatprogramming.github.com.teacker_tracker.presenter;

import java.io.Serializable;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.domain.Task;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.view.TaskView;
import beatprogramming.github.com.teacker_tracker.persistence.TaskDao;
import beatprogramming.github.com.teacker_tracker.persistence.TaskDaoImpl;

/**
 * Responsible object for handling all UI events on behalf of the view.
 */
public class TaskPresenter implements OnLoadFinishListener {

    private static String TAG = TaskPresenter.class.getName();

    private TaskView view;
    private TaskDao subjectDao;

    public TaskPresenter(TaskView view) {
        this.view = view;
        subjectDao = new TaskDaoImpl();
    }

    public void onResume() {
        subjectDao.findTasks(this);
    }

    public void onItemClicked(int position) {

    }

    @Override
    public void onLoadFinish(List<? extends Serializable> items) {
        view.setItems((List<Task>) items);
    }

    public void onFloatingButtonClick() {

    }
}