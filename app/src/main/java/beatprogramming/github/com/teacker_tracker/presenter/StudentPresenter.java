package beatprogramming.github.com.teacker_tracker.presenter;

import java.io.Serializable;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.domain.Student;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.view.StudentView;
import beatprogramming.github.com.teacker_tracker.persistence.StudentDao;
import beatprogramming.github.com.teacker_tracker.persistence.StudentDaoImpl;

/**
 * Responsible object for handling all UI events on behalf of the view.
 */
public class StudentPresenter implements OnLoadFinishListener {

    private static String TAG = StudentPresenter.class.getName();

    private StudentView view;
    private StudentDao subjectDao;

    public StudentPresenter(StudentView view) {
        this.view = view;
        subjectDao = new StudentDaoImpl();
    }

    public void onResume() {
        view.showLoading();
        subjectDao.findStudents(this);
    }

    public void onItemClicked(int position) {
        Student student = view.getStudentFromAdapter(position);
        view.loadStudentUpdateFragment(student);
    }

    @Override
    public void onLoadFinish(List<? extends Serializable> items) {
        view.setItems((List<Student>) items);
        view.hideLoading();
    }

    public void onCreateStudent() {
        view.loadStudentUpdateFragment(null);
    }
}
