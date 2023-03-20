package minhtvph26873.poly.pnlib.model;

public class Admin {
    private String User;
    private String Pass;

    public Admin() {
    }

    public Admin(String user, String pass) {
        User = user;
        Pass = pass;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }
}
