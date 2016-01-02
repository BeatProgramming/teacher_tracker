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
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.task_main);
        TextView subjectTextView = (TextView) listItemView.findViewById(R.id.task_subject);
        TextView hourTextView = (TextView) listItemView.findViewById(R.id.hour_main);
        ImageView newNoteButton = (ImageView) listItemView.findViewById(R.id.newNote);

        //Accion del newNoteButton
        newNoteButton.setOnClickListener(iconListener);

        //Obtiene una instancia de la Task en la posición actual
        Task task = getItem(position);

        nameTextView.setText(task.toString());
        subjectTextView.setText(task.getSubject().toString());
        hourTextView.setText(DateTimeFormatter.dateTimeToTimeString(task.getDateTime()));

        Log.d(TAG, "getView, " + task.getId() + ", " + task.getDateTime() + ", " + task
                .getNombre() + ", " + task.getNote());
        subject.setText(task.getSubject().getNombre() + " " + item.getSubject().getCurso());
        hour.setText(DateTimeFormatter.dateTimeToTimeString(item.getDateTime()));
        classroom.setText(task.getNombre());

        return listItemView;
    }

    View.OnClickListener iconListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Introduccion de un comentario de tarea.
            Toast.makeText(getContext(), "No implementado", Toast.LENGTH_SHORT).show();
        }
    };
}
