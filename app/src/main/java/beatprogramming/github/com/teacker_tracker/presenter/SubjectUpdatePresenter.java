package beatprogramming.github.com.teacker_tracker.presenter;

import beatprogramming.github.com.teacker_tracker.callback.OnDateTimePickedListener;
import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;
import beatprogramming.github.com.teacker_tracker.fragments.TimePickerFragment;
import beatprogramming.github.com.teacker_tracker.persistence.ScheduleDao;
import beatprogramming.github.com.teacker_tracker.persistence.ScheduleDaoImpl;
import beatprogramming.github.com.teacker_tracker.util.DateTimeFormatter;
import beatprogramming.github.com.teacker_tracker.view.SubjectUpdateView;
import beatprogramming.github.com.teacker_tracker.persistence.SubjectDao;
import beatprogramming.github.com.teacker_tracker.persistence.SubjectDaoImpl;

/**
 * - Controlador de la actualización de las asignaturas
 */
public class SubjectUpdatePresenter implements OnUpdateFinishListener, OnDeleteFinishListener, OnDateTimePickedListener {

    private SubjectUpdateView view;
    private SubjectDao subjectDao;
    private ScheduleDao scheduleDao;

    public SubjectUpdatePresenter(SubjectUpdateView view) {
        this.view = view;
        subjectDao = new SubjectDaoImpl();
        scheduleDao = new ScheduleDaoImpl();
    }

    public void submit(int idSubject, String name, String description, String course,
                       int idSchedule, String time,Boolean[] days, String classroom) {

        if(name.equals("") || description.equals("") || course.equals(""))
            view.setError("Completa todos los campos");
        else {
            int subjectId = subjectDao.updateSubject(idSubject, name, description, course, this);
            scheduleDao.updateSchedule(idSchedule, subjectId, time, classroom, days, this);
        }
    }

    public void delete(int id) {
        subjectDao.deleteSubject(id, this);
    }

    @Override
    public void onError(String message) {
        view.setError(message);
    }

    @Override
    public void onSuccess() {
        view.loadSubjectFragment();
    }

    public void showTimePicker() {
        TimePickerFragment fragment = TimePickerFragment.newInstance(this);
        view.showDialog(fragment);
    }
    @Override
    public void onDeleteFinish() {
        view.loadSubjectFragment();
    }

    @Override
    public void onTimePicked(int hour, int minute) {
        view.setTaskTime(DateTimeFormatter.timeToString(hour, minute));
    }
    @Override
    public void onDatePicked(int year, int month, int day) {

    }

}
