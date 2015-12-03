package beatprogramming.github.com.teacker_tracker.presenter;

import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;
import beatprogramming.github.com.teacker_tracker.persistence.TaskDao;
import beatprogramming.github.com.teacker_tracker.persistence.TaskDaoImpl;
import beatprogramming.github.com.teacker_tracker.view.TaskUpdateView;

/**
 * Responsible object for handling all UI events on behalf of the view.
 */
public class TaskUpdatePresenter implements OnUpdateFinishListener, OnDeleteFinishListener {

    private static String TAG = TaskUpdatePresenter.class.getName();

    private TaskUpdateView view;
    private TaskDao taskDao;

    public TaskUpdatePresenter(TaskUpdateView view) {
        this.view = view;
        taskDao = new TaskDaoImpl();
    }

    // SUBMIT METHOD HERE

    public void delete(int id) {
        taskDao.deleteTask(id, this);
    }

    @Override
    public void onError(String message) {
        view.setError(message);
    }

    @Override
    public void onSuccess() {
        view.loadTaskFragment();
    }

    @Override
    public void onDeleteFinish() {
        view.loadTaskFragment();
    }
}
