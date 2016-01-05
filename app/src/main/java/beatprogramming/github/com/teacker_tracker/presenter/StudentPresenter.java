package beatprogramming.github.com.teacker_tracker.presenter;

import android.util.Log;

import java.io.Serializable;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.domain.Student;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.domain.Subject;
import beatprogramming.github.com.teacker_tracker.persistence.SubjectDao;
import beatprogramming.github.com.teacker_tracker.persistence.SubjectDaoImpl;
import beatprogramming.github.com.teacker_tracker.view.StudentView;
import beatprogramming.github.com.teacker_tracker.persistence.StudentDao;
import beatprogramming.github.com.teacker_tracker.persistence.StudentDaoImpl;

/**
 * Responsible object for handling all UI events on behalf of the view.
 */
public class StudentPresenter implements OnLoadFinishListener {

    private static String TAG = StudentPresenter.class.getName();

    private StudentView view;
    private StudentDao studentDao;

    public StudentPresenter(StudentView view) {
        this.view = view;
        studentDao = new StudentDaoImpl();
    }

    public void findStudents(Subject subject) {
        view.showLoading();
        Log.d(TAG, "findStudents, subject: " + subject.toString());
        studentDao.findStudentsBySubject(subject, this);
    }

    public void onItemClicked(int position) {
        Student student = view.getStudentFromAdapter(position);
        view.loadStudentUpdateFragment(student);
    }

    @Override
    public void onLoadFinish(List<? extends Serializable> items) {
        Log.d(TAG, "onLoadFinish, number of items: " + items.size());
        view.setItems((List<Student>) items);
        view.hideLoading();
    }

    public void onCreateStudent() {
        view.loadStudentUpdateFragment(null);
    }
}
