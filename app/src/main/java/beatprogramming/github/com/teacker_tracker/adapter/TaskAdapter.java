package beatprogramming.github.com.teacker_tracker.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.Serializable;
import java.util.List;
import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.callback.OnNoteClickedListener;
import beatprogramming.github.com.teacker_tracker.domain.Schedule;
import beatprogramming.github.com.teacker_tracker.domain.Task;
import beatprogramming.github.com.teacker_tracker.util.DateTimeFormatter;

/**
 * - Implementación personalizada de ArrayAdapter respecto a tareas
 */
public class TaskAdapter extends ArrayAdapter<Serializable> {

    private Context context;
    private int resource;
    private OnNoteClickedListener callback;

    private List<Serializable> itemList;

    public TaskAdapter(Context context, int resource, List<Serializable> objects, OnNoteClickedListener callback) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.callback = callback;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View listItemView = (convertView == null) ? inflater.inflate(resource, parent, false) : convertView;

        //Obtiene instancias de los text views
        TextView nameSubjectTextView = (TextView) listItemView.findViewById(R.id.taskSubject);
        TextView nameTaskTextView = (TextView) listItemView.findViewById(R.id.taskName);
        TextView hourTextView = (TextView) listItemView.findViewById(R.id.hourTask);
        ImageView newNoteButton = (ImageView) listItemView.findViewById(R.id.newNoteTask);

        //Obtiene una instancia de la Task en la posición actual
        Serializable serializable = getItem(position);

        if(serializable instanceof Task) {
            final Task task = (Task) serializable;

            nameSubjectTextView.setText(task.getSubject().toString());
            nameTaskTextView.setText(task.toString());
            hourTextView.setText(DateTimeFormatter.dateTimeToTimeString(task.getDateTime()));

            //Accion del newNoteButton
            newNoteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onNoteClicked(task);
                }
            });
        } else {

            Schedule schedule = (Schedule) serializable;

            nameSubjectTextView.setText(schedule.getSubject().toString());
            nameTaskTextView.setText(schedule.getAula());
            hourTextView.setText(schedule.getDateTime());

            newNoteButton.setVisibility(View.INVISIBLE);
            newNoteButton.setClickable(false);
        }

        return listItemView;
    }

    public void orderAdd(List<Serializable> items) {

        for(Serializable item: items) {

            if(item instanceof Task) {

            } else {

            }
        }

        notifyDataSetChanged();
    }
}
