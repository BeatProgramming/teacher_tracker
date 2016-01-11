package beatprogramming.github.com.teacker_tracker.presenter;

import java.io.Serializable;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.domain.Task;
import beatprogramming.github.com.teacker_tracker.persistence.ScheduleDao;
import beatprogramming.github.com.teacker_tracker.persistence.ScheduleDaoImpl;
import beatprogramming.github.com.teacker_tracker.persistence.TaskDao;
import beatprogramming.github.com.teacker_tracker.persistence.TaskDaoImpl;
import beatprogramming.github.com.teacker_tracker.view.TaskView;

/**
 * Responsible object for handling all UI events on behalf of the view.
 */
public class TaskPresenter implements OnLoadFinishListener {


    private TaskView view;
    private TaskDao taskDao;
    private ScheduleDao scheduleDao;



    public TaskPresenter(TaskView view) {
        this.view = view;
        taskDao = new TaskDaoImpl();
        scheduleDao = new ScheduleDaoImpl();
    }

    public void onResume() {
        view.showLoading();
        taskDao.findTasks(this);
        scheduleDao.findSchedule(this);
    }

      public void onItemClicked(int position) {
        Serializable serializable = view.getTaskFromAdapter(position);
        if (serializable instanceof Task) {
            Task task = (Task) serializable;
            view.loadTaskUpdateFragment(task);
        } else{
            view.makeToast();
        }
    }

    @Override
    public void onLoadFinish(List<? extends Serializable> items) {
        view.setItems((List<Serializable>) items);
        view.hideLoading();
    }

    public void onCreateTask() {
        view.loadTaskUpdateFragment(null);
    }
}
