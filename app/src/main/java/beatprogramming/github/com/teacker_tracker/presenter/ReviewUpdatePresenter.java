package beatprogramming.github.com.teacker_tracker.presenter;

import java.io.Serializable;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;
import beatprogramming.github.com.teacker_tracker.domain.Subject;
import beatprogramming.github.com.teacker_tracker.persistence.ReviewDao;
import beatprogramming.github.com.teacker_tracker.persistence.ReviewDaoImpl;
import beatprogramming.github.com.teacker_tracker.persistence.SubjectDao;
import beatprogramming.github.com.teacker_tracker.persistence.SubjectDaoImpl;
import beatprogramming.github.com.teacker_tracker.view.ReviewUpdateView;

/**
 * Responsible object for handling all UI events on behalf of the view.
 */
public class ReviewUpdatePresenter implements OnUpdateFinishListener, OnDeleteFinishListener, OnLoadFinishListener {

    private ReviewUpdateView view;

    private ReviewDao reviewDao;
    private SubjectDao subjectDao;

    public ReviewUpdatePresenter(ReviewUpdateView view) {
        this.view = view;
        reviewDao = new ReviewDaoImpl();
        subjectDao = new SubjectDaoImpl();
    }

    public void onResume() {
        subjectDao.findSubjects(this);
    }

    // SUBMIT METHOD HERE

    public void delete(int id) {
        reviewDao.deleteReview(id, this);
    }

    @Override
    public void onError(String message) {
        view.setError(message);
    }

    @Override
    public void onSuccess() {
        view.loadReviewFragment();
    }

    @Override
    public void onDeleteFinish() {
        view.loadReviewFragment();
    }

    @Override
    public void onLoadFinish(List<? extends Serializable> items) {
        view.setSubjectItems((List<Subject>) items);
    }
}
