package StudentMS2_3;

import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

//定义学生类并重写toSting方法
class Student implements Serializable {
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

//定义学生工具类  实现自动生成学号 并封装文件读写
class StudentUtil {
    //工具类构造方法私有，所有成员变量和成员方法静态
    private static String sid = "ITheima_";
    private static int id = 1;

    private StudentUtil() {
    }

    //学号唯一
    public static String getSid(TreeMap<String, Student> treeMap) {
        int maxId = 0;
        Set<String> keys = treeMap.keySet();
        for (String key : keys) {
            String id = key.substring(key.indexOf("_") + 1);
            int i = Integer.parseInt(id);
            if (i > maxId) {
                maxId = i;
            }
        }
        maxId++;
        String nId = null;
        if (maxId < 10) {
            nId = sid + "00" + maxId;
        } else if (maxId >= 10 && maxId < 100) {
            nId = sid + "0" + maxId;
        } else if (maxId >= 100) {
            nId = sid + maxId;
        }
        return nId;
    }

    //封装文件写入
    public static void reSave() {
        try (FileWriter fw = new FileWriter("StudentsMS2.0\\students.txt")) {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("StudentsMS2.0\\students.txt"));
            oos.writeObject(Demo3.tm);
        } catch (Exception e) {

        }
    }

    //封装读取文件
    public static void readFile() {
        try (FileReader fr = new FileReader("StudentsMS2.0\\students.txt")) {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("StudentsMS2.0\\students.txt"));
            Demo3.tm = (TreeMap<String, Student>) ois.readObject();
        } catch (Exception e) {

        }
    }
}

public class Demo3 {
    static TreeMap<String, Student> tm = new TreeMap<>();

    public static void main(String[] args) {

        StudentUtil.readFile();
        Scanner cin = new Scanner(System.in);
        out:
        while (true) {
            System.out.println("--------欢迎来到学生信息管理系统--------");
            System.out.println("1 添加学生信息");
            System.out.println("2 删除学生信息");
            System.out.println("3 修改学生信息");
            System.out.println("4 查看学生信息");
            System.out.println("5 随机抽取幸运学生");
            System.out.println("6 退出");
            System.out.println("请输入你的选择:");
            //2. 用户键盘录入选项
            int num = cin.nextInt();

            //3. 功能路由
            switch (num) {
                case 1:
                    addStudent(tm, cin);
                    break;
                case 2:
                    deleteStudent(tm, cin);
                    break;
                case 3:
                    updateStudent(tm, cin);
                    break;
                case 4:
                    findAll(tm);
                    break;
                case 5:
                    searchStu(tm);
                    break;
                case 6:
                    System.out.println("谢谢使用!");
//                    return;  //结束方法
//                     System.exit(0);    //退出虚拟机
                    break out;  //指定退出while
            }
        }
    }

    private static void searchStu(TreeMap<String, Student> tm) {
        Random r = new Random();
        //先判断容器中是否有数据,有数据才遍历,没有数据,给出去添加的操作提示
        if (tm.size() == 0) {
            System.out.println("系统暂无数据,请先添加学生信息~");
        } else {
            int rkey = r.nextInt(tm.size()) + 1;
            String k = null;
            if (rkey < 10) {
                k = "ITheima_00" + rkey;
            } else if (rkey >= 10 && rkey < 100) {
                k = "ITheima_0" + rkey;
            } else if (rkey >= 100) {
                k = "ITheima_" + rkey;
            }
            Set<String> keys = tm.keySet();
            for (String key : keys) {
                if (key.equals(k)) {
                    Student s = tm.get(key);
                    System.out.println("恭喜" + s.getName() + "被抽中！");
                }
            }
        }
    }


    //添加学生方法
    private static void addStudent(TreeMap<String, Student> tm, Scanner sc) {
        String sid = StudentUtil.getSid(tm);

        System.out.println("请输入姓名:");
        String name = sc.next();
        System.out.println("请输入年龄:");
        int age = sc.nextInt();
        System.out.println("请输入专业：");
        String major = sc.next();
        System.out.println("请输入家乡：");
        String hometown = sc.next();

        //创建学生对象，把键盘录入的数据赋值给学生对象的成员变量
        //String sid, String name, int age, String major, String hometown
        Student s = new Student(sid, name, age, major, hometown);

        //往集合中添加学生对象
        tm.put(sid, s);

        //将更改后的集合重新写入到文件
        StudentUtil.reSave();
        //提示添加成功
        System.out.println("添加成功!");
    }


    //查看学生信息
    private static void findAll(TreeMap<String, Student> tm) {
        //先判断容器中是否有数据,有数据才遍历,没有数据,给出去添加的操作提示
        if (tm.size() == 0) {
            System.out.println("系统暂无数据,请先添加学生信息~");
        } else {
            System.out.println("学号\t\t姓名\t\t年龄\t\t专业\t\t家乡");
            Set<String> keys = tm.keySet();
            for (String key : keys) {
                Student s = tm.get(key);
                System.out.println(s.getSid() + "\t" + s.getName() + "\t\t" + s.getAge()
                        + "岁\t\t" + s.getMajor() + "\t\t" + s.getHometown());
            }
        }
    }

    //删除学生信息
    private static void deleteStudent(TreeMap<String, Student> tm, Scanner sc) {
        //1. 让用户输入要删除的学号信息
        System.out.println("请输入要删除的学生编号:");
        String sid = sc.next();
        //2. 遍历集合,取到每个学生对象
        String index = idIsExist(sid, tm);
        //3. 对index进行判断
        if (index.equals("no") == false) {
            //存在
            tm.remove(index);

            //将更改后的集合重新写入到文件
            StudentUtil.reSave();
            System.out.println("删除成功!");
        } else {
            //如果程序能走到这,说明用户输入的学号不存在
            System.out.println("您输入的学号有误,请检查后重新操作!");
        }
    }

    //修改学生信息
    private static void updateStudent(TreeMap<String, Student> tm, Scanner sc) {
        //1. 键盘录入要修改的学生学号
        System.out.println("请输入要修改的学生学号:");
        String sid = sc.next();
        //2. 针对输入的学号进行校验,遍历集合,拿每个学生对象的学号和输入的学号进行比对
        String index = idIsExist(sid, tm);
        if (index.equals("no") == false) {
            Student s = tm.get(index);
            //3. 匹配到了: 修改该学生的信息,进行键盘录入学生的新数据,将新数据替换老数据
            System.out.println("请输入姓名:");
            String name = sc.next();
            System.out.println("请输入年龄:");
            int age = sc.nextInt();
            System.out.println("请输入专业：");
            String major = sc.next();
            System.out.println("请输入家乡：");
            String hometown = sc.next();

            //正宗的修改应该这样玩:
            s.setName(name);
            s.setAge(age);
            s.setMajor(major);
            s.setHometown(hometown);
            //将更改后的集合重新写入到文件
            StudentUtil.reSave();
            System.out.println("修改成功!");
        } else {
            //4. 没有匹配到: 给出提示信息: 您输入的学号有误,请查看后重新输入
            System.out.println("您输入的学号有误,请检查后重新操作!");
        }
    }


    //封装一个方法,专门用来判断学号是否存在
    private static String idIsExist(String sid, TreeMap<String, Student> tm) {
        Set<String> keys = tm.keySet();
        for (String key : keys) {
            Student s = tm.get(key);
            if (sid.equals(s.getSid())) {
                return key;
            }
        }
        return "no";
    }
}