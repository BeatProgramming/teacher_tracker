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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.callback.FragmentCallback;
import beatprogramming.github.com.teacker_tracker.domain.Student;
import beatprogramming.github.com.teacker_tracker.presenter.StudentUpdatePresenter;
import beatprogramming.github.com.teacker_tracker.util.FileUtil;
import beatprogramming.github.com.teacker_tracker.util.ImageGetter;
import beatprogramming.github.com.teacker_tracker.view.StudentUpdateView;

/**
 * Created by malkomich on 27/11/15.
 */
public class StudentUpdateFragment extends Fragment implements StudentUpdateView, View.OnClickListener {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student_update, container, false);

        // Identify all fields of the form.
        idTextView = (TextView) view.findViewById(R.id.studentId);
        nameEditText = (EditText) view.findViewById(R.id.name_field);
        surnameEditText = (EditText) view.findViewById(R.id.surname_field);
        iconImageView = (ImageView) view.findViewById(R.id.student_icon);
        iconPathTextView = (TextView) view.findViewById(R.id.student_icon_path);

        iconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseImageIntent = ImageGetter.getPickImageIntent(getActivity());
                startActivityForResult(chooseImageIntent, IMAGE_INTENT);
            }
        });

        Bundle args = getArguments();
        if (args != null) {

            Student student = (Student) args.getSerializable(STUDENT);

            String iconPath = student.getIconPath();
            if(iconPath != null) {
                iconPathTextView.setText(iconPath);
                iconImageView.setImageDrawable(Drawable.createFromPath(iconPath));
            }

            nameEditText.setText(student.getName());
            surnameEditText.setText(student.getSurname());

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
                        surnameEditText.getText().toString(),
                        iconPathTextView.getText().toString());
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
     *
     * @param requestCode
     * @param resultCode
     * @param imageReturnedIntent
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
}
