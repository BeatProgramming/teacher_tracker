package beatprogramming.github.com.teacker_tracker;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import beatprogramming.github.com.teacker_tracker.adapter.TaskAdapter;

/**
 * Created by adrian on 27/11/2015.
 */
public class FragmentoMain extends Fragment {

    private ListView lista_main;
    private ArrayAdapter adaptador_main;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),SubjectActivity.class);
                startActivity(intent);
            }
        });

        //Instancia del ListView
        lista_main = (ListView)getView().findViewById(R.id.listViewMain);

        //Inicializa el adaptador con la fuente de datos
        adaptador_main = new TaskAdapter(
                getActivity(),
                DataSource.TASK);

        //Relacionando la lista con el adaptador
        lista_main.setAdapter(adaptador_main);
        return inflater.inflate(R.layout.content_main, container, false);
    }

}
