package beatprogramming.github.com.teacker_tracker.presenter;

import android.util.Log;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.callback.OnDateTimePickedListener;
import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;
import beatprogramming.github.com.teacker_tracker.domain.Subject;
import beatprogramming.github.com.teacker_tracker.domain.Task;
import beatprogramming.github.com.teacker_tracker.fragments.DatePickerFragment;
import beatprogramming.github.com.teacker_tracker.fragments.TimePickerFragment;
import beatprogramming.github.com.teacker_tracker.persistence.SubjectDao;
import beatprogramming.github.com.teacker_tracker.persistence.SubjectDaoImpl;
import beatprogramming.github.com.teacker_tracker.persistence.TaskDao;
import beatprogramming.github.com.teacker_tracker.persistence.TaskDaoImpl;
import beatprogramming.github.com.teacker_tracker.util.DateTimeFormatter;
import beatprogramming.github.com.teacker_tracker.view.TaskUpdateView;

/**
 * Responsible object for handling all UI events on behalf of the view.
 */
public class TaskUpdatePresenter implements OnUpdateFinishListener, OnDeleteFinishListener, OnDateTimePickedListener, OnLoadFinishListener {

    private static String TAG = TaskUpdatePresenter.class.getName();

    private TaskUpdateView view;
    private TaskDao taskDao;
    private SubjectDao subjectDao;

    public TaskUpdatePresenter(TaskUpdateView view) {
        this.view = view;
        taskDao = new TaskDaoImpl();
        subjectDao = new SubjectDaoImpl();

    }

    public void onResume() {
        subjectDao.findSubjects(this);
    }

    public void submit(int id, String name, String dateTimeString, int subjectId) {

        if (name.equals("") || subjectId == 0)
            view.setError("Completa todos los campos");
        else {
            DateTime dateTime = DateTimeFormatter.stringToDateTime(dateTimeString);
            taskDao.updateTask(id, name, subjectId, dateTime, this);
        }
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

    @Override
    public void onLoadFinish(List<? extends Serializable> items) {
        view.setSubjectItems((List<Subject>) items);
    }

    public void fillView(Task task) {

        DateTime dateTime = new DateTime();

        if (task != null) {

            view.setTaskId(task.getId());
            dateTime = task.getDateTime();

            view.setTaskName(task.getNombre());

            int subjectId = task.getSubject().getId();
            view.setSubject(subjectId);
        }

        onDatePicked(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth());
        onTimePicked(dateTime.getHourOfDay(), dateTime.getMinuteOfHour());

    }

    public void onSubjectSelected(Object item) {

        if (item != null) {
            Subject subject = (Subject) item;
            view.setSubjectId(subject.getId());
        } else {
            Log.d(TAG, "onSubjectSelected, nothing selected.");
            // ACCION CUANDO NO HAY ASIGNATURA SELECIONADA.
        }
    }
}