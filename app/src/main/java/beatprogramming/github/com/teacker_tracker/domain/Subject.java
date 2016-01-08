package beatprogramming.github.com.teacker_tracker.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que modela una asignatura de la base de datos.
 */
public class Subject implements Serializable {

    private int id;
    private String nombre;
    private String descripcion;
    private String curso;
    private List<Student> studentList;

    public Subject() {

    }

    public Subject(String nombre, String descripcion, String curso) {
        this.nombre=nombre;
        this.descripcion = descripcion;
        this.curso=curso;

        studentList = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }


    public String getCurso() {
        return curso;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void addStudents(Student... studentList) {
        for(Student student: studentList) {
            if(!this.studentList.contains(student))
                this.studentList.add(student);
        }
    }

    @Override
    public String toString(){
        return nombre + ", " + curso;
    }

}
