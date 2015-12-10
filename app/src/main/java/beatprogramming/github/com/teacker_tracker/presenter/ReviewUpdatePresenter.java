package beatprogramming.github.com.teacker_tracker.presenter;

import android.util.Log;

import org.joda.time.DateTime;

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
import beatprogramming.github.com.teacker_tracker.util.DateTimeFormatter;
import beatprogramming.github.com.teacker_tracker.view.ReviewUpdateView;

/**
 * Responsible object for handling all UI events on behalf of the view.
 */
public class ReviewUpdatePresenter implements OnUpdateFinishListener, OnDeleteFinishListener, OnLoadFinishListener {

    private static String TAG = ReviewUpdatePresenter.class.getName();

    public static final int RADIO_EXAM = 0;
    public static final int RADIO_PROJECT = 1;

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

    public void submit(int id, String name, int subjectId, String dateTimeString,  String
            typeValue) {
        DateTime dateTime = DateTimeFormatter.stringToDateTime(dateTimeString);
        reviewDao.updateReview(id, name, subjectId, dateTime, typeValue, this);
    }

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

    public void onSubjectSelected(Object item) {
        if(item != null) {
            Subject subject = (Subject) item;
            view.setSubjectId(subject.getId());
        } else {
            Log.d(TAG, "onSubjectSelected, nothing selected.");
            // ACCION CUANDO NO HAY ASIGNATURA SELECIONADA.
        }

    }

    public void onTypeChanged(int type) {

        String typeValue = null;
        switch (type) {
            case RADIO_EXAM:
                typeValue = ReviewDaoImpl.EXAM;
                break;
            case RADIO_PROJECT:
                typeValue = ReviewDaoImpl.PROJECT;
                break;
            default:
                break;
        }
        view.setTypeValue(typeValue);
    }
}
