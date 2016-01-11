package beatprogramming.github.com.teacker_tracker.view;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.domain.Review;

/**
 * - Define los métodos  sobre los que tiene control el presentador que conecta
 *   la capa de persistencia con la vista.
 */
public interface ReviewView {

    void setItems(List<Review> items);

    void loadReviewUpdateFragment(Review subject);

    Review getReviewFromAdapter(int position);

    void showLoading();

    void hideLoading();

    void loadScoreFragment(Review review);
}
