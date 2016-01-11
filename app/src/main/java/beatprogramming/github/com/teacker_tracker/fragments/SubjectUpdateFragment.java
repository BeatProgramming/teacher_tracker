package beatprogramming.github.com.teacker_tracker.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.joda.time.DateTime;

import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.domain.Schedule;
import beatprogramming.github.com.teacker_tracker.domain.Subject;
import beatprogramming.github.com.teacker_tracker.callback.FragmentCallback;
import beatprogramming.github.com.teacker_tracker.persistence.ScheduleDaoImpl;
import beatprogramming.github.com.teacker_tracker.util.DateTimeFormatter;
import beatprogramming.github.com.teacker_tracker.view.SubjectUpdateView;
import beatprogramming.github.com.teacker_tracker.presenter.SubjectUpdatePresenter;

public class SubjectUpdateFragment extends Fragment implements SubjectUpdateView, View.OnClickListener {

    private static final String TAG = SubjectUpdateFragment.class.getName();
    private static final String SUBJECT = "BUNDLE_SUBJECT";

    private FragmentCallback callback;
    private SubjectUpdatePresenter presenter;

    private TextView idSubjectTextView;
    private EditText nameEditText;
    private EditText descriptionEditText;
    private EditText courseEditText;
    private EditText classRoomEditText;
    private ToggleButton monToggleButton;
    private ToggleButton tueToggleButton;
    private ToggleButton wedToggleButton;
    private ToggleButton thuToggleButton;
    private ToggleButton friToggleButton;
    private ToggleButton satToggleButton;
    private ToggleButton sunToggleButton;
    private TextView idScheduleTextView;

    private Boolean[] days;
    private TextView timeTextView;

    // false para modificar, true para crear
    private boolean create;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SubjectUpdatePresenter(this);
    }

    /**
     * Instantiate the fragment with an existing subject to modify.
     *
     */
    public static SubjectUpdateFragment newInstance(Subject subject) {

        SubjectUpdateFragment fragment = new SubjectUpdateFragment();

        Bundle args = new Bundle();
        args.putSerializable(SUBJECT, subject);
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onResume() {
        super.onResume();
        if (create){
            getActivity().setTitle("   " + getResources().getString(R.string.subjectCreateTitle));
        } else{
            getActivity().setTitle("   " + getResources().getString(R.string.subjectUpdateTitle));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_subject_update, container, false);

        create = true;
        // Identify all fields of the form.
        idSubjectTextView = (TextView) view.findViewById(R.id.subjectId);
        nameEditText = (EditText) view.findViewById(R.id.subjectName);
        descriptionEditText = (EditText) view.findViewById(R.id.subjectDescription);
        courseEditText = (EditText) view.findViewById(R.id.subjectCourse);
        idScheduleTextView = (TextView) view.findViewById(R.id.scheduleId);
        classRoomEditText = (EditText) view.findViewById(R.id.subjectClassroom);
        monToggleButton = (ToggleButton) view.findViewById(R.id.ToggleButtonMon);
        tueToggleButton = (ToggleButton) view.findViewById(R.id.ToggleButtonTue);
        wedToggleButton = (ToggleButton) view.findViewById(R.id.ToggleButtonWed);
        thuToggleButton = (ToggleButton) view.findViewById(R.id.ToggleButtonThu);
        friToggleButton = (ToggleButton) view.findViewById(R.id.ToggleButtonFri);
        satToggleButton = (ToggleButton) view.findViewById(R.id.ToggleButtonSat);
        sunToggleButton = (ToggleButton) view.findViewById(R.id.ToggleButtonSun);
        timeTextView = (TextView) view.findViewById(R.id.scheduleHour);
        timeTextView.setOnClickListener(this);
        DateTime actual = new DateTime();
        timeTextView.setText(DateTimeFormatter.timeToString(actual.getHourOfDay(), actual.getMinuteOfHour()));
        Bundle args = getArguments();

        if (args != null) {
            Subject subject = (Subject) args.getSerializable(SUBJECT);
            ScheduleDaoImpl s = new ScheduleDaoImpl();
            Schedule schedule = s.findScheduleBySubjectId(subject.getId());
            create=false;
            if (schedule != null){
                classRoomEditText.setText(schedule.getAula());
                idScheduleTextView.setText(Integer.toString(schedule.getId()));
                days = schedule.getDias();
                monToggleButton.setChecked(days[0]);
                tueToggleButton.setChecked(days[1]);
                wedToggleButton.setChecked(days[2]);
                thuToggleButton.setChecked(days[3]);
                friToggleButton.setChecked(days[4]);
                satToggleButton.setChecked(days[5]);
                sunToggleButton.setChecked(days[6]);
                timeTextView.setText(schedule.getDateTime());
            }
            idSubjectTextView.setText(Integer.toString(subject.getId()));
            nameEditText.setText(subject.getNombre());
            descriptionEditText.setText(subject.getDescripcion());
            courseEditText.setText(subject.getCurso());

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
    public void setTaskTime(String timeString) {
        timeTextView.setText(timeString);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button:
                if (days==null){ days = new Boolean[7];}
                days[0] = monToggleButton.isChecked();
                days[1] = tueToggleButton.isChecked();
                days[2] = wedToggleButton.isChecked();
                days[3] = thuToggleButton.isChecked();
                days[4] = friToggleButton.isChecked();
                days[5] = satToggleButton.isChecked();
                days[6] = sunToggleButton.isChecked();
                Log.d(TAG, "update Subject, days: " + days[0] + days[1] + days[2] + days[3] + days[4] + days[5] + days[6]);

                presenter.submit(Integer.parseInt(idSubjectTextView.getText().toString()),
                        nameEditText.getText().toString(),
                        descriptionEditText.getText().toString(),
                        courseEditText.getText().toString(),
                        Integer.parseInt(idScheduleTextView.getText().toString()),
                        timeTextView.getText().toString(),
                        days,
                        classRoomEditText.getText().toString()
                        );
                break;
            case R.id.button_delete:
                presenter.delete(Integer.parseInt(idSubjectTextView.getText().toString()));
                break;
            case R.id.scheduleHour:
                presenter.showTimePicker();
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
    public void showDialog(DialogFragment fragment) {
        callback.showDialog(fragment);
    }


    @Override
    public void setError(String message) {
        showToastMessage(message);
    }

    private void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
