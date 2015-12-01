package beatprogramming.github.com.teacker_tracker.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import beatprogramming.github.com.teacker_tracker.DataSource;
import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.adapter.TaskAdapter;


/**
 * Created by adrian on 27/11/2015.
 */
public class TaskFragment extends ListFragment {

    private ArrayAdapter adaptador_main;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_listview, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "No implementado", Toast.LENGTH_SHORT).show();
            }
        });

        //Inicializa el adaptador con la fuente de datos
        adaptador_main = new TaskAdapter(
                getActivity(), R.layout.listview_task_row, DataSource.TASK);

        //Relacionando la lista con el adaptador
        setListAdapter(adaptador_main);
        return view;
    }

}
