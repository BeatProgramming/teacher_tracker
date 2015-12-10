package beatprogramming.github.com.teacker_tracker.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

import beatprogramming.github.com.teacker_tracker.callback.OnDateTimePickedListener;
import beatprogramming.github.com.teacker_tracker.presenter.TaskUpdatePresenter;

/**
 * Created by malkomich on 04/12/2015.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private OnDateTimePickedListener listener;

    public static DatePickerFragment newInstance(OnDateTimePickedListener listener) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setListener(listener);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        listener.onDatePicked(year, month, day);
    }

    public void setListener(OnDateTimePickedListener listener) {
        this.listener = listener;
    }
}
