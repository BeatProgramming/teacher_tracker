package beatprogramming.github.com.teacker_tracker.adapter;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.domain.Exam;
import beatprogramming.github.com.teacker_tracker.domain.Review;

/**
 * - Implementación personalizada de ArrayAdapter respecto a evaluaciones
 */
public class ReviewAdapter extends ArrayAdapter {

    private final String TAG = ReviewAdapter.class.getName();
    private final Context context;
    private final int resource;

    public ReviewAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = (convertView == null) ? inflater.inflate(resource, parent, false) : convertView;

        ImageView iconView = (ImageView) rowView.findViewById(R.id.item_review_icon);
        TextView nameTextView = (TextView) rowView.findViewById(R.id.item_review_name);
        TextView dateTextView = (TextView) rowView.findViewById(R.id.item_review_date);
        TextView subjectNameTextView = (TextView) rowView.findViewById(R.id.item_review_subject_name);
        TextView subjectCourseTextView = (TextView) rowView.findViewById(R.id.item_review_subject_course);

        Review review = (Review) getItem(position);

        int iconResource = (review instanceof Exam) ? R.drawable.exam : R.drawable.work;
        String name = review.getName();
        String date = review.getDateTime().toString("E dd,MMM (HH:mm)");
        String subject = review.getSubject().getNombre();
        String course = review.getSubject().getCurso();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            iconView.setImageDrawable(context.getDrawable(iconResource));
        } else {
            iconView.setImageDrawable(context.getResources().getDrawable(iconResource));
        }
        nameTextView.setText(name);
        dateTextView.setText(date);
        subjectNameTextView.setText(subject);
        subjectCourseTextView.setText(course);

        Log.d(TAG, "getView, name: " + name + ", subject: " + subject);

        return rowView;
    }

}
