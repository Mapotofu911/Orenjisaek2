package Client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientSend implements Runnable {
    private ObjectOutputStream out;
    private Socket sock;
    private Client client;

    public ClientSend(Client client, Socket sock) {
        this.client = client;
        this.sock = sock;
    }

    @Override
    public void run() {
        try {
            Scanner sc = new Scanner(System.in);

            this.out = new ObjectOutputStream(this.sock.getOutputStream());

            while(this.client.getAuthtentification().getIsConnected() != 1) {
                Thread.yield();
                if (this.client.getAuthtentification().getIsConnected() == 0) {

                    synchronized (this.client) {
                        System.out.println("Bienvenue");
                        System.out.print("Votre login >> ");
                        String login = sc.nextLine();
                        System.out.print("Votre password >> ");
                        String password = sc.nextLine();
                        Pair identifiant = new Pair(login, password);
                        this.client.getAuthtentification().setPair((String) identifiant.x,(String)  identifiant.y);
                        out.writeObject(this.client.getAuthtentification());
                        out.flush();
//Le truc c'est que si ici on met l'isConnected a 1, bah ca sert plus a rien de se connecter vu qu'au final ce sera bon
                    }
                }
                else if (this.client.getAuthtentification().getIsConnected() == -1) {
                    System.out.println("Mauvais login / mot de passe");
                    this.client.getAuthtentification().setIsConnected(0);
                }
            }

            while (this.client.getAuthtentification().getIsConnected() == 1) {
                Thread.yield();
                try{
                    System.out.print("Votre message >> ");
                    String msg = sc.nextLine();
                    if (msg != null)
                    {
                        this.client.getAuthtentification().setMessage(msg);
                        out.writeObject(this.client.getAuthtentification());
                        out.flush();
                        out.reset();
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
