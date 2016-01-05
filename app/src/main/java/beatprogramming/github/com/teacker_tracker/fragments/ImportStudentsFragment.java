package beatprogramming.github.com.teacker_tracker.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.adapter.SubjectAdapter;
import beatprogramming.github.com.teacker_tracker.callback.FragmentCallback;
import beatprogramming.github.com.teacker_tracker.domain.Student;
import beatprogramming.github.com.teacker_tracker.domain.Subject;
import beatprogramming.github.com.teacker_tracker.presenter.ImportStudentsPresenter;
import beatprogramming.github.com.teacker_tracker.view.ImportStudentsView;

/**
 * Created by malkomich on 04/01/2016.
 */
public class ImportStudentsFragment extends ListFragment implements ImportStudentsView {

    private static String TAG = StudentFragment.class.getName();

    public static final String STUDENTS = "BUNDLE_STUDENTS";

    private FragmentCallback callback;

    private ImportStudentsPresenter presenter;

    private ProgressBar progressBar;

    public static Fragment newInstance(List<Student> newStudents) {

        ImportStudentsFragment fragment = new ImportStudentsFragment();

        Bundle args = new Bundle();
        args.putSerializable(STUDENTS, (Serializable) newStudents);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Importando alumnos...");

        Bundle args = getArguments();
        List<Student> studentList = null;
        if (args != null) {
            studentList = (List<Student>) args.getSerializable(STUDENTS);
        }

        presenter = new ImportStudentsPresenter(this, studentList);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_listview_basic, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.progress);

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        presenter.onItemClicked(position);
    }

    @Override
    public void setItems(List<Subject> items) {
        setListAdapter(new SubjectAdapter(getActivity(), R.layout.listview_subject_row, items));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        callback = (FragmentCallback) context;
    }

    @Override
    public void loadStudentFragment() {
        callback.replaceFragment(new StudentTabFragment());
    }

    @Override
    public Subject getSubjectFromAdapter(int position) {
        return (Subject) getListAdapter().getItem(position);
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
    public void setError(String message) {
        showToastMessage(message);
    }

    private void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}
