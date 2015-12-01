package beatprogramming.github.com.teacker_tracker.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.DataSource;
import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.adapter.ScoreAdapter;
import beatprogramming.github.com.teacker_tracker.domain.Review;
import beatprogramming.github.com.teacker_tracker.domain.Score;

/**
 * Created by malkomich on 27/11/15.
 */
public class ScoreFragment extends ListFragment {

    public static final String REVIEW = "Prueba";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate (R.layout.fragment_score_listview, container, false);

        List<Score> scores = new ArrayList<Score>();
        Bundle args = getArguments();
        if (args != null) {
            Review review = (Review) args.getSerializable(REVIEW);
            getActivity().setTitle(review.getName() + " (" + review.getSubject().toString() + ")");
            scores = review.getScores();
        }

        scores = DataSource.SCORE; // Temporal (para prueba)
        ScoreAdapter adapter = new ScoreAdapter(getActivity(),R.layout.listview_score_row, scores);

        setListAdapter(adapter);

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {


    }

}
