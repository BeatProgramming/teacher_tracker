package beatprogramming.github.com.teacker_tracker.view;

import java.util.List;

import beatprogramming.github.com.teacker_tracker.domain.Subject;

/**
 * Created by malkomich on 04/01/2016.
 */
public interface ImportStudentsView {

    void setItems(List<Subject> items);

    void loadStudentFragment();

    Subject getSubjectFromAdapter(int position);

    void showLoading();

    void hideLoading();

    void setError(String message);
}
