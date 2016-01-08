package beatprogramming.github.com.teacker_tracker.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import java.util.Calendar;
import beatprogramming.github.com.teacker_tracker.callback.OnDateTimePickedListener;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private OnDateTimePickedListener listener;

    public static TimePickerFragment newInstance(OnDateTimePickedListener listener) {
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setListener(listener);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        listener.onTimePicked(hourOfDay, minute);
    }

    public void setListener(OnDateTimePickedListener listener) {
        this.listener = listener;
    }
}
