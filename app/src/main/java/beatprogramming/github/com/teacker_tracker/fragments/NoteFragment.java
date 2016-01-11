package beatprogramming.github.com.teacker_tracker.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.domain.Task;
import beatprogramming.github.com.teacker_tracker.persistence.TaskDao;
import beatprogramming.github.com.teacker_tracker.persistence.TaskDaoImpl;
import beatprogramming.github.com.teacker_tracker.presenter.NotePresenter;
import beatprogramming.github.com.teacker_tracker.presenter.TaskUpdatePresenter;
import beatprogramming.github.com.teacker_tracker.view.NoteView;

/**
 * Created by adrian on 11/01/2016.
 */
public class NoteFragment extends Fragment implements NoteView {

    private static final String TASK = "BUNDLE_TASK";

    private EditText text;
    private NotePresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static NoteFragment newInstance(Task task) {

        NoteFragment fragment = new NoteFragment();

        Bundle args = new Bundle();
        args.putSerializable(TASK, task);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_notes, container, false);

        text = (EditText) view.findViewById(R.id.task_note);
        Bundle args = getArguments();

        Task task = (args!= null) ? (Task) args.getSerializable(TASK) : null;
        if (task.getNote()!=null){
            text.setText(task.getNote());
        }
        presenter.setTask(task);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.saveNote(text.getText());
    }

    @Override
    public void setTextNote() {

    }
}
