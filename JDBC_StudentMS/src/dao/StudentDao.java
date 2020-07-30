package dao;

import domain.Student;

import java.util.ArrayList;

public interface StudentDao {
    public abstract ArrayList<Student> findAll();
    public abstract Student findById(Integer id);
    public abstract int insert(Student student);
    public abstract int update(Student student);
    public abstract int delete(Integer id);
}
