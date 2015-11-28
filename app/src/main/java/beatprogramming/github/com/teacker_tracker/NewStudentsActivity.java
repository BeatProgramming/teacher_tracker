package beatprogramming.github.com.teacker_tracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.adapter.StudentAdapter;
import beatprogramming.github.com.teacker_tracker.domain.Student;
import beatprogramming.github.com.teacker_tracker.util.CSVManager;

public class NewStudentsActivity extends AppCompatActivity {

    private final String TAG =NewStudentsActivity.class.getName();

    private StudentAdapter adapter;
    private List<Student> studentList = new ArrayList<Student>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_students);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Intent intent = getIntent();

        if(Intent.ACTION_VIEW.equals(intent.getAction())){
            //uri = intent.getStringExtra("URI");
            Uri uri = intent.getData();

            Log.d(TAG, "path: " + uri.getPath());
            Log.d(TAG, "encode path: " + uri.getEncodedPath());
            Log.d(TAG, "complete path: " + uri.toString());

            List<Student> newStudents = CSVManager.getInstance(this).importStudents(uri.getEncodedPath());
            if(newStudents != null)
                studentList.addAll(newStudents);

        }

        ListView studentsListView = (ListView) findViewById(R.id.list_students);
        adapter = new StudentAdapter(this, R.layout.listview_student_item_row, studentList);
        studentsListView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                createDialog().show();
            }
        });
    }

    private AlertDialog.Builder createDialog() {
        final View prompt = getLayoutInflater().inflate(R.layout.student_prompt, null);

        AlertDialog.Builder student_form = new AlertDialog.Builder(this);
        student_form.setView(prompt);
        student_form.create();
        student_form.setCancelable(false).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EditText nameValue = (EditText) prompt.findViewById(R.id.name_field);
                EditText surnameValue = (EditText) prompt.findViewById(R.id.surname_field);
                Student newStudent = new Student(nameValue.getText().toString(), surnameValue.getText().toString());
                studentList.add(newStudent);
                adapter.notifyDataSetChanged();
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        return student_form;
    }

}
