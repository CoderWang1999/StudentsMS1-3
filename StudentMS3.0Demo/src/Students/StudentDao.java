package Students;

import APP.StudentMsApp;

import java.io.IOException;
import java.util.*;

public class StudentDao {
    static TreeMap<String, Student> tm = new TreeMap<>();

    public static void Client() throws IOException {
        StudentUtil.readFile();
        Scanner cin = new Scanner(System.in);
        out:
        while (true) {
            System.out.println("-------------欢迎来到学生信息管理系统-------------");
            System.out.println("1 添加学生信息");
            System.out.println("2 删除学生信息");
            System.out.println("3 修改学生信息");
            System.out.println("4 查看学生信息");
            System.out.println("5 随机抽取幸运学生");
            System.out.println("6 退出");
            System.out.println("请输入你的选择:");
            //2. 用户键盘录入选项
            String num = cin.next();
            //3. 功能路由
            switch (num) {
                case "1":
                    addStudent(tm, cin);
                    break;
                case "2":
                    deleteStudent(tm, cin);
                    break;
                case "3":
                    updateStudent(tm, cin);
                    break;
                case "4":
                    findStu(tm, cin);
                    break;
                case "5":
                    searchStu(tm);
                    break;
                case "6":
                    System.out.println("谢谢使用,再见~");
                    System.exit(0);    //退出虚拟机
                default:
                    //提示输入有误
                    System.out.println("您的输入有误~~");
                    break;
            }
        }
    }

