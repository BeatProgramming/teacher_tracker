package beatprogramming.github.com.teacker_tracker;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Adrian on 07/11/2015.
 */
public class TareaArrayAdapter extends ArrayAdapter<Tarea> {

    public TareaArrayAdapter(Context context, List<Tarea> objects) {
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
                    R.layout.image_list_item,
                    parent,
                    false);
        }

        //Obteniendo instancias de los text views
        TextView titulo = (TextView)listItemView.findViewById(R.id.text1);
        TextView subtitulo = (TextView)listItemView.findViewById(R.id.text2);
        TextView hora = (TextView)listItemView.findViewById(R.id.hora);
        //Obteniendo instancia de la Tarea en la posición actual
        Tarea item = (Tarea)getItem(position);

        titulo.setText(item.getNombre());
        subtitulo.setText(item.getAux());
        hora.setText(item.getHora());

        //Devolver al ListView la fila creada
        return listItemView;

    }
}
