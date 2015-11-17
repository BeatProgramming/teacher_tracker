package beatprogramming.github.com.teacker_tracker;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import beatprogramming.github.com.teacker_tracker.domain.Student;

/**
 * Created by malkomich on 13/11/15.
 */
public class StudentAdapter extends ArrayAdapter {

    private final String TAG = StudentAdapter.class.getName();

    private final Context context;
    private final int resource;
    private final List<Student> values;


    public StudentAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.context = context;
        this.values = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(resource, parent, false);

        TextView nameTextView = (TextView) rowView.findViewById(R.id.student_name);
        TextView surnameTextView = (TextView) rowView.findViewById(R.id.student_surname);
        String name = values.get(position).getName();
        String surname = values.get(position).getSurname();

        nameTextView.setText(name);
        surnameTextView.setText(surname);

        Log.d(TAG, "name: " + name + ", surname: " + surname);

        return rowView;
    }

}
