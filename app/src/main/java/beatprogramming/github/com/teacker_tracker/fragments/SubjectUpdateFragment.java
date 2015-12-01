package beatprogramming.github.com.teacker_tracker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.domain.Subject;

/**
 * Created by malkomich on 27/11/15.
 */
public class SubjectUpdateFragment extends Fragment {

    public static final String SUBJECT = "Asignatura";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_subject_update, container, false);

        Bundle args = getArguments();
        if(args != null) {

            Subject subject = (Subject) args.getSerializable(SUBJECT);

            EditText nameEditText = (EditText) view.findViewById(R.id.subjectName);
            EditText descriptionEditText = (EditText) view.findViewById(R.id.subjectDescription);
            EditText courseEditText = (EditText) view.findViewById(R.id.subjectCurse);
            EditText classRoomEditText = (EditText) view.findViewById(R.id.subjectAula);

            nameEditText.setText(subject.getNombre());
            descriptionEditText.setText(subject.getDescripcion());
            courseEditText.setText(subject.getCurso());
            classRoomEditText.setText(subject.getAula());
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

}
