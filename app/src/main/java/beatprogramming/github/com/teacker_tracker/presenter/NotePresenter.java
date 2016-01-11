package beatprogramming.github.com.teacker_tracker.presenter;

import android.text.Editable;

import java.io.Serializable;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;
import beatprogramming.github.com.teacker_tracker.domain.Task;
import beatprogramming.github.com.teacker_tracker.persistence.TaskDao;
import beatprogramming.github.com.teacker_tracker.persistence.TaskDaoImpl;
import beatprogramming.github.com.teacker_tracker.view.NoteView;

/**
 * Created by adrian on 11/01/2016.
 */
public class NotePresenter implements OnLoadFinishListener, OnUpdateFinishListener {

    private NoteView view;
    private TaskDao taskDao;

    private Task task;

    private String note;
    public NotePresenter(NoteView view) {
        this.view=view;
        taskDao= new TaskDaoImpl();
    }

    public void onResume() {
        fillView();
    }

    private void fillView() {
        if (task.getNote()!=null){
            view.setTextNote();
        }
    }

    public void setTask(Task task) {
        this.task=task;
    }

    @Override
    public void onLoadFinish(List<? extends Serializable> items) {
        note = (String) items.get(0);
    }

    public void saveNote(String text) {
        taskDao.updateTask(task.getId(),task.getNombre(),task.getSubject().getId(),task.getDateTime(), text, this);
    }

    @Override
    public void onError(String message) {
    }

    @Override
    public void onSuccess() {
    }
}
