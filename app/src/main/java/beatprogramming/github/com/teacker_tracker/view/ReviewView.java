package beatprogramming.github.com.teacker_tracker.view;

import java.util.List;

import beatprogramming.github.com.teacker_tracker.domain.Review;

/** Interfaz
 * Created by malkomich on 01/12/2015.
 */
public interface ReviewView {

    void setItems(List<Review> items);

    void loadReviewUpdateFragment(Review subject);

    Review getReviewFromAdapter(int position);

    void showLoading();

    void hideLoading();

    void loadScoreFragment(Review review);
}
