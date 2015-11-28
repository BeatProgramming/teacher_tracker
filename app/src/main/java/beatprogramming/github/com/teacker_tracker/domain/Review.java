package beatprogramming.github.com.teacker_tracker.domain;

/**
 * Created by malkomich on 27/11/15.
 */
public class Review {

    private Subject subject;

    public Review(Subject subject) {
        this.subject = subject;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
