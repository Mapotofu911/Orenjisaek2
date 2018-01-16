package Client;

import java.io.Serializable;

public class Authtentification implements Serializable {

    private static final long serialVersionUID = -6425471594092345976L;

    private String login, message;
    private int isConnected = 0;
    private int privateId = -1;


    public Authtentification (String login, int isConnected) {
        this.login = login;
        this.isConnected = isConnected;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
}
