package Server;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Iterator;

import Client.Authtentification;
import Bdd.ConnexionManager;
import Model.User;

public class ConnectedClient implements Runnable {

    private Server server;
    private Socket sock;
    private int id;
    public static int counterId = 0;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Authtentification auth;
    private ConnexionManager cm;
    private User user;

    public ConnectedClient (Server server, Socket sock) {
        this.id = ConnectedClient.counterId++;
        this.server = server;
        this.sock = sock;
        this.cm = new ConnexionManager();
        this.user = new User();

        System.out.println("Nouvelle connexion, id = "+this.id+", ip = "+this.sock.getInetAddress().toString());
    }

    @Override
    public void run(){

        try {
            this.in = new ObjectInputStream(this.sock.getInputStream());
            this.out = new ObjectOutputStream(this.sock.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }


        while(this.sock.isConnected()) {

            try {
                Authtentification auth1 = (Authtentification) in.readObject();
                this.auth = auth1;
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }

            System.out.println("Session de "+this.auth.getLogin());

            if(this.auth.getMessage() != null) {

                String message = this.auth.getMessage();

                System.out.println("Message recu de "+this.auth.getLogin()+" : "+message);

                if (this.auth.getIsConnected() == 1) {
                    if (this.auth.getPrivateId() != -1)
                        this.server.privateMessage(message, this.id, this.auth.getPrivateId());
                    else
                        this.server.broadcastMessage(message, this.auth);
                } else {
                    //UsersRepository db = UsersRepository.getInstance();
                    //db.newLogin(this.session.getLogin(), this.session.encryptedMessage());

                    if(cm.login(this.auth.getLogin(), this.auth.getMessage()) != null) {
                        auth.setIsConnected(1);
                        auth.setMessage("Vous etes auth");
                        this.sendMessage(auth);
                        this.server.getClients().remove(this);
                        this.server.getAuthClients().add(this);

                        for (Iterator<ConnectedClient> i = server.getAuthClients().iterator(); i.hasNext();) {
                            ConnectedClient cc = i.next();
                            System.out.println("ARRAY ConnectedClient : "+cc.getAuth().getLogin());
                        }
                    }
                    else {
                        auth.setIsConnected(-1);
                        //this.server.disconnectedClient(this);
                    }
                }
            } else {
                this.server.disconnectedClient(this);
            }
        }
    }


    public void sendMessage (Authtentification auth) {
        try {
            this.out.writeObject(auth);
            this.out.flush();
            this.out.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeClient () throws IOException {
        this.in.close();
        this.out.close();
        this.sock.close();
    }

    public int getId() {
        return this.id;
    }

    public Authtentification getSession() {
        return auth;
    }

    public void setSession(Authtentification session) {
        this.auth = session;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Authtentification getAuth() {
        return auth;
    }

    public void setAuth(Authtentification auth) {
        this.auth = auth;
    }
}
