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
public class TareaArrayAdapter <T> extends ArrayAdapter<T> {

    public TareaArrayAdapter(Context context, List<T> objects) {
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
                    android.R.layout.two_line_list_item,
                    parent,
                    false);
        }

        //Obteniendo instancias de los text views
        TextView titulo = (TextView)listItemView.findViewById(android.R.id.text1);
        TextView subtitulo = (TextView)listItemView.findViewById(android.R.id.text2);

        //Obteniendo instancia de la Tarea en la posición actual
        T item = (T)getItem(position);

        //Dividir la cadena en Nombre y Hora
        String cadenaBruta;
        String subCadenas [];
        String delimitador = ",";

        cadenaBruta = item.toString();
        subCadenas = cadenaBruta.split(delimitador,2);

        titulo.setText(subCadenas[0]);
        subtitulo.setText(subCadenas[1]);

        //Devolver al ListView la fila creada
        return listItemView;

    }
}
