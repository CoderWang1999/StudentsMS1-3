package Service;

import domain.Student;

import java.util.ArrayList;

public interface StudentService {
    public abstract ArrayList<Student> findAll();
    public abstract Student findById(Integer id);
    public abstract int insert(Student student);
    public abstract int update(Student student);
    public abstract int delete(Integer id);
}
