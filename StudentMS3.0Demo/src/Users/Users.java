package Users;

import java.io.Serializable;

public class Users implements Serializable {
    private String ID;
    private String password;
    private static final long serialVersionUID = 42L;

    public Users() {
    }

    public Users(String ID, String password) {
        this.ID = ID;
        this.password = password;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
