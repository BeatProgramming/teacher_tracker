package beatprogramming.github.com.teacker_tracker.view;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.domain.Subject;

/**
 * - Define los métodos  sobre los que tiene control el presentador que conecta
 *   la capa de persistencia con la vista.
 */
public interface StudentUpdateView {

    void loadStudentFragment();

    void setError(String message);

    void setSubjectItems(List<Subject> items);

    void setSubjectId(int id);

    void setSubject(int subjectId);

    void setIconPath(String iconPath);

    void setName(String name);

    void setSurname(String surname);

    void setId(int id);
}
