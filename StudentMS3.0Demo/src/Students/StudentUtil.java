package Students;

import java.io.*;
import java.util.Set;
import java.util.TreeMap;

//定义学生工具类  实现自动生成学号 并封装文件读写
public class StudentUtil {
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
        try (FileWriter fw = new FileWriter("StudentMS3.0Demo\\students.txt")) {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("StudentMS3.0Demo\\students.txt"));
            oos.writeObject(StudentDao.tm);

        } catch (Exception e) {

        }
    }

    //封装读取文件
    public static void readFile() {
        try (FileReader fr = new FileReader("StudentMS3.0Demo\\students.txt")) {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("StudentMS3.0Demo\\students.txt"));
            StudentDao.tm = (TreeMap<String, Student>) ois.readObject();
        } catch (Exception e) {

        }
    }
}