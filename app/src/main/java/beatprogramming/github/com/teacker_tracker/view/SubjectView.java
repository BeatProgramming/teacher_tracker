package beatprogramming.github.com.teacker_tracker.view;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.domain.Subject;

/**
 * - Define los métodos  sobre los que tiene control el presentador que conecta
 *   la capa de persistencia con la vista.
 */
public interface SubjectView {

    void setItems(List<Subject> items);

    void loadSubjectUpdateFragment(Subject subject);

    Subject getSubjectFromAdapter(int position);

    void showLoading();

    void hideLoading();
}
