package beatprogramming.github.com.teacker_tracker.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.domain.Review;
import beatprogramming.github.com.teacker_tracker.domain.Score;
import beatprogramming.github.com.teacker_tracker.domain.Student;

/**
 * Created by malkomich on 13/11/15.
 */
public class ScoreAdapter extends ArrayAdapter<Score> {

    private final String TAG = ScoreAdapter.class.getName();

    private final Context context;
    private final int resource;


    public ScoreAdapter(Context context, int resource, List<Score> items) {
        super(context, resource, items);
        this.resource = resource;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = (convertView == null) ? inflater.inflate(resource, parent, false) : convertView;

        final TextView studentTextView = (TextView) rowView.findViewById(R.id.item_score_student);
        final TextView scoreTextView = (TextView) rowView.findViewById(R.id.item_score_score);
        final ImageView commentImageView = (ImageView) rowView.findViewById(R.id.item_score_comment);

        final Score score = (Score) getItem(position);

        Student student = score.getStudent();
        Review review = score.getReview();
        Float scoreValue = score.getCalificacion();
        String comment = score.getComentario();

        studentTextView.setText(student.toString());

        if(scoreValue != null)
            scoreTextView.setText(Float.toString(scoreValue));
        else
            scoreTextView.setText("NP");

        commentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(scoreTextView.getText() == "NP")
                    Toast.makeText(context, context.getString(R.string.score_comment_toast), Toast.LENGTH_SHORT).show();
                else {
                    // Introduccion de un comentario de nota.
                    Toast.makeText(context, "No implementado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Log.d(TAG, "getView, score: " + score.toString());

        return rowView;
    }

}
