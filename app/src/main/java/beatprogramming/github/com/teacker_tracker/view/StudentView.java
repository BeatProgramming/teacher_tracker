package beatprogramming.github.com.teacker_tracker.view;

import java.io.Serializable;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.domain.Student;
import beatprogramming.github.com.teacker_tracker.domain.Subject;

/** Interfaz
 * Created by malkomich on 01/12/2015.
 */
public interface StudentView {

    void setItems(List<Student> items);

    void loadStudentUpdateFragment(Student subject);

    Student getStudentFromAdapter(int position);

    void showLoading();

    void hideLoading();

}
