package beatprogramming.github.com.teacker_tracker.fragments;

import android.content.Context;
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
import beatprogramming.github.com.teacker_tracker.adapter.SubjectAdapter;
import beatprogramming.github.com.teacker_tracker.domain.Subject;
import beatprogramming.github.com.teacker_tracker.callback.FragmentCallback;
import beatprogramming.github.com.teacker_tracker.view.SubjectView;
import beatprogramming.github.com.teacker_tracker.presenter.SubjectPresenter;

/**
 * Fragment view for Subject's List
 */
public class SubjectFragment extends ListFragment implements SubjectView {

    private static String TAG = SubjectFragment.class.getName();

    private FragmentCallback callback;

    private ProgressBar progressBar;

    private SubjectPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SubjectPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_listview, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.progress);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onFloatingButtonClick();
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
    public void onListItemClick(ListView l, View v, int position, long id) {
        presenter.onItemClicked(position);
    }

    /**
     * Sets the list adapter which will fill the view.
     *
     * @param items
     */
    @Override
    public void setItems(List<Subject> items) {
        setListAdapter(new SubjectAdapter(getActivity(), R.layout.listview_subject_row, items));
    }

    @Override
    public void loadSubjectUpdateFragment(Subject subject) {
        SubjectUpdateFragment fragment = (subject != null) ?
                SubjectUpdateFragment.newInstance(subject) : new SubjectUpdateFragment();
        callback.replaceFragment(fragment);
    }

    /**
     * Retrieves an specific item from the adapter.
     *
     * @param position
     * @return
     */
    @Override
    public Subject getSubjectFromAdapter(int position) {
        return (Subject) getListAdapter().getItem(position);
    }

    /**
     * Called once the fragment is associated with its activity context.
     * It assigns this context to be responsible of the fragment transactions.
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        callback = (FragmentCallback) context;
    }
}