package beatprogramming.github.com.teacker_tracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.domain.Subject;

/** Clase que modifica la clase ArrayAdapter para utilizarla en la actividad principal
 * Created by Adrian on 07/11/2015.
 */
public class SubjectAdapter extends ArrayAdapter<Subject> {

    public SubjectAdapter(Context context, List<Subject> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        //Obtiene una instancia del inflater
        LayoutInflater inflater = (LayoutInflater)getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Guarda la referencia del View de la fila
        View listItemView = convertView;

        //Comprueba si el View no existe
        if (null == convertView) {
            listItemView = inflater.inflate(
                    R.layout.listview_subject_row,
                    parent,
                    false);
        }

        //Obtiene instancias de los text views
        TextView name = (TextView)listItemView.findViewById(R.id.item_subject);
        TextView course = (TextView)listItemView.findViewById(R.id.item_course);

        Subject item = (Subject)getItem(position);

        name.setText(item.getName());
        course.setText(item.getCourse());

        //Devuelve al ListView la fila creada
        return listItemView;

    }
}
