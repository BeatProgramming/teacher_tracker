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
import beatprogramming.github.com.teacker_tracker.domain.Subject;
import beatprogramming.github.com.teacker_tracker.callback.FragmentCallback;
import beatprogramming.github.com.teacker_tracker.view.SubjectUpdateView;
import beatprogramming.github.com.teacker_tracker.presenter.SubjectUpdatePresenter;

/**
 * Created by malkomich on 27/11/15.
 */
public class SubjectUpdateFragment extends Fragment implements SubjectUpdateView, View.OnClickListener {

    private static final String TAG = SubjectUpdateFragment.class.getName();
    private static final String SUBJECT = "BUNDLE_SUBJECT";

    private FragmentCallback callback;
    private SubjectUpdatePresenter presenter;

    private TextView idTextView;
    private EditText nameEditText;
    private EditText descriptionEditText;
    private EditText courseEditText;
    private EditText classRoomEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SubjectUpdatePresenter(this);
    }

    /**
     * Instantiate the fragment with an existing subject to modify.
     *
     * @param subject
     * @return
     */
    public static SubjectUpdateFragment newInstance(Subject subject) {

        SubjectUpdateFragment fragment = new SubjectUpdateFragment();

        Bundle args = new Bundle();
        args.putSerializable(SUBJECT, subject);
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_subject_update, container, false);

        // Identify all fields of the form.
        idTextView = (TextView) view.findViewById(R.id.subjectId);
        nameEditText = (EditText) view.findViewById(R.id.subjectName);
        descriptionEditText = (EditText) view.findViewById(R.id.subjectDescription);
        courseEditText = (EditText) view.findViewById(R.id.subjectCurse);
        classRoomEditText = (EditText) view.findViewById(R.id.subjectAula);

        Bundle args = getArguments();
        if (args != null) {

            Subject subject = (Subject) args.getSerializable(SUBJECT);

            idTextView.setText(Integer.toString(subject.getId()));
            nameEditText.setText(subject.getNombre());
            descriptionEditText.setText(subject.getDescripcion());
            courseEditText.setText(subject.getCurso());
            classRoomEditText.setText(subject.getAula());
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
                        nameEditText.getText().toString(),
                        descriptionEditText.getText().toString(),
                        courseEditText.getText().toString(),
                        classRoomEditText.getText().toString());
                break;
            case R.id.button_delete:
                presenter.delete(Integer.parseInt(idTextView.getText().toString()));
                break;
            default:
                break;
        }

    }

    @Override
    public void loadSubjectFragment() {
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
