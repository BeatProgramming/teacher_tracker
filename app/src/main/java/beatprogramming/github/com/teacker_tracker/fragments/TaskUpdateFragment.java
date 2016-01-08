package beatprogramming.github.com.teacker_tracker.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.callback.FragmentCallback;
import beatprogramming.github.com.teacker_tracker.domain.Subject;
import beatprogramming.github.com.teacker_tracker.domain.Task;
import beatprogramming.github.com.teacker_tracker.presenter.TaskUpdatePresenter;
import beatprogramming.github.com.teacker_tracker.view.TaskUpdateView;

public class TaskUpdateFragment extends Fragment implements TaskUpdateView, View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final String TASK = "BUNDLE_TASK";

    private FragmentCallback callback;
    private TaskUpdatePresenter presenter;

    private TextView idTextView;
    private TextView dateTextView;
    private TextView timeTextView;
    private EditText nameEditText;
    private Spinner subjectSpinner;
    private TextView subjectIdTextView;

    /**
     * Instantiate the fragment with an existing subject to modify.
     *
     */
    public static TaskUpdateFragment newInstance(Task task) {

        TaskUpdateFragment fragment = new TaskUpdateFragment();

        Bundle args = new Bundle();
        args.putSerializable(TASK, task);
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new TaskUpdatePresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("   " + getResources().getString(R.string.scoreTitle));
        presenter.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task_update, container, false);

        // Identify all fields of the form.
        idTextView = (TextView) view.findViewById(R.id.tasktId);
        dateTextView = (TextView) view.findViewById(R.id.taskDate);
        timeTextView = (TextView) view.findViewById(R.id.taskHour);
        nameEditText = (EditText) view.findViewById(R.id.taskName);
        subjectSpinner = (Spinner) view.findViewById(R.id.taskSubject);
        subjectIdTextView = (TextView) view.findViewById(R.id.taskSubjectId);

        timeTextView.setOnClickListener(this);
        dateTextView.setOnClickListener(this);
        subjectSpinner.setOnItemSelectedListener(this);

        Bundle args = getArguments();
        if(args != null) {
            Task task = (Task) args.getSerializable(TASK);
            presenter.setTask(task);
        }

        Button submit = (Button) view.findViewById(R.id.button);
        submit.setOnClickListener(this);

        Button delete = (Button) view.findViewById(R.id.button_delete);
        delete.setOnClickListener(this);

        return view;
    }

    /**
     * Called once the fragment is associated with its activity context.
     * It assigns this context to be responsible of the fragment transactions.
     *
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        callback = (FragmentCallback) context;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button:
                presenter.submit(Integer.parseInt(idTextView.getText().toString()),
                        nameEditText.getText().toString(),
                        dateTextView.getText().toString() + " " + timeTextView.getText().toString(),
                        Integer.parseInt(subjectIdTextView.getText().toString()));
                break;
            case R.id.button_delete:
                presenter.delete(Integer.parseInt(idTextView.getText().toString()));
                break;
            case R.id.taskDate:
                presenter.showDatePicker();
                break;
            case R.id.taskHour:
                presenter.showTimePicker();
                break;
            default:
                break;
        }

    }

    @Override
    public void goBack() {
        callback.goBack();
    }

    @Override
    public void setError(String message) {
        showToastMessage(message);
    }

    @Override
    public void setSubjectItems(List<Subject> items) {
        subjectSpinner.setAdapter(new ArrayAdapter<>(getContext(),
                R.layout.textview, items));
    }

    @Override
    public void setSubjectId(int id) {
        subjectIdTextView.setText(Integer.toString(id));
    }

    @Override
    public void showDialog(DialogFragment fragment) {
        callback.showDialog(fragment);
    }

    @Override
    public void setTaskDate(String dateString) {
        dateTextView.setText(dateString);
    }

    @Override
    public void setTaskTime(String timeString) {
        timeTextView.setText(timeString);
    }

    @Override
    public void setTaskId(int id) {
        idTextView.setText(Integer.toString(id));
    }

    @Override
    public void setTaskName(String name) {
        nameEditText.setText(name);
    }

    @Override
    public void setSubject(int subjectId) {

        for (int i = 0; i < subjectSpinner.getAdapter().getCount(); i++) {
            Subject subject = (Subject) subjectSpinner.getAdapter().getItem(i);
            if (subject.getId() == subjectId) {
                subjectSpinner.setSelection(i);
                break;
            }
        }
    }

    private void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        presenter.onSubjectSelected(subjectSpinner.getAdapter().getItem(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        presenter.onSubjectSelected(null);
    }
}
