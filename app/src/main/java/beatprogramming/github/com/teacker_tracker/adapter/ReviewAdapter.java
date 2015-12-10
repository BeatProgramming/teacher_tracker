package beatprogramming.github.com.teacker_tracker.adapter;

import android.content.Context;
import android.os.Build;
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
 * Created by malkomich on 13/11/15.
 */
public class ReviewAdapter extends ArrayAdapter {

    private final String TAG = ReviewAdapter.class.getName();

    private final Context context;
    private final int resource;
    private final List<Review> values;


    public ReviewAdapter(Context context, int resource, List objects) {
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

        ImageView iconView = (ImageView) rowView.findViewById(R.id.item_review_icon);
        TextView nameTextView = (TextView) rowView.findViewById(R.id.item_review_name);
        TextView subjectTextView = (TextView) rowView.findViewById(R.id.item_review_subject);

        int iconResource = (values.get(position) instanceof Exam) ? R.drawable.exam : R.drawable.work;
        String name = values.get(position).getName();
        String subject = values.get(position).getSubject().getNombre();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            iconView.setImageDrawable(context.getDrawable(iconResource));
        } else {
            iconView.setImageDrawable(context.getResources().getDrawable(iconResource));
        }
        nameTextView.setText(name);
        subjectTextView.setText(subject);

        return rowView;
    }

}
