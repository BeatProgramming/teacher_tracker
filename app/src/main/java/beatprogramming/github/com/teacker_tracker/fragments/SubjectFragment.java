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
import beatprogramming.github.com.teacker_tracker.adapter.SubjectAdapter;
import beatprogramming.github.com.teacker_tracker.domain.Subject;

/**
 * Created by malkomich on 27/11/15.
 */
public class SubjectFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate (R.layout.fragment_listview, container, false);

        SubjectAdapter adapter_subject = new SubjectAdapter(getActivity(), R.layout.listview_subject_row, DataSource.SUBJECT);

        setListAdapter(adapter_subject);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new SubjectUpdateFragment());
            }
        });

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Subject subject = ((Subject) getListAdapter().getItem(position));

        Fragment fragment = new SubjectUpdateFragment();

        Bundle args = new Bundle();
        args.putSerializable(SubjectUpdateFragment.SUBJECT, subject);
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
