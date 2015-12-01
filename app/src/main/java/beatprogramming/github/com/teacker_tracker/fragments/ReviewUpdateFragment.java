package beatprogramming.github.com.teacker_tracker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import beatprogramming.github.com.teacker_tracker.DataSource;
import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.domain.Exam;
import beatprogramming.github.com.teacker_tracker.domain.Review;
import beatprogramming.github.com.teacker_tracker.domain.Subject;

/**
 * Created by malkomich on 27/11/15.
 */
public class ReviewUpdateFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    public static final String REVIEW = "Prueba";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_review_update, container, false);

        RadioGroup typeRadioGroup = (RadioGroup) view.findViewById(R.id.review_type);
        EditText nameEditText = (EditText) view.findViewById(R.id.review_name);
        Spinner subjectSpinner = (Spinner) view.findViewById(R.id.review_subject);

        typeRadioGroup.setOnCheckedChangeListener(this);
        subjectSpinner.setAdapter(new ArrayAdapter<Subject>(getContext(), R.layout.textview, DataSource.SUBJECT));

        Bundle args = getArguments();
        if (args != null) {
            Review review = (Review) args.getSerializable(REVIEW);

            if (review instanceof Exam)
                typeRadioGroup.check(R.id.review_type_exam);
            else
                typeRadioGroup.check(R.id.review_type_project);

            nameEditText.setText(review.getName());
            subjectSpinner.setSelection(0);
        }

        Button submit = (Button) view.findViewById(R.id.button);
        submit.setOnClickListener(submitListener);

        Button delete = (Button) view.findViewById(R.id.button_delete);
        delete.setOnClickListener(deleteListener);

        return view;
    }

    View.OnClickListener submitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getContext(), "Actualizada", Toast.LENGTH_SHORT).show();
            // Introducir asignatura en base de datos
        }
    };
    View.OnClickListener deleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getContext(), "Eliminada", Toast.LENGTH_SHORT).show();
            // Eliminar asignatura de la base de datos
        }
    };

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        String type = (checkedId == R.id.review_type_exam) ? "Examen" : "Práctica";
        Toast.makeText(getContext(), type + " seleccionado", Toast.LENGTH_SHORT).show();
    }
}
