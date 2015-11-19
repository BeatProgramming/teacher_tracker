package beatprogramming.github.com.teacker_tracker.adapter;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.domain.Task;

/**
 * Created by Adrian on 07/11/2015.
 */
public class TaskAdapter extends ArrayAdapter<Task> {

    public TaskAdapter(Context context, List<Task> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        //Obteniendo una instancia del inflater
        LayoutInflater inflater = (LayoutInflater)getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Salvando la referencia del View de la fila
        View listItemView = convertView;

        //Comprobando si el View no existe
        if (null == convertView) {
            //Si no existe, entonces inflarlo con two_line_list_item.xml
            listItemView = inflater.inflate(
                    R.layout.listview_main_row,
                    parent,
                    false);
        }

        //Obteniendo instancias de los text views
        TextView subject = (TextView)listItemView.findViewById(R.id.subject_main);
        TextView classroom = (TextView)listItemView.findViewById(R.id.classroom_main);
        TextView hour = (TextView)listItemView.findViewById(R.id.hour_main);
        ImageButton newNoteButton = (ImageButton) listItemView.findViewById(R.id.newNote);

        newNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "HOLA", Toast.LENGTH_SHORT).show();
            }
        });
        //Obteniendo instancia de la Task en la posición actual
        Task item = (Task)getItem(position);

        subject.setText(item.getNombre());
        classroom.setText(item.getAux());
        hour.setText(item.getHora());

        //Devolver al ListView la fila creada
        return listItemView;

    }
}
