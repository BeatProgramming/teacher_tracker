package beatprogramming.github.com.teacker_tracker.fragments;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import java.util.List;
import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.adapter.ReviewAdapter;
import beatprogramming.github.com.teacker_tracker.callback.FragmentCallback;
import beatprogramming.github.com.teacker_tracker.domain.Review;
import beatprogramming.github.com.teacker_tracker.view.ReviewView;
import beatprogramming.github.com.teacker_tracker.presenter.ReviewPresenter;

/**
 * - Implementa las evaluaciones (Exámenes/Prácticas)
 */
public class ReviewFragment extends ListFragment implements ReviewView, AdapterView.OnItemLongClickListener {

    private FragmentCallback callback;
    private ProgressBar progressBar;
    private ReviewPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ReviewPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("   " + getResources().getString(R.string.reviewTitle));
        presenter.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate (R.layout.fragment_listview, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.progress);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onCreateReview();
            }
        });

        return view;
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        getListView().setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
        getListView().setVisibility(View.VISIBLE);
    }

    @Override
    public void setupListView() {
        getListView().setOnItemLongClickListener(this);
    }

    @Override
    public void loadScoreFragment(Review review) {
        Fragment fragment = ScoreFragment.newInstance(review);
        callback.replaceFragment(fragment);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        presenter.onItemClicked(position);
    }

    @Override
    public void setItems(List<Review> items) {
        setListAdapter(new ReviewAdapter(getActivity(), R.layout.listview_review_row, items));
    }

    @Override
    public void loadReviewUpdateFragment(Review review) {
        ReviewUpdateFragment fragment = (review != null) ?
                ReviewUpdateFragment.newInstance(review) : new ReviewUpdateFragment();
        callback.replaceFragment(fragment);
    }

    @Override
    public Review getReviewFromAdapter(int position) {
        return (Review) getListAdapter().getItem(position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        callback = (FragmentCallback) context;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        presenter.onItemLongClick(position);
        return true;
    }
}
