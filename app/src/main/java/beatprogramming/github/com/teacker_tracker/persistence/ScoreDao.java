package beatprogramming.github.com.teacker_tracker.persistence;

import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;
import beatprogramming.github.com.teacker_tracker.domain.Review;


public interface ScoreDao {

    void findScore(OnLoadFinishListener listener);

    void updateScore(int id,double calification,String comment,int reviewId,int studentId,
                    OnUpdateFinishListener listener);

    void deleteScore(int id, OnDeleteFinishListener listener);

    void findScoreByReview(Review review, OnLoadFinishListener listener);
}
