package Controller;

import Service.ServiceIml;
import Service.StudentService;
import domain.Student;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

public class StudentController {
    StudentService ss=new ServiceIml();
    @Test
    public void findAll(){
        ArrayList<Student> arrayList=ss.findAll();
        for (Student student : arrayList) {
            System.out.println(student);
        }
    }
    @Test
    public void findByID(){
        Student stu=ss.findById(5);
        System.out.println(stu);
    }
    @Test
    public void insert(){
        int i = ss.insert(new Student(5, "柳岩", 23, new Date()));
        if (i!=0){
            System.out.println("添加成功");
        }else {
            System.out.println("删除成功");
        }
    }
    @Test
    public void uodate(){
        Student student=ss.findById(5);
        student.setName("杨幂");
        student.setAge(25);
        int i = ss.update(student);
        if (i!=0){
            System.out.println("修改成功");
        }else {
            System.out.println("修改失败");
        }
    }
    @Test
    public void delete(){
        int i = ss.delete(6);
        if (i!=0){
            System.out.println("删除成功");
        }else {
            System.out.println("删除失败");
        }

    }
}
