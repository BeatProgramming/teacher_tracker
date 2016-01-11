package beatprogramming.github.com.teacker_tracker.domain;
import java.io.Serializable;

/**
 * - Modela un estudiante de la base de datos.
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

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return surname + ", " + name;
    }
}
