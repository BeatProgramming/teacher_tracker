package beatprogramming.github.com.teacker_tracker.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ListFragment;
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
import beatprogramming.github.com.teacker_tracker.domain.Student;
import beatprogramming.github.com.teacker_tracker.presenter.ScorePresenter;
import beatprogramming.github.com.teacker_tracker.view.ScoreView;

/**
 * Created by malkomich on 27/11/15.
 */
public class ScoreFragment extends ListFragment implements ScoreView {

    private static String TAG = ScoreFragment.class.getName();

    public static final String REVIEW = "Prueba";

    private FragmentCallback callback;

    private ScorePresenter presenter;

    private ProgressBar progressBar;

    private List<Score> items;
    private ScoreAdapter adapter;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate (R.layout.fragment_listview, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.progress);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onCreateScore();
            }
        });

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        presenter.onItemClicked(position);
    }

    @Override
    public void setItems(List<Score> items) {
        this.items = items;
        if(adapter != null)
            adapter.addAll(items);
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
    public void newItem() {
        items.add(new Score());
        ScoreAdapter adapter = ((ScoreAdapter) getListAdapter());
        adapter.notifyDataSetChanged();
        setSelection(adapter.getCount() - 1);
    }

    @Override
    public void createAdapter(List<Student> studentList) {
        adapter = new ScoreAdapter(getContext(), R.layout.listview_score_row, studentList);
    }

}
