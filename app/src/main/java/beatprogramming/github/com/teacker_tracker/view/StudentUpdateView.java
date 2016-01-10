package beatprogramming.github.com.teacker_tracker.view;

import java.util.List;

import beatprogramming.github.com.teacker_tracker.domain.Subject;

/** Interfaz
 * Created by malkomich on 02/12/2015.
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
}
