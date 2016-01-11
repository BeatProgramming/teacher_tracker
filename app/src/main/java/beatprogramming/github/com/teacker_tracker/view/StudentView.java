package beatprogramming.github.com.teacker_tracker.view;
import java.util.List;
import beatprogramming.github.com.teacker_tracker.domain.Student;

/**
 * - Define los métodos  sobre los que tiene control el presentador que conecta
 *   la capa de persistencia con la vista.
 */
public interface StudentView {

    void setItems(List<Student> items);

    void loadStudentUpdateFragment(Student subject);

    Student getStudentFromAdapter(int position);

    void showLoading();

    void hideLoading();

}
