package beatprogramming.github.com.teacker_tracker.presenter;

import org.joda.time.DateTime;

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
 * - Controlador de las tareas
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

    public void reloadItems(DateTime dateTime) {
        view.showLoading();
        taskDao.findTasks(dateTime, this);
        scheduleDao.findSchedule(dateTime, this);
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
