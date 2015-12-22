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

import java.util.List;

import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.adapter.TaskAdapter;
import beatprogramming.github.com.teacker_tracker.domain.Task;
import beatprogramming.github.com.teacker_tracker.callback.FragmentCallback;
import beatprogramming.github.com.teacker_tracker.view.TaskView;
import beatprogramming.github.com.teacker_tracker.presenter.TaskPresenter;


/**
 * Created by adrian on 27/11/2015.
 */
public class TaskFragment extends ListFragment implements TaskView {

    private static String TAG = TaskFragment.class.getName();

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
        presenter.onResume();
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
