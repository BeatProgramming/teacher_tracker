package beatprogramming.github.com.teacker_tracker.presenter;
import java.io.Serializable;
import java.util.List;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.domain.Subject;
import beatprogramming.github.com.teacker_tracker.persistence.SubjectDao;
import beatprogramming.github.com.teacker_tracker.persistence.SubjectDaoImpl;
import beatprogramming.github.com.teacker_tracker.view.StudentTabView;

/**
 * - Controlador de los estudiantes según la asignatura
 */
public class StudentTabPresenter implements OnLoadFinishListener {

    private StudentTabView view;
    private SubjectDao subjectDao;

    public StudentTabPresenter(StudentTabView view) {
        this.view = view;
        subjectDao = new SubjectDaoImpl();
    }

    public void findSubjects() {
        subjectDao.findSubjects(this);
    }

    @Override
    public void onLoadFinish(List<? extends Serializable> items) {
        view.setTabs((List<Subject>) items);
    }

}
