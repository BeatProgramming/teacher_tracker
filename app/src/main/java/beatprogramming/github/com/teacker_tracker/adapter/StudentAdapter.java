package beatprogramming.github.com.teacker_tracker.adapter;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.domain.Student;

/**
 * - Implementación personalizada de ArrayAdapter respecto a estudiantes
 */
public class StudentAdapter extends ArrayAdapter {

    private static final String TAG = StudentAdapter.class.getName();
    private Context context;
    private int resource;

    public StudentAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = (convertView == null) ? inflater.inflate(resource, parent, false) : convertView;

        ImageView iconButton = (ImageView) rowView.findViewById(R.id.item_student_icon);
        TextView nameTextView = (TextView) rowView.findViewById(R.id.item_student_name);
        TextView surnameTextView = (TextView) rowView.findViewById(R.id.item_student_surname);

        Student student = (Student) getItem(position);

        String name = student.getName();
        String surname = student.getSurname();
        String iconPath = student.getIconPath();
        if(iconPath != null) {
            Drawable icon = Drawable.createFromPath(iconPath);
            iconButton.setImageDrawable(icon);
        }

        nameTextView.setText(name);
        surnameTextView.setText(surname);

        Log.d(TAG, "name: " + name + ", surname: " + surname);

        return rowView;
    }

}
