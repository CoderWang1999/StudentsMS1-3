package StudentMS2_2;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Student implements Serializable{
    private String sid;
    private String name;
    private String major;
    private int age;
    private String address;
    private static final long serialVersionUID = 42L;

    public Student(String sid, String name, String major, int age, String address) {
        this.sid = sid;
        this.name = name;
        this.major = major;
        this.age = age;
        this.address = address;
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

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    //重写toSting方法
    @Override
    public String toString() {
        return sid+","+name+","+major+","+age+","+address;
    }
}
class StudentUtil {
    private StudentUtil(){}
    private static String sId="ITheima_";
    private static int id=1;
    //专门将集合写入到文件
    public static void reSave() {
        try (ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("StudentsMS2.0\\student.txt"))){
            oos.writeObject(Demo2_2.arrayList);
        }catch (Exception e){

        }
    }
    //专门用来加载文件数据进内存
    public static void readFile(){
        try(ObjectInputStream ois=new ObjectInputStream(new FileInputStream("StudentsMS2.0\\student.txt"))){
            Demo2_2.arrayList=(ArrayList<Student>)ois.readObject();
        }catch (Exception e){

        }
    }
    //生成唯一学号
    public static String getSid(ArrayList<Student> list){
        int maxId=0;
        for (Student student : list) {
            String sid=student.getSid();
            String id=sid.substring(sid.indexOf("_")+1);
            if(Integer.valueOf(id)>maxId){
                maxId=Integer.valueOf(id);
            }
        }
        id=maxId+1;
        String nId=null;
        if (id<10){
            nId=sId+"00"+id;
        }else if(id>=10&&id<100){
            nId=sId+"0"+id;
        }else if(id>=100){
            nId=sId+id;
        }
        return nId;
    }
}

public class Demo2_2 {
    static ArrayList<Student> arrayList=new ArrayList<>();
    public static void main(String[] args) {

        StudentUtil.readFile();
        out:while (true){
            System.out.println("-----欢迎来到学生管理系统-----");
            System.out.println("     1.添加学生");
            System.out.println("     2.删除学生");
            System.out.println("     3.修改学生");
            System.out.println("     4.查看所有学生");
            System.out.println("     5.退出");
            System.out.println("    请输入你的选择");
            Scanner cin=new Scanner(System.in);
            int a=cin.nextInt();
            switch (a){
                case 1:
                    addStudent(arrayList,cin);
                    break;
                case 2:
                    deleteStudent(arrayList,cin);
                    break;
                case 3:
                    updateStudent(cin,arrayList);
                    break;
                case 4:
                    lookStudent(arrayList);
                    break;
                case 5:
                    System.out.println("谢谢使用");
                    break out;
            }
        }
    }

    //学生信息修改
    private static void updateStudent(Scanner cin, ArrayList<Student> arrayList) {
        System.out.println("请输入要修改学生的学号：");
        String sid=cin.next();
        int index=sidISexist(sid,arrayList);
        if(index!=-1){
            Student student=arrayList.get(index);
            System.out.println("请输入姓名：");
            String name=cin.next();
            System.out.println("请输入专业：");
            String maior=cin.next();
            System.out.println("请输入年龄：");
            int age=cin.nextInt();
            System.out.println("请输入住址：");
            String adress=cin.next();
            student.setName(name);
            student.setMajor(maior);
            student.setAge(age);
            student.setAddress(adress);
            StudentUtil.reSave();
            System.out.println("修改成功！");
        }else {
            System.out.println("您输入的学号有误，请重新操作！");
        }
    }

    //删除学生信息
    private static void deleteStudent(ArrayList<Student> arrayList, Scanner cin) {
        //没有信息可供删除的情况下：

        if (arrayList.size()==0){
            System.out.println("暂无信息可供删除！");
            return;
        }
        System.out.println("请输入学号:");
        String sid;
        //如果学号输入错误则重新输入直至输入正确
        while (true){
            sid=cin.next();

            int index=sidISexist(sid,arrayList);
            if (index==-1){
                System.out.println("您输入的学号有误，请重新输入！");
            }else{
                break;
            }
        }
        int index=sidISexist(sid,arrayList);
        arrayList.remove(index);
        StudentUtil.reSave();
        System.out.println("删除成功！");
    }

    //查看所有学生信息
    private static void lookStudent(ArrayList<Student> arrayList) {

        if(arrayList.size()==0){
            System.out.println("暂无任何学生信息，请返回添加！");
        }else{
            System.out.println("学号           姓名     专业    年龄      住址");
            for (int i = 0; i < arrayList.size(); i++) {
                Student student=arrayList.get(i);
                System.out.println(student.getSid()+"   "+student.getName()+"     "+student.getMajor()+"      "+student.getAge()+"     "+student.getAddress());
            }
        }
    }

    //创建添加学生信息的封装
    private static void addStudent(ArrayList<Student> arrayList, Scanner cin)  {
        StudentUtil.getSid(arrayList);
        System.out.println("请输入姓名：");
        String name=cin.next();
        System.out.println("请输入专业：");
        String maior=cin.next();
        System.out.println("请输入年龄：");
        int age=cin.nextInt();
        System.out.println("请输入住址：");
        String adress=cin.next();
        Student s=new Student(StudentUtil.getSid(arrayList),name,maior,age,adress);
        arrayList.add(s);
        StudentUtil.reSave();
        System.out.println("添加成功！");
    }


    //创建学号是否存在的封装   如果存在返回索引  否则返回-1
    private static int sidISexist(String sid,ArrayList<Student> arrayList){
        for (int i = 0; i < arrayList.size(); i++) {
            Student s=arrayList.get(i);
            if(s.getSid().equals(sid)){
                return i;
            }
        }
        return -1;
    }
}