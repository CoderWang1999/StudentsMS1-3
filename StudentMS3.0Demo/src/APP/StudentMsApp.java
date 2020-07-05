package APP;

import Students.StudentDao;
import Users.UsersUtils;
import java.io.IOException;
import java.util.*;

//实现用户登录
public class StudentMsApp {
    public static HashMap<String, String> hm = new HashMap<>();

    public static void main(String[] args) throws IOException {
        UsersUtils.readFile();
        Scanner cin = new Scanner(System.in);
        out:
        while (true) {
            System.out.println("----欢迎来到学生信息管理系统登录页面----");
            System.out.println("1 已有账号？登录。");
            System.out.println("2 还没有账号？请注册。");
            System.out.println("3 退出");
            System.out.println("请输入你的选择：");
            //2. 用户键盘录入选项
            String num=cin.next();

            //3. 功能路由
            switch (num) {
                case "1":
                    Login(hm, cin);
                    break;
                case "2":
                    Register(hm, cin);
                    break;
                case "3":
                    System.out.println("再见");
                    break out;
                default:
                    //提示输入有误
                    System.out.println("您的输入有误~~");
                    break;
            }
        }
    }

    //封装用户注册  账号长度必须为8且只能为数字
    private static void Register(HashMap<String, String> hm, Scanner cin) throws IOException {
        while (true) {
            System.out.println("请输入您的账号：(账号只能由8位数字组成！)");
            String ID = cin.next();
            System.out.println("请输入您的密码：（密码的长度必须在8~12个字符长度内）");
            String password = cin.next();
            //首先判断账号的合法性即是否由八位数字组成
            if (ID.length() == 8 && isNumer(ID)) {
                //判断账号是否存在：
                if (!IDisExist(ID, hm)) {
                    //判断密码的长度是否在8~12个字符
                    if (password.length() >= 8 && password.length() <= 12) {
                        while (true) {
                            String Code = VerificationCode();
                            System.out.println("请输入您的验证码： " + Code);
                            String yzm = cin.next();
                            //判断验证码是否正确
                            if (yzm.equalsIgnoreCase(Code)) {
                                System.out.println("正在处理中，请稍后...");
                                hm.put(ID, password);
                                UsersUtils.reSave();
                                System.out.println("注册成功！");
                                return;
                            } else {
                                System.out.println("验证码输入有误请重新输入！");
                            }
                        }
                    } else {
                        System.out.println("您输入的密码不合法（密码长度必须为8~12位有效字符。）");
                        while (true){
                            //访问用户是否退出
                            System.out.println("是否返回主页面？（Y/N）");
                            String src=cin.next();
                            if (src.equalsIgnoreCase("y")){
                                return;
                            }else if (src.equalsIgnoreCase("n")){
                                break;
                            }else {
                                System.out.println("您的输入有误,请重新输入！");
                            }
                        }
                    }
                } else {
                    System.out.println("此账号已存在，请重新输入！");
                    while (true){
                        //访问用户是否退出
                        System.out.println("是否返回主页面？（Y/N）");
                        String src=cin.next();
                        if (src.equalsIgnoreCase("y")){
                            return;
                        }else if (src.equalsIgnoreCase("n")){
                            break;
                        }else {
                            System.out.println("您的输入有误,请重新输入！");
                        }
                    }
                }
            } else {
                System.out.println("您输入的账号不合法，请确保您的账号由8位数字组成。");
                while (true){
                    //访问用户是否退出
                    System.out.println("是否返回主页面？（Y/N）");
                    String src=cin.next();
                    if (src.equalsIgnoreCase("y")){
                        return;
                    }else if (src.equalsIgnoreCase("n")){
                        break;
                    }else {
                        System.out.println("您的输入有误,请重新输入！");
                    }
                }
            }
        }
    }


    //封装用户登录
    private static void Login(HashMap<String, String> hm, Scanner cin) throws IOException {
        int count = 2;
        out:
        while (true) {
            System.out.println("请输入账号：");
            String ID = cin.next();
            System.out.println("请输入密码：");
            String password = cin.next();
            //判断账号是否合法：
            if (ID.length() == 8 && isNumer(ID)) {
                //判断账号是否存在：
                if (IDisExist(ID, hm)) {
                    //判断密码是否正确
                    if (hm.get(ID).equals(password)) {
                        while (true) {
                            String Code = VerificationCode();
                            System.out.println("请输入您的验证码： " + Code);
                            String yzm = cin.next();
                            //判断验证码是否正确
                            if (yzm.equalsIgnoreCase(Code)) {
                                System.out.println("登录成功！");
                                StudentDao.Client();
                                break out;
                            } else {
                                System.out.println("验证码输入有误请重新输入！");
                            }
                        }
                    } else {
                        //密码错误输入只允许三次，超过三次直接冻结账号：
                        if (count == 0) {
                            System.out.println("您的账号已冻结，请于管理员联系~");
                            break;
                        }
                        System.out.println("密码输入错误，您还有" + count + "次机会");
                        while (true){
                            //访问用户是否退出
                            System.out.println("是否返回主页面？（Y/N）");
                            String src=cin.next();
                            if (src.equalsIgnoreCase("y")){
                                return;
                            }else if (src.equalsIgnoreCase("n")){
                                break;
                            }else {
                                System.out.println("您的输入有误,请重新输入！");
                            }
                        }
                        count--;
                    }
                } else {
                    System.out.println("此账号不存在，请重新输入！");
                    while (true){
                        //访问用户是否退出
                        System.out.println("是否返回主页面？（Y/N）");
                        String src=cin.next();
                        if (src.equalsIgnoreCase("y")){
                            return;
                        }else if (src.equalsIgnoreCase("n")){
                            break;
                        }else {
                            System.out.println("您的输入有误,请重新输入！");
                        }
                    }
                }
            } else {
                System.out.println("您输入的账号不合法，请确保您的账号由8位数字组成。");
                while (true){
                    //访问用户是否退出
                    System.out.println("是否返回主页面？（Y/N）");
                    String src=cin.next();
                    if (src.equalsIgnoreCase("y")){
                        return;
                    }else if (src.equalsIgnoreCase("n")){
                        break;
                    }else {
                        System.out.println("您的输入有误,请重新输入！");
                    }
                }
            }
        }
    }


    //生成随机验证码（由26个大写字母和26个小写字母以及0-9数字组成的四位随机码）
    private static String VerificationCode() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 'a'; i <= 'z'; i++) {
            arrayList.add(String.valueOf((char) i));
        }
        for (int i = 'A'; i <= 'Z'; i++) {
            arrayList.add(String.valueOf((char) i));
        }
        for (int i = 0; i < 10; i++) {
            String s = String.valueOf(i);
            arrayList.add(s);
        }
        Collections.shuffle(arrayList);
        String code = "";
        for (int i = 0; i < 4; i++) {
            code += arrayList.get(i);
        }
        return code;
    }


    //封装一个方法，判断账号是否已存在：
    private static boolean IDisExist(String id, HashMap<String, String> hm) {
        Set<String> keySet = hm.keySet();
        for (String s : keySet) {
            if (id.equals(s)) {
                return true;
            }
        }
        return false;
    }

    //判断一个字符串是否由纯数字组成
    public static boolean isNumer(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}