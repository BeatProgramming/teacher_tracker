package beatprogramming.github.com.teacker_tracker.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;
import java.util.Calendar;
import beatprogramming.github.com.teacker_tracker.callback.OnDateTimePickedListener;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private OnDateTimePickedListener listener;

    public static DatePickerFragment newInstance(OnDateTimePickedListener listener) {

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setListener(listener);
        return fragment;
    }

    @NonNull
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
        Log.d("OnDateSet", "año " + year + " mes" + month+1 + " dia " + day);
        listener.onDatePicked(year, month+1, day);
    }

    public void setListener(OnDateTimePickedListener listener) {
        this.listener = listener;
    }
}
