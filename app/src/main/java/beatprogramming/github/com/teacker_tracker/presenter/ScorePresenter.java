package beatprogramming.github.com.teacker_tracker.presenter;

import java.io.Serializable;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.domain.Review;
import beatprogramming.github.com.teacker_tracker.domain.Score;
import beatprogramming.github.com.teacker_tracker.domain.Student;
import beatprogramming.github.com.teacker_tracker.persistence.ScoreDao;
import beatprogramming.github.com.teacker_tracker.persistence.ScoreDaoImpl;
import beatprogramming.github.com.teacker_tracker.persistence.StudentDao;
import beatprogramming.github.com.teacker_tracker.persistence.StudentDaoImpl;
import beatprogramming.github.com.teacker_tracker.view.ScoreView;

/**
 * Created by malkomich on 03/12/2015.
 */
public class ScorePresenter implements OnLoadFinishListener {

    private static String TAG = ScorePresenter.class.getName();

    private ScoreView view;
    private ScoreDao scoreDao;
    private StudentDao studentDao;

    private Review review;

    public ScorePresenter(ScoreView view, Review review) {
        this.view = view;
        this.review = review;
        scoreDao = new ScoreDaoImpl();
        studentDao = new StudentDaoImpl();
    }

    public void onResume() {
        view.showLoading();
        studentDao.findStudents(new OnLoadFinishListener() {
            @Override
            public void onLoadFinish(List<? extends Serializable> items) {
                view.createAdapter((List<Student>) items);
            }
        });
        scoreDao.findScoreByReview(review, this);
    }

    @Override
    public void onLoadFinish(List<? extends Serializable> items) {
        view.setItems((List<Score>) items);
        view.hideLoading();
    }

    public void onCreateScore() {
        view.newItem();
    }

    public void onItemClicked(int position) {

    }
}
