package beatprogramming.github.com.teacker_tracker.domain;


import java.util.Map;

/** Clase que modela un examen
 *
 * Created by malkomich on 27/11/15.
 */
public class Exam extends Review{

    private Map <Student, Score> list;

    public Exam(Map<Student, Score> list, Subject subject) {
        super(subject);
        this.list = list;
    }

    public Map<Student, Score> getList() {
        return list;
    }

    public void setList(Map<Student, Score> list) {
        this.list = list;
    }

}
