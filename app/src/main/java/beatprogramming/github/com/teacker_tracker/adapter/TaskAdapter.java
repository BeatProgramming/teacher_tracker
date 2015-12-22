package beatprogramming.github.com.teacker_tracker.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.domain.Task;
import beatprogramming.github.com.teacker_tracker.util.DateTimeFormatter;

/**
 * Clase que modifica la clase ArrayAdapter para utilizarla en la actividad principal
 * Created by Adrian on 07/11/2015.
 */
public class TaskAdapter extends ArrayAdapter<Task> {

    private static String TAG = TaskAdapter.class.getName();

    private Context context;
    private int resource;

    public TaskAdapter(Context context, int resource, List<Task> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View listItemView = (convertView == null) ? inflater.inflate(resource, parent, false) : convertView;

        //Obtiene instancias de los text views
        TextView subject = (TextView) listItemView.findViewById(R.id.subject_main);
        TextView hour = (TextView) listItemView.findViewById(R.id.hour_main);
        TextView classroom = (TextView) listItemView.findViewById(R.id.classroom_main);
        ImageView newNoteButton = (ImageView) listItemView.findViewById(R.id.newNote);

        //Accion del newNoteButton
        newNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Sin implementar", Toast.LENGTH_SHORT).show();
            }
        });

        //Obtiene una instancia de la Task en la posición actual
        Task item = getItem(position);

        Log.d(TAG, "getView, " + item.getId() + ", " + item.getDateTime() + ", " + item
                .getNombre() + ", " + item.getNote());

        subject.setText(item.getSubject().getNombre() + " " + item.getSubject().getCurso());
        hour.setText(DateTimeFormatter.dateTimeToTimeString(item.getDateTime()));
        classroom.setText(item.getNombre());

        return listItemView;
    }
}
