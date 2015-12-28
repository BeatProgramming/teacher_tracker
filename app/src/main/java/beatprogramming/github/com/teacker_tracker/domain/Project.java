package beatprogramming.github.com.teacker_tracker.domain;

import org.joda.time.DateTime;

/**
 * Clase que modela un proyecto, hereda de review.
 */
public class Project extends Review{

    public Project() {
        super();
    }

    public Project(String name, Subject subject, DateTime dateTime) {
        super(name, subject, dateTime);
    }

}
