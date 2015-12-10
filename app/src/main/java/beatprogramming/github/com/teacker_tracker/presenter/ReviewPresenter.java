package beatprogramming.github.com.teacker_tracker.presenter;

import java.io.Serializable;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.domain.Review;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.view.ReviewView;
import beatprogramming.github.com.teacker_tracker.persistence.ReviewDao;
import beatprogramming.github.com.teacker_tracker.persistence.ReviewDaoImpl;

/**
 * Responsible object for handling all UI events on behalf of the view.
 */
public class ReviewPresenter implements OnLoadFinishListener {

    private static String TAG = ReviewPresenter.class.getName();

    private ReviewView view;
    private ReviewDao reviewDao;

    public ReviewPresenter(ReviewView view) {
        this.view = view;
        reviewDao = new ReviewDaoImpl();
    }

    public void onResume() {
        reviewDao.findReviews(this);
    }

    public void onItemClicked(int position) {
        Review review = view.getReviewFromAdapter(position);
        view.loadReviewUpdateFragment(review);
    }

    @Override
    public void onLoadFinish(List<? extends Serializable> items) {
        view.setItems((List<Review>) items);
    }

    public void onFloatingButtonClick() {
        view.loadReviewUpdateFragment(null);
    }
}
