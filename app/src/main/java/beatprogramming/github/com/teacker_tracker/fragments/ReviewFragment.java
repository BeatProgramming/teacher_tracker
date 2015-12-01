package beatprogramming.github.com.teacker_tracker.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import beatprogramming.github.com.teacker_tracker.DataSource;
import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.adapter.ReviewAdapter;
import beatprogramming.github.com.teacker_tracker.domain.Review;

/**
 * Created by malkomich on 27/11/15.
 */
public class ReviewFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate (R.layout.fragment_listview, container, false);

        ReviewAdapter adapter = new ReviewAdapter(getActivity(),R.layout.listview_review_row, DataSource.REVIEW);

        setListAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new ReviewUpdateFragment());
            }
        });

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Review review = ((Review) getListAdapter().getItem(position));

        Fragment fragment = new ScoreFragment();

        Bundle args = new Bundle();
        args.putSerializable(ScoreFragment.REVIEW, review);
        fragment.setArguments(args);

        replaceFragment(fragment);
    }

    public void replaceFragment(Fragment fragment) {

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
