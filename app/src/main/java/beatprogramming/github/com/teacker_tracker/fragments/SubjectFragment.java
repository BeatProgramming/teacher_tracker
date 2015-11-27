package beatprogramming.github.com.teacker_tracker.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import beatprogramming.github.com.teacker_tracker.DataSource;
import beatprogramming.github.com.teacker_tracker.ModifySubjectActivity;
import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.adapter.SubjectAdapter;

/**
 * Created by malkomich on 27/11/15.
 */
public class SubjectFragment extends ListFragment {

    private final static String ID = "_id";
    private final static String ASIGNATURA = "Asignatura";
    private final static String CURSO = "Curso";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subject, container);
        createList();

        return view;
    }

    public void createList(){

        SubjectAdapter adapter = new SubjectAdapter(getActivity(), DataSource.SUBJECT);

        setListAdapter(adapter);
        getListView().setOnItemClickListener(itemClickListener);
    }

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView textoTitulo = (TextView) view.findViewById(R.id.item_subject);
            TextView textoCurso = (TextView) view.findViewById(R.id.item_course);
            Intent intent = new Intent(getActivity(),ModifySubjectActivity.class);
            intent.putExtra("asignatura",textoTitulo.getText());
            intent.putExtra("curso",textoCurso.getText());
            startActivity(intent);
        }
    };
}
