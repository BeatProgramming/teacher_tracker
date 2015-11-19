package beatprogramming.github.com.teacker_tracker;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.domain.Student;
import beatprogramming.github.com.teacker_tracker.util.CSVManager;

/**
 * Created by malkomich on 13/11/15.
 */
public class CSVManagerTest {

    @Test
    public void exportStudents() {
        CSVManager manager = CSVManager.getInstance(null);
        List<Student> students = new ArrayList<Student>();
        Student student1 = new Student();
            student1.setName("Juan Carlos");
            student1.setSurname("González Cabrero");
        Student student2 = new Student();
            student1.setName("Óscar");
            student1.setSurname("Fernández Núñez");
        Student student3 = new Student();
            student1.setName("Adrián");
            student1.setSurname("Martín Gómez");
        students.add(student1);
        students.add(student2);
        students.add(student3);

        manager.exportStudents(students);
    }
}
