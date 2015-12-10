package beatprogramming.github.com.teacker_tracker.presenter;

import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;
import beatprogramming.github.com.teacker_tracker.view.SubjectUpdateView;
import beatprogramming.github.com.teacker_tracker.persistence.SubjectDao;
import beatprogramming.github.com.teacker_tracker.persistence.SubjectDaoImpl;

/**
 * Responsible object for handling all UI events on behalf of the view.
 */
public class SubjectUpdatePresenter implements OnUpdateFinishListener, OnDeleteFinishListener {

    private static String TAG = SubjectUpdatePresenter.class.getName();

    private SubjectUpdateView view;
    private SubjectDao subjectDao;

    public SubjectUpdatePresenter(SubjectUpdateView view) {
        this.view = view;
        subjectDao = new SubjectDaoImpl();
    }

    public void submit(int id, String name, String description, String course, String
            classRoom) {
        subjectDao.updateSubject(id, name, description, course, classRoom, this);
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

    @Override
    public void onDeleteFinish() {
        view.loadSubjectFragment();
    }
}
