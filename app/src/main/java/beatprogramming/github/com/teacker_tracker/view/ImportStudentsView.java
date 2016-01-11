package beatprogramming.github.com.teacker_tracker.view;

import java.util.List;

import beatprogramming.github.com.teacker_tracker.domain.Subject;

/**
 * - Define los métodos  sobre los que tiene control el presentador que conecta
 *   la capa de persistencia con la vista.
 */
public interface ImportStudentsView {

    void setItems(List<Subject> items);

    void loadStudentFragment();

    Subject getSubjectFromAdapter(int position);

    void showLoading();

    void hideLoading();

    void setError(String message);
}
