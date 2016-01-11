package beatprogramming.github.com.teacker_tracker.domain;
import org.joda.time.DateTime;

/**
 * - Modela un examen, hereda de review
 */
public class Exam extends Review{

    public Exam() {
        super();
    }

    public Exam(String name, Subject subject, DateTime dateTime) {
        super(name, subject, dateTime);
    }

}
