package Server;

import java.io.IOException;
import java.util.*;

import Client.Authtentification;

public class Server {
    private int port;
    //private List<ConnectedClient> clients = new ArrayList<>();
    private List<ConnectedClient> authClients = new ArrayList<>();
    private Authtentification auth;

    public Server (int port) throws IOException {
        this.port = port;
        Connection con = new Connection(this);
        Thread t = new Thread(con);
        t.start();
    }

    public void addClient (ConnectedClient client) {
        this.broadcastMessage("addClient function");

		/*String msg = "Le client "+client.getSession().getLogin()+" vient de se connecter !";
		this.broadcastMessage(msg, -1);*/
//        this.clients.add(client);
    }

    public void broadcastMessage (String message) { //virage du parametre auth, parce que le serveur n'a pas de auth
        for (ConnectedClient client : this.authClients) {
            if (this.auth != client.getSession()) {
                this.auth.setMessage(message);
                client.sendMessage(this.auth.getMessage());
            }
        }
    }

//    public void privateMessage (String msg, int idSend, int idRec) {
//        ConnectedClient send = null, rec = null;
//
//        for(ConnectedClient client : this.authClients)  {
//            if(client.getId() == idSend) {
//                send = client;
//            }
//            else if (client.getId() == idRec) {
//                rec = client;
//            }
//        }
//
//        if (rec == null && send != null) {
//            //send.sendMessage("Impossible de trouver le destinataire");
//        }
//        else if (rec != null && send != null) {
//            msg = "Message privé de "+send.getId()+" : "+msg;
//            rec.getSession().setMessage(msg);
//            rec.sendMessage(rec.getSession());
//        }
//    }

    public void disconnectedClient (ConnectedClient client) {
        this.authClients.remove(client);
		/*String msg = "Le client "+client.getId()+" vient de se d�connecter !";
		this.broadcastMessage(msg, this.session);*/
    }

    public int getPort () {
        return this.port;
    }

    public void setSession(Authtentification auth) {
        this.auth = auth;
    }

//    public List<ConnectedClient> getClients() {
//        return clients;
//    }

//    public void setClients(List<ConnectedClient> clients) {
//        this.clients = clients;
//    }

    public List<ConnectedClient> getAuthClients() {
        return authClients;
    }

    public void setAuthClients(List<ConnectedClient> authClients) {
        this.authClients = authClients;
    }

    public Authtentification getAuth() {
        return auth;
    }

    public void addAuthclients(ConnectedClient cl){
        this.authClients.add(cl);
    }
}
