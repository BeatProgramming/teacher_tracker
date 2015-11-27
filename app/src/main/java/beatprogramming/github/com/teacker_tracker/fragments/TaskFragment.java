package beatprogramming.github.com.teacker_tracker.fragments;

import android.support.v4.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import beatprogramming.github.com.teacker_tracker.DataSource;
import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.TempSubjectActivity;
import beatprogramming.github.com.teacker_tracker.adapter.TaskAdapter;


/**
 * Created by adrian on 27/11/2015.
 */
public class TaskFragment extends ListFragment {

    private ArrayAdapter adaptador_main;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),TempSubjectActivity.class);
                startActivity(intent);
            }
        });

        //Inicializa el adaptador con la fuente de datos
        adaptador_main = new TaskAdapter(
                getActivity(),
                DataSource.TASK);

        //Relacionando la lista con el adaptador
        setListAdapter(adaptador_main);
        return view;
    }

}
