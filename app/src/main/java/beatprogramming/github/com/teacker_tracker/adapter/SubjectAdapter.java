package beatprogramming.github.com.teacker_tracker.adapter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.domain.Subject;

/**
 * - Implementación personalizada de ArrayAdapter respecto a asignaturas
 */
public class SubjectAdapter extends ArrayAdapter<Subject> {

    private static String TAG = SubjectAdapter.class.getName();
    private Context context;
    private int resource;

    public SubjectAdapter(Context context, int resource, List<Subject> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Obtiene una instancia del inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Guarda la referencia del View de la fila
        View listItemView = (convertView == null) ? inflater.inflate(resource, parent, false) : convertView;

        //Obtiene instancias de los text views
        TextView name = (TextView) listItemView.findViewById(R.id.item_subject);
        TextView course = (TextView) listItemView.findViewById(R.id.item_course);

        Subject subject = getItem(position);

        name.setText(subject.getNombre());
        course.setText(subject.getCurso());

        Log.d(TAG, "getView, subject: " + subject.toString());

        //Devuelve al ListView la fila creada
        return listItemView;

    }
}
