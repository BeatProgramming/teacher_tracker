package beatprogramming.github.com.teacker_tracker.view;

import java.util.List;

import beatprogramming.github.com.teacker_tracker.domain.Subject;

/**
 * Created by malkomich on 01/12/2015.
 */
public interface SubjectView {

    void setItems(List<Subject> items);

    void loadSubjectUpdateFragment(Subject subject);

    Subject getSubjectFromAdapter(int position);

    void showLoading();

    void hideLoading();
}
