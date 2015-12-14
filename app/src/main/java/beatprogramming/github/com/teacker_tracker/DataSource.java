package beatprogramming.github.com.teacker_tracker;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.domain.Exam;
import beatprogramming.github.com.teacker_tracker.domain.Project;
import beatprogramming.github.com.teacker_tracker.domain.Review;
import beatprogramming.github.com.teacker_tracker.domain.Score;
import beatprogramming.github.com.teacker_tracker.domain.Student;
import beatprogramming.github.com.teacker_tracker.domain.Subject;
import beatprogramming.github.com.teacker_tracker.domain.Task;

/**
 * Clase que simula una base de datos para las asignaturas creadas por un usuario
 * <p/>
 * Created by Adrian on 07/11/2015.
 */
public class DataSource {

    public final static List TASK = new ArrayList<Task>();
    public final static List SUBJECT = new ArrayList<Subject>();
    public static final List STUDENT = new ArrayList<Student>();
    public static final List SCORE = new ArrayList<Score>();
    public static final List REVIEW = new ArrayList<Review>();

    static {

        SUBJECT.add(new Subject("Lengua", "", "2º ESO"));
        SUBJECT.add(new Subject("Matemáticas", "", "1º ESO"));
        SUBJECT.add(new Subject("Filosofía", "", "2º ESO"));

    }

    static {

        TASK.add(new Task("a",(Subject) SUBJECT.get(0), new DateTime()));
        TASK.add(new Task("b", (Subject) SUBJECT.get(0), new DateTime()));
        TASK.add(new Task("c", (Subject) SUBJECT.get(1), new DateTime()));
        TASK.add(new Task("d", (Subject) SUBJECT.get(2), new DateTime()));
        TASK.add(new Task("e", (Subject) SUBJECT.get(2), new DateTime()));

    }

    static {

        STUDENT.add(new Student("Juan", "Dominguez"));
        STUDENT.add(new Student("Bonifacio", "Perez"));
        STUDENT.add(new Student("Ruperta", "Galindo"));

    }

    static {

        SCORE.add(new Score(5.3f, "Aprobado", (Student) STUDENT.get(0), (Review) REVIEW.get(0)));
        SCORE.add(new Score(4.0f, "Suspenso", (Student) STUDENT.get(1), (Review) REVIEW.get(1)));
        SCORE.add(new Score(9.1f, "Sobresaliente", (Student) STUDENT.get(2), (Review) REVIEW.get(2)));

    }

    static {

        REVIEW.add(new Exam("Parcial 1", (Subject) SUBJECT.get(0), new DateTime()));
        REVIEW.add(new Exam("Final", (Subject) SUBJECT.get(1), new DateTime()));
        REVIEW.add(new Project("Práctica final", (Subject) SUBJECT.get(2), new DateTime()));

    }

}
