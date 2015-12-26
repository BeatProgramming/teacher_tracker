package beatprogramming.github.com.teacker_tracker.presenter;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.domain.Schedule;
import beatprogramming.github.com.teacker_tracker.domain.Task;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.persistence.ScheduleDao;
import beatprogramming.github.com.teacker_tracker.persistence.ScheduleDaoImpl;
import beatprogramming.github.com.teacker_tracker.view.TaskView;
import beatprogramming.github.com.teacker_tracker.persistence.TaskDao;
import beatprogramming.github.com.teacker_tracker.persistence.TaskDaoImpl;

/**
 * Responsible object for handling all UI events on behalf of the view.
 */
public class TaskPresenter implements OnLoadFinishListener {

    private static String TAG = TaskPresenter.class.getName();

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
        //scheduleDao.findSchedule(this);
    }

    public void onItemClicked(int position) {
        Task task = view.getTaskFromAdapter(position);
        view.loadTaskUpdateFragment(task);
    }

    @Override
    public void onLoadFinish(List<? extends Serializable> items) {
      /*  List<Schedule> list = scheduleDao.findSchedule(this);
        Schedule sc;
        DateTime dt = new DateTime();
        Boolean [] dias ;
        Task t;
        int diaActual= dt.getDayOfMonth();
        for (int i = 0; i< list.size();i++){
            sc = list.get(i);
            dias = sc.getDias();
            if (dias[diaActual]){
                t = new Task(sc.getSubject().getNombre(), sc.getSubject(), dt);
                items.add(t);
            }
        }*/
        view.setItems((List<Task>) items);
        view.hideLoading();
    }

    public void onCreateTask() {
        view.loadTaskUpdateFragment(null);
    }
}
