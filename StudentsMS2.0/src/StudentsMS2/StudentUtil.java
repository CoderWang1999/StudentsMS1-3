package StudentsMS2;

import java.io.*;
import java.util.ArrayList;

/*
    学生工具类
        1. 解决学号的自动生成问题
        2. 封装IO操作
 */
public class StudentUtil {
    private StudentUtil(){}
    private static String sId="ITheima_";
    private static int id=1;
    //专门将集合写入到文件
    public static void reSave(ArrayList<Student> list)throws IOException {
        BufferedWriter bw=new BufferedWriter(new FileWriter("StudentsMS2.0\\student.txt"));
        for (Student student : list) {
            bw.write(student.toString());
            bw.newLine();
            bw.flush();
        }
        bw.close();
    }
    //专门用来加载文件数据进内存
    public static void readFile(ArrayList<Student> list) throws IOException{
        BufferedReader br=new BufferedReader(new FileReader("StudentsMS2.0\\student.txt"));
        String line;
        while ((line=br.readLine())!=null){
            String []ns=line.split(",");
            Student s=new Student(ns[0],ns[1],ns[2],Integer.parseInt(ns[3]),ns[4]);
            list.add(s);
        }
        br.close();
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
