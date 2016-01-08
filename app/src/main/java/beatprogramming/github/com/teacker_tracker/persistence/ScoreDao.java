package beatprogramming.github.com.teacker_tracker.persistence;

import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.domain.Review;
import beatprogramming.github.com.teacker_tracker.domain.Score;


public interface ScoreDao {

    void findScore(OnLoadFinishListener listener);

    void updateScore(float calification,String comment,int reviewId,int studentId);

    void deleteScore(int id, OnDeleteFinishListener listener);

    void findScoreByReview(Review review, OnLoadFinishListener listener);

    void updateScore(Score score);
}
