package beatprogramming.github.com.teacker_tracker.presenter;

import java.io.Serializable;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnScorePickedListener;
import beatprogramming.github.com.teacker_tracker.domain.Review;
import beatprogramming.github.com.teacker_tracker.domain.Score;
import beatprogramming.github.com.teacker_tracker.fragments.ScorePickerFragment;
import beatprogramming.github.com.teacker_tracker.persistence.ScoreDao;
import beatprogramming.github.com.teacker_tracker.persistence.ScoreDaoImpl;
import beatprogramming.github.com.teacker_tracker.view.ScoreView;

public class ScorePresenter implements OnLoadFinishListener, OnScorePickedListener {


    private ScoreView view;
    private ScoreDao scoreDao;

    private Review review;

    public ScorePresenter(ScoreView view, Review review) {
        this.view = view;
        this.review = review;
        scoreDao = new ScoreDaoImpl();
    }

    public void onResume() {
        view.showLoading();
        scoreDao.findScoreByReview(review, this);
    }

    @Override
    public void onLoadFinish(List<? extends Serializable> items) {
        view.setItems((List<Score>) items);
        view.hideLoading();
    }

    public void onItemClicked(int position) {
        Float value = view.getScoreValue(position);
        ScorePickerFragment fragment = ScorePickerFragment.newInstance(this, position, value);
        view.showDialog(fragment);
    }

    @Override
    public void onScorePicked(Float scoreValue, int position) {
        view.setScoreValue(scoreValue, position);
    }

    public void saveRecord(Score score) {
        if(score.getCalificacion() != null)
            scoreDao.updateScore(score);
    }

}
