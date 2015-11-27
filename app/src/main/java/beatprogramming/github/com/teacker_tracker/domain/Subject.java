package beatprogramming.github.com.teacker_tracker.domain;

/**
 * Created by malkomich on 27/11/15.
 */
public class Subject {

    private String course;
    private String name;

    public Subject(String name, String course) {
        this.course=course;
        this.name=name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
