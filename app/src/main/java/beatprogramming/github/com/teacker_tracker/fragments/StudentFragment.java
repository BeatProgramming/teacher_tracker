package beatprogramming.github.com.teacker_tracker.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.List;

import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.adapter.StudentAdapter;
import beatprogramming.github.com.teacker_tracker.callback.FragmentCallback;
import beatprogramming.github.com.teacker_tracker.domain.Student;
import beatprogramming.github.com.teacker_tracker.domain.Subject;
import beatprogramming.github.com.teacker_tracker.view.StudentView;
import beatprogramming.github.com.teacker_tracker.presenter.StudentPresenter;

/**
 * Created by malkomich on 30/11/15.
 */
public class StudentFragment extends ListFragment implements StudentView {

    public static final String SUBJECT = "Subject";
    private static String TAG = StudentFragment.class.getName();

    private FragmentCallback callback;

    private ProgressBar progressBar;

    private StudentPresenter presenter;

    private Subject subject;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new StudentPresenter(this);

        Bundle extras = getArguments();
        if (extras != null) {
            if (extras.containsKey(SUBJECT)) {
                subject = (Subject) extras.getSerializable(SUBJECT);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.findStudents(subject);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_listview, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.progress);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onCreateStudent();
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

    @Override
    public void setItems(List<Student> items) {
        setListAdapter(new StudentAdapter(getActivity(), R.layout.listview_student_row, items));
    }

    @Override
    public void loadStudentUpdateFragment(Student student) {
        StudentUpdateFragment fragment = (student != null) ?
                StudentUpdateFragment.newInstance(student) : new StudentUpdateFragment();
        callback.replaceFragment(fragment);
    }

    @Override
    public Student getStudentFromAdapter(int position) {
        return (Student) getListAdapter().getItem(position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        callback = (FragmentCallback) context;
    }

}
