package beatprogramming.github.com.teacker_tracker.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.callback.FragmentCallback;
import beatprogramming.github.com.teacker_tracker.domain.Subject;
import beatprogramming.github.com.teacker_tracker.domain.Task;
import beatprogramming.github.com.teacker_tracker.presenter.TaskUpdatePresenter;
import beatprogramming.github.com.teacker_tracker.util.DateTimeFormatter;
import beatprogramming.github.com.teacker_tracker.view.TaskUpdateView;

/**
 * Created by malkomich on 27/11/15.
 */
public class TaskUpdateFragment extends Fragment implements TaskUpdateView, View.OnClickListener {

    private static final String TAG = TaskUpdateFragment.class.getName();
    private static final String TASK = "BUNDLE_TASK";

    private FragmentCallback callback;
    private TaskUpdatePresenter presenter;

    private TextView idTextView;
    private EditText dateEditText;
    private EditText timeEditText;
    private EditText descriptionEditText;
    private Spinner subjectSpinner;
    private TextView subjectValueTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new TaskUpdatePresenter(this);
    }

    /**
     * Instantiate the fragment with an existing subject to modify.
     *
     * @param task
     * @return
     */
    public static TaskUpdateFragment newInstance(Task task) {

        TaskUpdateFragment fragment = new TaskUpdateFragment();

        Bundle args = new Bundle();
        args.putSerializable(TASK, task);
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task_update, container, false);

        // Identify all fields of the form.
        idTextView = (TextView) view.findViewById(R.id.tasktId);
        dateEditText = (EditText) view.findViewById(R.id.taskDate);
        timeEditText = (EditText) view.findViewById(R.id.taskHour);
        descriptionEditText = (EditText) view.findViewById(R.id.taskDescription);
        subjectSpinner = (Spinner) view.findViewById(R.id.taskSubject);
        subjectValueTextView = (TextView) view.findViewById(R.id.taskSubjectId);

        timeEditText.setOnClickListener(this);
        dateEditText.setOnClickListener(this);

        Bundle args = getArguments();
        if (args != null) {

            Task task = (Task) args.getSerializable(TASK);

            idTextView.setText(Integer.toString(task.getId()));
            dateEditText.setText(DateTimeFormatter.dateTimeToDateString(task.getDateTime()));
            timeEditText.setText(DateTimeFormatter.dateTimeToTimeString(task.getDateTime()));
            descriptionEditText.setText(task.getDescription());

            int subjectId = task.getSubject().getId();
            subjectValueTextView.setText(Integer.toString(subjectId));
            if (subjectId > 0) {
                for (int i = 0; i < subjectSpinner.getAdapter().getCount(); i++) {
                    Subject subject = (Subject) subjectSpinner.getAdapter().getItem(i);
                    if (subject.getId() == subjectId) {
                        subjectSpinner.setSelection(i);
                        break;
                    }
                }
            }

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
     * @param context
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
                        descriptionEditText.getText().toString(),
                        dateEditText.getText().toString() + " " + timeEditText.getText().toString(),
                        Integer.parseInt(subjectValueTextView.getText().toString()));
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
    public void showDialog(DialogFragment fragment) {
        callback.showDialog(fragment);
    }

    @Override
    public void setTaskDate(String dateString) {
        dateEditText.setText(dateString);
    }

    @Override
    public void setTaskTime(String timeString) {
        timeEditText.setText(timeString);
    }

    private void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}
