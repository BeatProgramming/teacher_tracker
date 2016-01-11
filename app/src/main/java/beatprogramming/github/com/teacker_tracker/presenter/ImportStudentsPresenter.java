package beatprogramming.github.com.teacker_tracker.presenter;
import android.util.Log;

import java.io.Serializable;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;
import beatprogramming.github.com.teacker_tracker.domain.Student;
import beatprogramming.github.com.teacker_tracker.domain.Subject;
import beatprogramming.github.com.teacker_tracker.persistence.StudentDao;
import beatprogramming.github.com.teacker_tracker.persistence.StudentDaoImpl;
import beatprogramming.github.com.teacker_tracker.persistence.SubjectDao;
import beatprogramming.github.com.teacker_tracker.persistence.SubjectDaoImpl;
import beatprogramming.github.com.teacker_tracker.view.ImportStudentsView;

/**
 * - Controlador de la importación de estudiantes
 */
public class ImportStudentsPresenter implements OnLoadFinishListener, OnUpdateFinishListener {

    private static String TAG = SubjectPresenter.class.getName();
    private List<Student> studentList;
    private ImportStudentsView view;
    private SubjectDao subjectDao;
    private StudentDao studentDao;

    public ImportStudentsPresenter(ImportStudentsView view, List<Student> studentList) {
        this.view = view;
        this.studentList = studentList;

        subjectDao = new SubjectDaoImpl();
        studentDao = new StudentDaoImpl();
    }

    public void onResume() {
        subjectDao.findSubjects(this);
    }

    public void onItemClicked(int position) {

        view.showLoading();

        Subject subject = view.getSubjectFromAdapter(position);
        for(Student student: studentList) {
            Log.d(TAG, "onItemClicked, student: " + student.toString());
            int id = studentDao.updateStudent(student.getId(), student.getName(), student.getSurname(), student.getIconPath(), subject.getId(), this);
            student.setId(id);
        }
        subject.addStudents(studentList.toArray(new Student[studentList.size()]));
        subjectDao.updateSubject(subject, this);
        view.hideLoading();
        view.loadStudentFragment();
    }

    @Override
    public void onLoadFinish(List<? extends Serializable> items) {
        view.setItems((List<Subject>) items);
    }

    @Override
    public void onError(String message) {
        view.setError(message);
    }

    @Override
    public void onSuccess() {
        // Nothing to do here.
    }
}
