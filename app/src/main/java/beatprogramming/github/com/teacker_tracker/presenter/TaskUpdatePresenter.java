package beatprogramming.github.com.teacker_tracker.presenter;

import org.joda.time.DateTime;

import beatprogramming.github.com.teacker_tracker.callback.OnDateTimePickedListener;
import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;
import beatprogramming.github.com.teacker_tracker.fragments.DatePickerFragment;
import beatprogramming.github.com.teacker_tracker.fragments.TimePickerFragment;
import beatprogramming.github.com.teacker_tracker.persistence.TaskDao;
import beatprogramming.github.com.teacker_tracker.persistence.TaskDaoImpl;
import beatprogramming.github.com.teacker_tracker.util.DateTimeFormatter;
import beatprogramming.github.com.teacker_tracker.view.TaskUpdateView;

/**
 * Responsible object for handling all UI events on behalf of the view.
 */
public class TaskUpdatePresenter implements OnUpdateFinishListener, OnDeleteFinishListener, OnDateTimePickedListener {

    private static String TAG = TaskUpdatePresenter.class.getName();

    private TaskUpdateView view;
    private TaskDao taskDao;

    public TaskUpdatePresenter(TaskUpdateView view) {
        this.view = view;
        taskDao = new TaskDaoImpl();
    }

    public void submit(int id, String description, String dateTimeString, int subjectId) {
        DateTime dateTime = DateTimeFormatter.stringToDateTime(dateTimeString);
        taskDao.updateTask(id, description, dateTime, subjectId, this);
    }

    public void delete(int id) {
        taskDao.deleteTask(id, this);
    }

    @Override
    public void onError(String message) {
        view.setError(message);
    }

    @Override
    public void onSuccess() {
        view.goBack();
    }

    @Override
    public void onDeleteFinish() {
        view.goBack();
    }

    public void showDatePicker() {
        DatePickerFragment fragment = DatePickerFragment.newInstance(this);
        view.showDialog(fragment);
    }

    public void showTimePicker() {
        TimePickerFragment fragment = TimePickerFragment.newInstance(this);
        view.showDialog(fragment);
    }

    @Override
    public void onDatePicked(int year, int month, int day) {
        view.setTaskDate(DateTimeFormatter.dateToString(year, month, day));
    }

    @Override
    public void onTimePicked(int hour, int minute) {
        view.setTaskTime(DateTimeFormatter.timeToString(hour, minute));
    }
}