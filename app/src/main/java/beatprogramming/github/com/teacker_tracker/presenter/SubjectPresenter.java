package beatprogramming.github.com.teacker_tracker.presenter;
import java.io.Serializable;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.domain.Subject;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.persistence.SubjectDao;
import beatprogramming.github.com.teacker_tracker.view.SubjectView;
import beatprogramming.github.com.teacker_tracker.persistence.SubjectDaoImpl;

/**
 * - Controlador de las asignaturas
 */
public class SubjectPresenter implements OnLoadFinishListener {

    private SubjectView view;
    private SubjectDao subjectDao;

    public SubjectPresenter(SubjectView view) {
        this.view = view;
        subjectDao = new SubjectDaoImpl();
    }

    public void onResume() {
        view.showLoading();
        subjectDao.findSubjects(this);
    }

    public void onItemClicked(int position) {
        Subject subject = view.getSubjectFromAdapter(position);
        view.loadSubjectUpdateFragment(subject);
    }

    @Override
    public void onLoadFinish(List<? extends Serializable> items) {
        view.setItems((List<Subject>) items);
        view.hideLoading();
    }

    public void onCreateSubject() {
        view.loadSubjectUpdateFragment(null);
    }

}
