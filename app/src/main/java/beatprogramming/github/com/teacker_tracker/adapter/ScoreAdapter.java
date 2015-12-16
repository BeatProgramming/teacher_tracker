package beatprogramming.github.com.teacker_tracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.domain.Score;
import beatprogramming.github.com.teacker_tracker.domain.Student;

/**
 * Created by malkomich on 13/11/15.
 */
public class ScoreAdapter extends ArrayAdapter implements View.OnClickListener {

    private final String TAG = ScoreAdapter.class.getName();

    private final Context context;
    private final int resource;
    private final List<Student> studentList;


    public ScoreAdapter(Context context, int resource, List students) {
        super(context, resource);
        this.resource = resource;
        this.context = context;
        this.studentList = students;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = (convertView == null) ? inflater.inflate(resource, parent, false) : convertView;

        Spinner studentSpinner = (Spinner) rowView.findViewById(R.id.item_score_student);
        TextView scoreTextView = (TextView) rowView.findViewById(R.id.item_score_score);
        ImageView commentImageView = (ImageView) rowView.findViewById(R.id.item_score_comment);

        final Score score = (Score) getItem(position);
        Student student = score.getStudent();
        Float scoreValue = score.getCalificacion();
        String comment = score.getComentario();

        studentSpinner.setAdapter(new ArrayAdapter<Student>(context,
                R.layout.textview, studentList));
        if(student != null) {
            for (int i = 0; i < studentSpinner.getAdapter().getCount(); i++) {
                Student st = (Student) studentSpinner.getAdapter().getItem(i);
                if (st.getId() == student.getId()) {
                    studentSpinner.setSelection(i);
                    break;
                }
            }
        }
        studentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Student st = (Student) parent.getItemAtPosition(position);
                score.setStudent(st);
                studentList.remove(st);
                notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(scoreValue != null)
            scoreTextView.setText(Float.toString(scoreValue));
        scoreTextView.setOnClickListener(this);

        commentImageView.setOnClickListener(this);

        return rowView;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.item_score_score:
                break;
            case R.id.item_score_comment:
                break;
            default:
                break;

        }
    }
}
