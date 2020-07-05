package Users;

import APP.StudentMsApp;

import java.io.*;
import java.util.HashMap;
public class UsersUtils {
    private UsersUtils() throws IOException {

    }

    //从本地取
    public static void readFile() {
        try (FileReader fr = new FileReader("StudentMS3.0Demo\\users.txt")) {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("StudentMS3.0Demo\\users.txt"));
            StudentMsApp.hm= (HashMap<String,String>) ois.readObject();
        } catch (Exception e) {

        }
    }

    //存在本地
    public static void reSave() {
        try (FileWriter fw = new FileWriter("StudentMS3.0Demo\\users.txt")) {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("StudentMS3.0Demo\\users.txt"));
            oos.writeObject(StudentMsApp.hm);
        } catch (Exception e) {
        }
    }
}