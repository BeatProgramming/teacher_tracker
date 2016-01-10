package beatprogramming.github.com.teacker_tracker.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.callback.FragmentCallback;
import beatprogramming.github.com.teacker_tracker.domain.Review;
import beatprogramming.github.com.teacker_tracker.domain.Student;
import beatprogramming.github.com.teacker_tracker.domain.Subject;
import beatprogramming.github.com.teacker_tracker.presenter.StudentUpdatePresenter;
import beatprogramming.github.com.teacker_tracker.util.FileUtil;
import beatprogramming.github.com.teacker_tracker.util.ImageGetter;
import beatprogramming.github.com.teacker_tracker.view.StudentUpdateView;

public class StudentUpdateFragment extends Fragment implements StudentUpdateView, View.OnClickListener,
        AdapterView.OnItemSelectedListener {

    private static String TAG = StudentUpdateFragment.class.getName();

    public static final String STUDENT = "BUNDLE_STUDENT";
    private static final int IMAGE_INTENT = 666;

    private FragmentCallback callback;
    private StudentUpdatePresenter presenter;

    private TextView idTextView;
    private EditText nameEditText;
    private EditText surnameEditText;
    private ImageView iconImageView;
    private TextView iconPathTextView;
    private Spinner subjectSpinner;
    private TextView subjectIdTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new StudentUpdatePresenter(this);
    }

    public static StudentUpdateFragment newInstance(Student student) {

        StudentUpdateFragment fragment = new StudentUpdateFragment();

        Bundle args = new Bundle();
        args.putSerializable(STUDENT, student);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("   " + getResources().getString(R.string.studentUpdateTitle));
        presenter.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student_update, container, false);

        // Identify all fields of the form.
        idTextView = (TextView) view.findViewById(R.id.studentId);
        nameEditText = (EditText) view.findViewById(R.id.name_field);
        surnameEditText = (EditText) view.findViewById(R.id.surname_field);
        iconImageView = (ImageView) view.findViewById(R.id.student_icon);
        iconPathTextView = (TextView) view.findViewById(R.id.student_icon_path);
        subjectSpinner = (Spinner) view.findViewById(R.id.student_subject);
        subjectIdTextView = (TextView) view.findViewById(R.id.student_subject_id);

        subjectSpinner.setOnItemSelectedListener(this);

        iconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseImageIntent = ImageGetter.getPickImageIntent(getActivity());
                startActivityForResult(chooseImageIntent, IMAGE_INTENT);

            }
        });

        Bundle args = getArguments();
        Student student = (args != null) ? (Student) args.getSerializable(STUDENT) : null;
        presenter.setStudent(student);

        Button submit = (Button) view.findViewById(R.id.button);
        submit.setOnClickListener(this);

        Button delete = (Button) view.findViewById(R.id.button_delete);
        delete.setOnClickListener(this);

        return view;
    }

    /**
     * Called once the fragment is associated with its activity context.
     * It assigns this context to be responsible of the fragment transactions.
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
                        surnameEditText.getText().toString(),
                        iconPathTextView.getText().toString(),
                        Integer.parseInt(subjectIdTextView.getText().toString()));
                break;
            case R.id.button_delete:
                presenter.delete(Integer.parseInt(idTextView.getText().toString()));
                break;
            default:
                break;
        }
    }

    /**
     * Called when the ImageGetter activity finish returning the chosen image.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {

        switch (requestCode) {
            case IMAGE_INTENT:
                Bitmap bitmap = ImageGetter.getImageFromResult(getActivity(), resultCode, imageReturnedIntent);

                if (bitmap != null) {
                    String imagePath = FileUtil.getImagePath(getContext(), bitmap);
                    Log.d(TAG, "onActivityResult, bitmap Path: " + imagePath);

                    Drawable d = new BitmapDrawable(getResources(), bitmap);
                    iconPathTextView.setText(imagePath);
                    iconImageView.setImageDrawable(d);
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
                break;
        }
    }

    @Override
    public void loadStudentFragment() {
        callback.goBack();
    }

    @Override
    public void setError(String message) {
        showToastMessage(message);
    }

    private void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setSubjectItems(List<Subject> items) {
        subjectSpinner.setAdapter(new ArrayAdapter<Subject>(getContext(),
                R.layout.textview, items));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        presenter.onSubjectSelected(position);
        Log.d(TAG, "onItemSelected, " + position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        presenter.onSubjectSelected(0);
        Log.d(TAG, "onNothingSelected ");
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

    @Override
    public void setIconPath(String iconPath) {
        iconPathTextView.setText(iconPath);
        iconImageView.setImageDrawable(Drawable.createFromPath(iconPath));
    }

    @Override
    public void setName(String name) {
        nameEditText.setText(name);
    }

    @Override
    public void setSurname(String surname) {
        surnameEditText.setText(surname);
    }

    @Override
    public void setSubjectId(int id) {
        subjectIdTextView.setText(Integer.toString(id));
    }
}
