package beatprogramming.github.com.teacker_tracker.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.joda.time.DateTime;

import java.util.List;

import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.adapter.TaskAdapter;
import beatprogramming.github.com.teacker_tracker.callback.FragmentCallback;
import beatprogramming.github.com.teacker_tracker.domain.Task;
import beatprogramming.github.com.teacker_tracker.presenter.TaskPresenter;
import beatprogramming.github.com.teacker_tracker.view.TaskView;


/** Fragmento de tareas
 * Created by adrian on 27/11/2015.
 */
public class TaskFragment extends ListFragment implements TaskView {

    private FragmentCallback callback;

    private ProgressBar progressBar;

    private TaskPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new TaskPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        DateTime date = new DateTime();
        getActivity().setTitle("   " + getDayString(date) + ", " + date.getDayOfMonth() + "-" +
                date.getMonthOfYear() + "-" + date.getYear());
        presenter.onResume();
    }

    private String getDayString(DateTime date) {
        if (date.getDayOfWeek()==1){ return getResources().getString(R.string.Lunes);}
        else if (date.getDayOfWeek()==2){ return getResources().getString(R.string.Martes);}
        else if (date.getDayOfWeek()==3){ return getResources().getString(R.string.Miercoles);}
        else if (date.getDayOfWeek()==4){ return getResources().getString(R.string.Jueves);}
        else if (date.getDayOfWeek()==5){ return getResources().getString(R.string.Viernes);}
        else if (date.getDayOfWeek()==6){ return getResources().getString(R.string.Sabado);}
        else return getResources().getString(R.string.Domingo);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_listview, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.progress);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onCreateTask();
            }
        });

        return view;
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        getListView().setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
        getListView().setVisibility(View.VISIBLE);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        presenter.onItemClicked(position);
    }

    @Override
    public void setItems(List<Task> items) {
        items = ordenarPorHora(items);
        setListAdapter(new TaskAdapter(getActivity(), R.layout.listview_task_row, items));
    }

    private List<Task> ordenarPorHora(List<Task> items) {
        return items;
    }

    @Override
    public void loadTaskUpdateFragment(Task task) {
        TaskUpdateFragment fragment = (task != null) ?
                TaskUpdateFragment.newInstance(task) : new TaskUpdateFragment();
        callback.replaceFragment(fragment);
    }

    @Override
    public Task getTaskFromAdapter(int position) {
        return (Task) getListAdapter().getItem(position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        callback = (FragmentCallback) context;
    }

}
