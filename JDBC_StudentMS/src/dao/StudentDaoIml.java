package dao;

import Utils.JDBCUtils;
import domain.Student;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class StudentDaoIml implements StudentDao{
    @Override
    public ArrayList<Student> findAll() {
        Connection con=null;
        PreparedStatement st =null;
        ResultSet rs=null;
        ArrayList<Student> list=new ArrayList<>();
        try {
            con= JDBCUtils.getConnection();
            String sql="SELECT * FROM student";
            st=con.prepareStatement(sql);
            rs=st.executeQuery();
            while (rs.next()){
                Date birthday = rs.getDate("birthday");
                Student student = new Student(rs.getInt("sid"), rs.getString("name"), rs.getInt("age"), birthday);
                list.add(student);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.close(con,st,rs);
        }
        return list;
    }

    @Override
    public Student findById(Integer id) {
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        Student student = new Student();
        try {
            con=JDBCUtils.getConnection();
            ps=con.prepareStatement("SELECT * FROM student WHERE sid=?");
            ps.setInt(1,id);
            rs= ps.executeQuery();
            while (rs.next()){
                student.setSid(rs.getInt("sid"));
                student.setName(rs.getString("name"));
                student.setAge(rs.getInt("age"));
                student.setBirthday(rs.getDate("birthday"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(con,ps,rs);
        }
        return student;
    }

    @Override
    public int insert(Student student) {
        Connection con=null;
        PreparedStatement ps=null;
        int result=0;
        try {
            con=JDBCUtils.getConnection();
            String sql="INSERT INTO student VALUES (?,?,?,?)";
            ps=con.prepareStatement(sql);
            ps.setInt(1,student.getSid());
            ps.setString(2,student.getName());
            ps.setInt(3,student.getAge());
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(student.getBirthday());
            ps.setString(4,date);
            result = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(con,ps);
        }
        return result;
    }

    @Override
    public int update(Student student) {
        Connection con=JDBCUtils.getConnection();
        int r=0;
        try {
            PreparedStatement ps=con.prepareStatement("UPDATE student SET sid=?,NAME=?,age=?,birthday=? WHERE sid=?");
            ps.setInt(1,student.getSid());
            ps.setString(2,student.getName());
            ps.setInt(3,student.getAge());
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(student.getBirthday());
            ps.setString(4,date);
            ps.setInt(5,student.getSid());
            r=ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    @Override
    public int delete(Integer id) {
        Connection con=JDBCUtils.getConnection();
        int result=0;
        try {
            PreparedStatement ps=con.prepareStatement("DELETE FROM student WHERE sid=?");
            ps.setInt(1,id);
            result=ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
