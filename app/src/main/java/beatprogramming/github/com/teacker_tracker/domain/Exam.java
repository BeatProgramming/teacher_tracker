package beatprogramming.github.com.teacker_tracker.domain;


import org.joda.time.DateTime;

/** Clase que modela un examen
 *
 * Created by malkomich on 27/11/15.
 */
public class Exam extends Review{

    public Exam(String name, Subject subject, DateTime dateTime) {
        super(name, subject, dateTime);
    }

}
