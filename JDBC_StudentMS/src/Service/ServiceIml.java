package Service;

import dao.StudentDao;
import dao.StudentDaoIml;
import domain.Student;

import java.util.ArrayList;

public class ServiceIml implements StudentService {
    StudentDao dao=new StudentDaoIml();
    @Override
    public ArrayList<Student> findAll() {
        return dao.findAll();
    }

    @Override
    public Student findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public int insert(Student student) {
        return dao.insert(student);
    }

    @Override
    public int update(Student student) {
        return dao.update(student);
    }

    @Override
    public int delete(Integer id) {
        return dao.delete(id);
    }
}
