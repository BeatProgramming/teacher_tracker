package beatprogramming.github.com.teacker_tracker.domain;

import java.io.Serializable;

/**
 * Created by malkomich on 11/11/15.
 */
public class Student implements Serializable{

    private int id;
    private String name;
    private String surname;
    private String iconPath;

    public Student() {

    }

    public Student(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }
}
