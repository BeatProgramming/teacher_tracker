package beatprogramming.github.com.teacker_tracker.presenter;

import android.util.Log;

import java.io.Serializable;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;
import beatprogramming.github.com.teacker_tracker.domain.Project;
import beatprogramming.github.com.teacker_tracker.domain.Student;
import beatprogramming.github.com.teacker_tracker.domain.Subject;
import beatprogramming.github.com.teacker_tracker.persistence.StudentDao;
import beatprogramming.github.com.teacker_tracker.persistence.StudentDaoImpl;
import beatprogramming.github.com.teacker_tracker.persistence.SubjectDao;
import beatprogramming.github.com.teacker_tracker.persistence.SubjectDaoImpl;
import beatprogramming.github.com.teacker_tracker.view.StudentUpdateView;

/**
 * Responsible object for handling all UI events on behalf of the view.
 */
public class StudentUpdatePresenter implements OnUpdateFinishListener, OnDeleteFinishListener, OnLoadFinishListener {

    private static String TAG = StudentUpdatePresenter.class.getName();

    private StudentUpdateView view;
    private StudentDao studentDao;

    private SubjectDao subjectDao;
    private List<Subject> subjectList;
    private Student student;

    public StudentUpdatePresenter(StudentUpdateView view) {
        this.view = view;
        studentDao = new StudentDaoImpl();
        subjectDao = new SubjectDaoImpl();
    }

    public void onResume() {
        subjectDao.findSubjects(this);
        fillView();
    }

    public void submit(int id, String name, String surname, String iconPath, int subjectId) {

        if(name.equals("") || surname.equals("") || subjectId == 0)
            view.setError("Completa todos los campos");
        else {
            if (iconPath.equals(""))
                iconPath = null;
            studentDao.updateStudent(id, name, surname, iconPath, subjectId, this);
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

    public void onSubjectSelected(int position) {
        if(position >= 0) {
            Subject subject = subjectList.get(position);
            view.setSubjectId(subject.getId());
            Log.d(TAG, "onSubjectSelected, selected: " + subject.toString());
        } else {
            Log.d(TAG, "onSubjectSelected, nothing selected.");
            // ACCION CUANDO NO HAY ASIGNATURA SELECIONADA.
        }

    }

    @Override
    public void onLoadFinish(List<? extends Serializable> items) {
        subjectList = (List<Subject>) items;
        view.setSubjectItems(subjectList);
    }

    public void fillView() {


        if (student != null) {

            String iconPath = student.getIconPath();
            if (iconPath != null) {
                view.setIconPath(iconPath);
            }

            view.setName(student.getName());
            view.setSurname(student.getSurname());

        }

    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
