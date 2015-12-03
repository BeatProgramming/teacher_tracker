package beatprogramming.github.com.teacker_tracker.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by malkomich on 27/11/15.
 */
public abstract class Review implements Serializable {

    private int id;
    private String name;
    private Subject subject;
    private List<Score> scores = new ArrayList<Score>();

    public Review() {

    }

    public Review(String name, Subject subject) {
        this.name = name;
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Subject getSubject() {

        return subject;
    }

    public void setSubject(Subject subject) {

        this.subject = subject;
    }

    public List<Score> getScores() {

        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    public void addScore(Score score) {
        scores.add(score);
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }
}
