package beatprogramming.github.com.teacker_tracker.fragments;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.domain.Task;
import beatprogramming.github.com.teacker_tracker.presenter.NotePresenter;
import beatprogramming.github.com.teacker_tracker.view.NoteView;

/**
 * - Implementa la creación de notas de una tarea
 */
public class NoteFragment extends Fragment implements NoteView {

    private static final String TASK = "BUNDLE_TASK";

    private EditText text;
    private NotePresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new NotePresenter(this);
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
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        text = (EditText) view.findViewById(R.id.task_note);
        Bundle args = getArguments();
        Task task=null;
        Log.d("debug", "args: " + args.toString());
        if (args!=null){
            task = (Task) args.getSerializable(TASK);
            Log.d("debug", "task: " + task.toString());
            if (task.getNote()!=null){
                text.setText(task.getNote());
            }
            presenter.setTask(task);
        } else{
            Log.d("debug", "task es null");
        }
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.saveNote(text.getText().toString());
    }

    @Override
    public void setTextNote() {

    }
}