    //随机抽取幸运同学
    private static void searchStu(TreeMap<String, Student> tm) {
        //先判断容器中是否有数据,有数据才遍历,没有数据,给出去添加的操作提示
        if (tm.size() == 0) {
            System.out.println("系统暂无数据,请先添加学生信息~");
        } else {
            //获取学号中最大数字：
            Set<String> keys = tm.keySet();
            int maxId = 0;
            for (String key : keys) {
                String id = key.substring(key.indexOf("_") + 1);
                int i = Integer.parseInt(id);
                if (i > maxId) {
                    maxId = i;
                }
            }
            while (true) {
                Random r = new Random();
                int rkey = r.nextInt(maxId) + 1;
                String k = null;
                if (rkey < 10) {
                    k = "ITheima_00" + rkey;
                } else if (rkey >= 10 && rkey < 100) {
                    k = "ITheima_0" + rkey;
                } else if (rkey >= 100) {
                    k = "ITheima_" + rkey;
                }
                //判断此学号是否存在
                String string = idIsExist(k, tm);
                if (string.equals("no") == false) {
                    Student s = tm.get(k);
                    System.out.println("恭喜" + s.getName() + "被抽中！");
                    return;
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
        int age = 0;
        //判断年龄是否合法
        while (true) {
            String Sage = sc.next();
            if (StudentMsApp.isNumer(Sage) && Integer.parseInt(Sage) > 0 && Integer.parseInt(Sage) < 120) {
                age = Integer.parseInt(Sage);
                break;
            } else {
                System.out.println("您输入的年龄不合法，请重新输入!");
            }
        }

        System.out.println("请输入专业：");
        String major = sc.next();
        System.out.println("请输入生源地：");
        String hometown = sc.next();
        System.out.println("正在处理中，请稍后...");

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
    private static void findStu(TreeMap<String, Student> tm, Scanner sc) {
        //先判断容器中是否有数据,有数据才遍历,没有数据,给出去添加的操作提示
        if (tm.size() == 0) {
            System.out.println("系统暂无数据,请先添加学生信息~");
        } else {
            System.out.println("1.查看所有学生信息~");
            System.out.println("2.给定学生学号，查找该学生~");
            System.out.println("3.给定学生姓名，查找该学生~");
            System.out.println("请输入你的选择：");
            String choose = sc.next();
            switch (choose) {
                case "1":
                    System.out.println("学号\t\t\t姓名\t\t年龄\t\t专业\t\t生源地");
                    Set<String> keys = tm.keySet();
                    for (String key : keys) {
                        Student s = tm.get(key);
                        System.out.println(s.getSid() + "\t\t" + s.getName() + "\t\t" + s.getAge()
                                + "岁\t\t" + s.getMajor() + "\t\t" + s.getHometown());
                    }
                    break;
                case "2":
                    //按照学号查找
                    //1. 让用户输入要查找的学号信息
                    System.out.println("请输入要查找的学生编号:");
                    String sid = sc.next();
                    String index = idIsExist(sid, tm);
                    // 对index进行判断
                    if (index.equals("no") == false) {
                        //存在
                        Student s = tm.get(index);
                        System.out.println("学号\t\t\t姓名\t\t年龄\t\t专业\t\t生源地");
                        System.out.println(s.getSid() + "\t\t" + s.getName() + "\t\t" + s.getAge()
                                + "岁\t\t" + s.getMajor() + "\t\t" + s.getHometown());
                    } else {
                        //如果程序能走到这,说明用户输入的学号不存在
                        System.out.println("您输入的学号有误,请检查后重新操作!");
                    }
                    break;
                case "3":
                    //按照姓名查找
                    //1.让用户输入要查找的学生姓名
                    System.out.println("请输入要查找的学生姓名：");
                    String name = sc.next();
                    String s = nameIsExist(name, tm);
                    if (s.equals("no")) {
                        System.out.println("您输入的姓名不存在,请检查后重新操作!");
                    } else {
                        System.out.println("学号\t\t\t姓名\t\t年龄\t\t专业\t\t生源地");
                        for (Student stu : arr) {
                            System.out.println(stu.getSid() + "\t\t" + stu.getName() + "\t\t" + stu.getAge()
                                    + "岁\t\t" + stu.getMajor() + "\t\t" + stu.getHometown());
                        }
                    }
                    break;
                default:
                    System.out.println("您的输入有误~~");
            }
        }
    }

    //删除学生信息
    private static void deleteStudent(TreeMap<String, Student> tm, Scanner sc) {
        //先判断容器中是否有数据,有数据才遍历,没有数据,给出去添加的操作提示
        if (tm.size() == 0) {
            System.out.println("系统暂无数据,请先添加学生信息~");
        } else {
            System.out.println("1.给定学号进行删除");
            System.out.println("2.给定姓名进行删除");
            System.out.println("请输入你的选择：");
            String choose = sc.next();
            switch (choose) {
                case "1":
                    //1. 让用户输入要删除的学号信息
                    System.out.println("请输入要删除的学生编号:");
                    String sid = sc.next();
                    //2. 遍历集合,取到每个学生对象
                    String index = idIsExist(sid, tm);
                    //3. 对index进行判断
                    if (index.equals("no") == false) {
                        //存在
                        System.out.println("您确定要删除此学生的所有信息吗？（Y/N）");
                        String r = sc.next();
                        if (r.equalsIgnoreCase("y")) {
                            System.out.println("正在处理中，请稍后...");
                            tm.remove(index);
                            //将更改后的集合重新写入到文件
                            StudentUtil.reSave();
                            System.out.println("删除成功!");
                        } else if (r.equalsIgnoreCase("n")) {
                            System.out.println("已取消删除操作");
                        }
                    } else {
                        //如果程序能走到这,说明用户输入的学号不存在
                        System.out.println("您输入的学号有误,请检查后重新操作!");
                    }
                    break;
                case "2":
                    //按照姓名删除
                    //1.让用户输入要删除的学生姓名
                    System.out.println("请输入要删除的学生姓名：");
                    String name = sc.next();
                    String s = nameIsExist(name, tm);
                    //对s进行判断
                    if (s.equals("no")) {
                        //如果程序能走到这,说明用户输入的姓名不存在
                        System.out.println("您输入的姓名不存在,请检查后重新操作!");
                    } else if (s.equals("1")) {
                        //姓名唯一，直接删除
                        Student student = arr.get(0);
                        Set<String> keySet = tm.keySet();
                        //存在
                        System.out.println("您确定要删除此学生的所有信息吗？（Y/N）");
                        String r = sc.next();
                        if (r.equalsIgnoreCase("y")) {
                            System.out.println("正在处理中，请稍后...");
                            try {
                                for (String key : keySet) {
                                    if (tm.get(key).toString().equals(student.toString())) {
                                        tm.remove(key);
                                        //将更改后的集合重新写入到文件
                                        StudentUtil.reSave();
                                        System.out.println("删除成功!");
                                    }
                                }
                            } catch (Exception e) {

                            }
                        } else if (r.equalsIgnoreCase("n")) {
                            System.out.println("已取消删除操作");
                        }
                    } else if (s.equals("2")) {
                        //有重名的请按照学号删除
                        System.out.println("对不起，此姓名不唯一，无法执行操作，请按照学号删除您要删除的学生~");
                    }
                    break;
                default:
                    System.out.println("您的输入有误，请重新输入");
                    break;
            }
        }
    }

    //修改学生信息
    private static void updateStudent(TreeMap<String, Student> tm, Scanner sc) {
        //先判断容器中是否有数据,有数据才遍历,没有数据,给出去添加的操作提示
        if (tm.size() == 0) {
            System.out.println("系统暂无数据,请先添加学生信息~");
        } else {
            //1. 键盘录入要修改的学生学号
            System.out.println("请输入要修改的学生学号:");
            String sid = sc.next();
            //2. 针对输入的学号进行校验,遍历集合,拿每个学生对象的学号和输入的学号进行比对
            String index = idIsExist(sid, tm);
            if (index.equals("no") == false) {
                Student s = tm.get(index);
                //3. 匹配到了: 修改该学生的信息,进行键盘录入学生的新数据,将新数据替换老数据
                System.out.println("1.修改该学生的姓名~");
                System.out.println("2.修改该学生的年龄~");
                System.out.println("3.修改该学生的专业~");
                System.out.println("4.修改该学生的生源地~");
                System.out.println("请输入您的选择：");
                String i = sc.next();
                switch (i) {
                    case "1":
                        System.out.println("请输入姓名:");
                        String name = sc.next();
                        System.out.println("正在处理中，请稍后...");
                        s.setName(name);
                        //将更改后的集合重新写入到文件
                        StudentUtil.reSave();
                        System.out.println("修改成功!");
                        break;
                    case "2":
                        System.out.println("请输入年龄:");
                        int age = 0;
                        //判断年龄是否合法
                        while (true) {
                            String Sage = sc.next();
                            if (StudentMsApp.isNumer(Sage) && Integer.parseInt(Sage) > 0 && Integer.parseInt(Sage) < 120) {
                                age = Integer.parseInt(Sage);
                                break;
                            } else {
                                System.out.println("您输入的年龄不合法，请重新输入!");
                            }
                        }
                        System.out.println("正在处理中，请稍后...");
                        s.setAge(age);
                        //将更改后的集合重新写入到文件
                        StudentUtil.reSave();
                        System.out.println("修改成功!");
                        break;
                    case "3":
                        System.out.println("请输入专业：");
                        String major = sc.next();
                        System.out.println("正在处理中，请稍后...");
                        s.setMajor(major);
                        //将更改后的集合重新写入到文件
                        StudentUtil.reSave();
                        System.out.println("修改成功!");
                        break;
                    case "4":
                        System.out.println("请输入生源地：");
                        String hometown = sc.next();
                        System.out.println("正在处理中，请稍后...");
                        s.setHometown(hometown);
                        //将更改后的集合重新写入到文件
                        StudentUtil.reSave();
                        System.out.println("修改成功!");
                        break;
                    default:
                        System.out.println("您的输入有误~");
                        break;
                }
            } else {
                //4. 没有匹配到: 给出提示信息: 您输入的学号有误,请查看后重新输入
                System.out.println("您输入的学号有误,请检查后重新操作!");
            }
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

    //封装一个方法,专门用来判断姓名是否存在   如果不存在返回no,唯一返回"1"，存在不唯一返回"2"

    static ArrayList<Student> arr = new ArrayList<>();//存放按照姓名找到的学生

    private static String nameIsExist(String name, TreeMap<String, Student> tm) {
        arr.clear();//确保每次对arr操作前里面的内容都是空的
        int count = 0;
        Set<String> keys = tm.keySet();
        for (String key : keys) {
            Student s = tm.get(key);
            if (name.equals(s.getName())) {
                count++;
                arr.add(tm.get(key));
            }
        }
        if (count > 1) {
            return "2";
        } else if (count == 1) {
            return "1";
        }
        return "no";
    }
}