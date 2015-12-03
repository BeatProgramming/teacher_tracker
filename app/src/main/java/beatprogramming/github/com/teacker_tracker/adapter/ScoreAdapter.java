package beatprogramming.github.com.teacker_tracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.domain.Score;
import beatprogramming.github.com.teacker_tracker.domain.Student;

/**
 * Created by malkomich on 13/11/15.
 */
public class ScoreAdapter extends ArrayAdapter {

    private final String TAG = ScoreAdapter.class.getName();

    private final Context context;
    private final int resource;
    private final List<Score> values;


    public ScoreAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.context = context;
        this.values = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = (convertView == null) ? inflater.inflate(resource, parent, false) : convertView;

        TextView studentTextView = (TextView) rowView.findViewById(R.id.item_score_student);
        TextView scoreTextView = (TextView) rowView.findViewById(R.id.item_score_score);

        Student student = values.get(position).getStudent();
        String name = student.getSurname() + ", " + student.getName();
        float score = values.get(position).getCalificacion();

        studentTextView.setText(name);
        scoreTextView.setText(String.valueOf(score));

        return rowView;
    }

}
