package beatprogramming.github.com.teacker_tracker.view;

import java.util.List;

import beatprogramming.github.com.teacker_tracker.domain.Score;
import beatprogramming.github.com.teacker_tracker.domain.Student;

/**
 * Created by malkomich on 01/12/2015.
 */
public interface ScoreView {

    void setItems(List<Score> items);

    void showLoading();

    void hideLoading();

    void newItem();

    void createAdapter(List<Student> studentList);
}
