package beatprogramming.github.com.teacker_tracker.presenter;

import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;
import beatprogramming.github.com.teacker_tracker.persistence.StudentDao;
import beatprogramming.github.com.teacker_tracker.persistence.StudentDaoImpl;
import beatprogramming.github.com.teacker_tracker.view.StudentUpdateView;

/**
 * Responsible object for handling all UI events on behalf of the view.
 */
public class StudentUpdatePresenter implements OnUpdateFinishListener, OnDeleteFinishListener {

    private static String TAG = StudentUpdatePresenter.class.getName();

    private StudentUpdateView view;
    private StudentDao studentDao;

    public StudentUpdatePresenter(StudentUpdateView view) {
        this.view = view;
        studentDao = new StudentDaoImpl();
    }

    public void submit(int id, String name, String surname, String iconPath) {

        if(name.equals("") || surname.equals(""))
            view.setError("Completa todos los campos");
        else {
            if (iconPath.equals(""))
                iconPath = null;
            studentDao.updateStudent(id, name, surname, iconPath, this);
        }
    }

    public void delete(int id) {
        studentDao.deleteStudent(id, this);
    }

    @Override
    public void onError(String message) {
        view.setError(message);
    }

    @Override
    public void onSuccess() {
        view.loadStudentFragment();
    }

    @Override
    public void onDeleteFinish() {
        view.loadStudentFragment();
    }

}
