package beatprogramming.github.com.teacker_tracker.domain;

import org.joda.time.DateTime;

/**
 * Created by malkomich on 27/11/15.
 */
public class Project extends Review{

    public Project(String name, Subject subject, DateTime dateTime) {
        super(name, subject, dateTime);
    }

}
