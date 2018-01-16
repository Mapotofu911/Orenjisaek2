package Client;

import java.net.*;
import java.io.*;

public class Client {
    private String address;
    private int port;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Authtentification auth;

    public Client(String address, int port){
        this.address = address;
        this.port = port;
        this.auth = new Authtentification(null, 0);

        try {
            this.socket = new Socket(address, port);

            Thread rec = new Thread(new ClientReceive(this, this.socket));
            Thread send = new Thread(new ClientSend(this, this.socket));

            rec.start();
            send.start();
        } catch(IOException e)  {
            e.printStackTrace();
        }
    }

    public void disconnectedServer(){
        try {
            this.in.close();
            this.out.close();
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);

    }

    public Authtentification getAuthtentification() {
        return this.auth;
    }

    public void setAuthtentification (Authtentification session) {
        this.auth = session;
    }

}
