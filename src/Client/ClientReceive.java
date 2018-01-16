package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientReceive implements Runnable {
    private final Client client;
    private Socket sock;
    private ObjectInputStream in;

    public ClientReceive(Client client, Socket sock) {
        this.client = client;
        this.sock = sock;
    }

    @Override
    public void run() {
        boolean isActive = true;
        try {
            this.in = new ObjectInputStream(this.sock.getInputStream());
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        while (isActive) {
            String message = null;
            try {

                if (this.client.getAuthtentification().getIsConnected() != 1) {
                    synchronized (this.client) {
                        this.client.setAuthtentification((Authtentification) in.readObject());
                    }
                }
                else {
                    this.client.getAuthtentification().setMessage(((Authtentification) in.readObject()).getMessage());
                }
                message = this.client.getAuthtentification().getMessage();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();

            }

            if (this.client.getAuthtentification() != null) {
                System.out.println("\nMessage recu de " + this.client.getAuthtentification().getLogin()+ " : " + message);
            } else {
                isActive = false;
            }
        }
        client.disconnectedServer();
    }

}
