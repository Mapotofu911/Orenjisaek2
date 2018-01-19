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
import Client.Pair;
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

            //Si pas connecté on se connecte et on crée un auth avec les info de connexion
            if(this.auth == null){
                try {
                    Pair identifiants = (Pair) in.readObject();
                    String login = (String) identifiants.x;
                    String mdp = (String) identifiants.y;

                    //Now chercher comment check en base le couple id password
                    this.user = cm.login(login, mdp);
                    if(user.getLogin() != null){
                        //here we go il est co
                        //lui assigner ses info tout ca
                        //lui presenter un new prompt
                        //broadcast aux autres que machin est bien co
                        //broadcast la liste d'user a tout les users

                        this.auth = new Authtentification(login, 1);
                        System.out.println(this.auth);
                        auth.setIsConnected(1);

                        this.server.addAuthclients(this);
                        this.getAuth().setIsConnected(1);
                        //Le truc c'est que une fois co bah on dit pas au auth que c'est OK, donc dans clientReceive il
                        //reste en mode balais couille



                    }


                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }

            }
            //Sinon bah c'est un message lambda a traiter
//            if(this.auth != null) {
//
//            Pair message = this.auth.getMessage();
//
//                System.out.println("Message recu de "+message.x+" : "+message.y);
//
//                if (this.auth.getIsConnected() == 1) {
//                    if (this.auth.getPrivateId() != -1)
//                        this.server.privateMessage(message, this.id, this.auth.getPrivateId());
//                    else
//                        this.server.broadcastMessage(message, this.auth);
//                } else {
//                    //UsersRepository db = UsersRepository.getInstance();
//                    //db.newLogin(this.session.getLogin(), this.session.encryptedMessage());
//
//                    if(cm.login(this.auth.getLogin(), this.auth.getMessage()) != null) {
//                        auth.setIsConnected(1);
//                        auth.setMessage("Vous etes auth");
//                        this.sendMessage(auth);
//                        this.server.getClients().remove(this);
//                        this.server.getAuthClients().add(this);
//
//                        for (Iterator<ConnectedClient> i = server.getAuthClients().iterator(); i.hasNext();) {
//                            ConnectedClient cc = i.next();
//                            System.out.println("ARRAY ConnectedClient : "+cc.getAuth().getLogin());
//                        }
//                    }
//                    else {
//                        auth.setIsConnected(-1);
//                        //this.server.disconnectedClient(this);
//                    }
//                }
//            } else {
//                this.server.disconnectedClient(this);
//            }
        }
    }


    public void sendMessage (Pair msg) {
        try {
            this.out.writeObject(msg);
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
