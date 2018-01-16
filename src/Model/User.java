package Model;

public class User {
    private int id;
    private String login;
    private String password;
    private String avatar; // Path
    private String color;

    public User() {
    }

    public User(int id, String login, String password, String avatar, String color) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.avatar = avatar;
        this.color = color;
    }

    public String createUserTable(){
        return "CREATE TABLE IF NOT EXISTS User(id integer PRIMARY KEY, login text NOT NULL, password text NOT NULL, avatar text, color text);";
    }

    public String checkLogin() {
        return "SELECT login FROM User WHERE login=" + "'" + this.login + "';";
    }

    /*public String checkConnection(){
        SHA512 hashpass = new SHA512(this.password);
        return "SELECT id FROM User WHERE login=" + "'" + this.login + "'" + "AND password =" + "'" + hashpass.getPass() + "';";
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}