package beatprogramming.github.com.teacker_tracker.fragments;

import android.content.Context;
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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import beatprogramming.github.com.teacker_tracker.DataSource;
import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.adapter.SubjectAdapter;
import beatprogramming.github.com.teacker_tracker.callback.FragmentCallback;
import beatprogramming.github.com.teacker_tracker.domain.Exam;
import beatprogramming.github.com.teacker_tracker.domain.Review;
import beatprogramming.github.com.teacker_tracker.domain.Subject;
import beatprogramming.github.com.teacker_tracker.presenter.ReviewUpdatePresenter;
import beatprogramming.github.com.teacker_tracker.presenter.StudentUpdatePresenter;
import beatprogramming.github.com.teacker_tracker.view.ReviewUpdateView;

/**
 * Created by malkomich on 27/11/15.
 */
public class ReviewUpdateFragment extends Fragment implements ReviewUpdateView, RadioGroup
        .OnCheckedChangeListener, View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = ReviewUpdateFragment.class.getName();
    private static final String REVIEW = "BUNDLE_REVIEW";

    private FragmentCallback callback;
    private ReviewUpdatePresenter presenter;

    private TextView idTextView;
    private RadioGroup typeRadioGroup;
    private TextView typeValueTextView;
    private EditText nameEditText;
    private Spinner subjectSpinner;
    private TextView subjectIdTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ReviewUpdatePresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    public static ReviewUpdateFragment newInstance(Review review) {

        ReviewUpdateFragment fragment = new ReviewUpdateFragment();

        Bundle args = new Bundle();
        args.putSerializable(REVIEW, review);
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_review_update, container, false);

        // Identify all fields of the form.
        idTextView = (TextView) view.findViewById(R.id.reviewId);
        typeRadioGroup = (RadioGroup) view.findViewById(R.id.review_type);
        typeValueTextView = (TextView) view.findViewById(R.id.review_type_value);
        nameEditText = (EditText) view.findViewById(R.id.review_name);
        subjectSpinner = (Spinner) view.findViewById(R.id.review_subject);
        subjectIdTextView = (TextView) view.findViewById(R.id.review_subject_id);

        typeRadioGroup.setOnCheckedChangeListener(this);
        subjectSpinner.setOnItemSelectedListener(this);

        Bundle args = getArguments();
        if (args != null) {

            Review review = (Review) args.getSerializable(REVIEW);

            idTextView.setText(Integer.toString(review.getId()));
            if (review instanceof Exam)
                typeRadioGroup.check(R.id.review_type_exam);
            else
                typeRadioGroup.check(R.id.review_type_project);

            nameEditText.setText(review.getName());
            subjectSpinner.setSelection(0);
        }

        Button submit = (Button) view.findViewById(R.id.button);
        submit.setOnClickListener(this);

        Button delete = (Button) view.findViewById(R.id.button_delete);
        delete.setOnClickListener(this);

        return view;
    }

    @Override
    public void setSubjectItems(List<Subject> items) {
        subjectSpinner.setAdapter(new ArrayAdapter<Subject>(getContext(),
                R.layout.textview, items));
    }

    @Override
     public void setSubjectId(int id) {
        subjectIdTextView.setText(Integer.toString(id));
    }

    @Override
    public void setTypeValue(String type) {
        typeValueTextView.setText(type);
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
                        Integer.parseInt(subjectIdTextView.getText().toString()),
                        typeValueTextView.getText().toString());
                break;
            case R.id.button_delete:
                presenter.delete(Integer.parseInt(idTextView.getText().toString()));
                break;
            default:
                break;
        }
    }

    @Override
    public void loadReviewFragment() {
        callback.goBack();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        int type = 0;
        switch (checkedId) {
            case R.id.review_type_exam:
                type = ReviewUpdatePresenter.RADIO_EXAM;
                break;
            case R.id.review_type_project:
                type = ReviewUpdatePresenter.RADIO_PROJECT;
                break;
            default:
                break;
        }
        presenter.onTypeChanged(type);
    }

    @Override
    public void setError(String message) {
        showToastMessage(message);
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
