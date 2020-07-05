package Students;

import java.io.Serializable;

//定义学生类并重写toSting方法
public class Student implements Serializable {
    private String sid;
    private String name;
    private int age;
    private String major;
    private String hometown;

    private static final long serialVersionUID = 42L;

    public Student() {
    }

    public Student(String sid, String name, int age, String major, String hometown) {
        this.sid = sid;
        this.name = name;
        this.age = age;
        this.major = major;
        this.hometown = hometown;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    @Override
    public String toString() {
        //String sid, String name, int age, String major, String hometown
        return sid + "," + name + "," + age + "," + major + "," + hometown;
    }
}