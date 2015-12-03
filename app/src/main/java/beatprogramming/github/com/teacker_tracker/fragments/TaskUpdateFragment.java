package beatprogramming.github.com.teacker_tracker.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.callback.FragmentCallback;
import beatprogramming.github.com.teacker_tracker.domain.Subject;
import beatprogramming.github.com.teacker_tracker.domain.Task;
import beatprogramming.github.com.teacker_tracker.presenter.SubjectUpdatePresenter;
import beatprogramming.github.com.teacker_tracker.presenter.TaskUpdatePresenter;
import beatprogramming.github.com.teacker_tracker.view.SubjectUpdateView;
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

        Bundle args = getArguments();
        if (args != null) {

            Task task = (Task) args.getSerializable(TASK);

            idTextView.setText(Integer.toString(task.getId()));
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
                // CALL PRESENTER SUBMIT METHOD
                break;
            case R.id.button_delete:
                presenter.delete(Integer.parseInt(idTextView.getText().toString()));
                break;
            default:
                break;
        }

    }

    @Override
    public void loadTaskFragment() {
        callback.goBack();
    }

    @Override
    public void setError(String message) {
        showToastMessage(message);
    }

    private void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
