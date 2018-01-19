package Client;

import java.io.Serializable;

public class Authtentification implements Serializable {

    private static final long serialVersionUID = -6425471594092345976L;

    private String login;
    private Pair<String, String> message = new Pair("","");
    private int isConnected = 0;
    private int privateId = -1;


    public Authtentification(String login, int isConnected) {
        this.login = login;
        this.isConnected = isConnected;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Pair getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message.y = message;
    }

    public String getMessageLogin() {
        return message.x;
    }

    public void setMessageLogin() {
        this.message.x = this.login;
    }

    public int getIsConnected() {
        return isConnected;
    }

    public void setIsConnected(int isConnected) {
        this.isConnected = isConnected;
    }

    public int getPrivateId() {
        return privateId;
    }

    public void setPrivateId(int privateId) {
        this.privateId = privateId;
    }

    public void setPair(String x, String y) {
        this.message.x = x;
        this.message.y = y;
    }

    public Pair<String, String> getPair() {
        return this.message;
    }
}
