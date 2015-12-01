package beatprogramming.github.com.teacker_tracker.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import beatprogramming.github.com.teacker_tracker.DataSource;
import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.adapter.StudentAdapter;
import beatprogramming.github.com.teacker_tracker.domain.Student;

/**
 * Created by malkomich on 30/11/15.
 */
public class StudentFragment extends ListFragment {

    StudentAdapter adapter;

    LayoutInflater inflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.inflater = inflater;
        View view = inflater.inflate (R.layout.fragment_listview, container, false);

        adapter = new StudentAdapter(getActivity(),R.layout.listview_student_row, DataSource.STUDENT);

        setListAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                createDialog().show();
            }
        });

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

    }

    private AlertDialog.Builder createDialog() {
        final View prompt = inflater.inflate(R.layout.student_prompt, null);

        AlertDialog.Builder student_form = new AlertDialog.Builder(getContext());
        student_form.setView(prompt);
        student_form.create();
        student_form.setCancelable(false).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EditText nameValue = (EditText) prompt.findViewById(R.id.name_field);
                EditText surnameValue = (EditText) prompt.findViewById(R.id.surname_field);
                Student newStudent = new Student(nameValue.getText().toString(), surnameValue.getText().toString());
                adapter.addStudents(newStudent);
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        return student_form;
    }
}
