package beatprogramming.github.com.teacker_tracker.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;

import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.adapter.ScoreAdapter;
import beatprogramming.github.com.teacker_tracker.callback.FragmentCallback;
import beatprogramming.github.com.teacker_tracker.domain.Review;
import beatprogramming.github.com.teacker_tracker.domain.Score;
import beatprogramming.github.com.teacker_tracker.presenter.ScorePresenter;
import beatprogramming.github.com.teacker_tracker.view.ScoreView;

/**
 * Created by malkomich on 27/11/15.
 */
public class ScoreFragment extends ListFragment implements ScoreView {

    private static String TAG = ScoreFragment.class.getName();

    private static final String REVIEW = "BUNDLE_REVIEW";

    private FragmentCallback callback;

    private ScorePresenter presenter;

    private ProgressBar progressBar;

    private ScoreAdapter adapter;

    public static ScoreFragment newInstance(Review review) {

        ScoreFragment fragment = new ScoreFragment();

        Bundle args = new Bundle();
        args.putSerializable(REVIEW, review);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        Review review = null;
        if (args != null) {
            review = (Review) args.getSerializable(REVIEW);
            getActivity().setTitle(review.getName() + " (" + review.getSubject().toString() + ")");
        }

        presenter = new ScorePresenter(this, review);
    }

    @Override
    public void onResume() {

        super.onResume();
        presenter.onResume();

    }

    @Override
    public void onPause() {

        super.onPause();
        for(int i=0; i < adapter.getCount(); i++){
            Log.d(TAG, "onPause, Saving item " + i);
            presenter.saveRecord(adapter.getItem(i));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate (R.layout.fragment_listview_basic, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.progress);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        callback = (FragmentCallback) context;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        presenter.onItemClicked(position);
    }

    @Override
    public void setItems(List<Score> items) {

        adapter = new ScoreAdapter(getContext(), R.layout.listview_score_row, items);
        setListAdapter(adapter);

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
    public void showDialog(DialogFragment fragment) {
        callback.showDialog(fragment);
    }

    @Override
    public void setScoreValue(Float scoreValue, int position) {
        adapter.getItem(position).setCalificacion(scoreValue);
        adapter.notifyDataSetChanged();
    }

    @Override
    public Float getScoreValue(int position) {
        return adapter.getItem(position).getCalificacion();
    }

}
