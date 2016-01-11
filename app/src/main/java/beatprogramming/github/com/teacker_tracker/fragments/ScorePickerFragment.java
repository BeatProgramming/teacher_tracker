package beatprogramming.github.com.teacker_tracker.fragments;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.callback.OnScorePickedListener;

/**
 * - Implementa el contador de selección númerica para las calificaciones
 */
public class ScorePickerFragment extends DialogFragment implements View.OnClickListener {

    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 10;
    private static final float INCREMENT = 0.1f;
    private OnScorePickedListener listener;
    private int itemPosition;
    private Button upButton;
    private Button downButton;
    private EditText editText;
    private float value;
    private DialogInterface.OnClickListener positiveButtonListener;
    private DialogInterface.OnClickListener negativeButtonListener;

    public static ScorePickerFragment newInstance(OnScorePickedListener listener, int position, Float value) {
        ScorePickerFragment fragment = new ScorePickerFragment();
        fragment.setOnScorePickedListener(listener);
        fragment.setItemPosition(position);
        fragment.setValue(value);
        fragment.initializeButtonListeners();

        return fragment;
    }

    private void initializeButtonListeners() {

        positiveButtonListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    float value_temp = Float.valueOf(String.valueOf(editText.getText()));
                    value = (float) (Math.round(value_temp * 100.0) / 100.0);
                    listener.onScorePicked(value, itemPosition);
                } catch (NumberFormatException e) {
                    listener.onScorePicked(null, itemPosition);
                }
            }
        };

        negativeButtonListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onScorePicked(null, itemPosition);
            }
        };
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //getting proper access to LayoutInflater is the trick. getLayoutInflater is a                   //Function
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_score_picker, null);

        upButton = (Button) view.findViewById(R.id.upButton);

        downButton = (Button) view.findViewById(R.id.downButton);
        editText = (EditText) view.findViewById(R.id.numberEditText);

        editText.setText(String.valueOf(value));

        upButton.setOnClickListener(this);
        downButton.setOnClickListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle(getActivity().getString(R.string.score_picker_title));

        // Button type changed for correct alignment
        builder.setNegativeButton(getActivity().getString(R.string.score_picker_ok), positiveButtonListener);
        builder.setNeutralButton(getActivity().getString(R.string.score_picker_cancel), negativeButtonListener);

        return builder.create();
    }

    public void setOnScorePickedListener(OnScorePickedListener onScorePickedListener) {
        this.listener = onScorePickedListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upButton:

                downButton.setBackgroundResource(R.drawable.numberpicker_down_normal);
                upButton.setBackgroundResource(R.drawable.numberpicker_up_pressed);
                if (value <= MAX_VALUE) {
                    value += INCREMENT;
                    value = (float) (Math.round(value * 100.0) / 100.0);
                }

                editText.setText(String.valueOf(value));
                break;
            case R.id.downButton:

                downButton.setBackgroundResource(R.drawable.numberpicker_down_pressed);
                upButton.setBackgroundResource(R.drawable.numberpicker_up_normal);
                if (value >= MIN_VALUE) {
                    value -= INCREMENT;
                    value = (float) (Math.round(value * 100.0) / 100.0);
                }
                editText.setText(String.valueOf(value));
                break;
            default:
                break;
        }
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    public void setValue(Float value) {
        if(value != null)
            this.value = value;
        else
            this.value = 0.0f;
    }
}
