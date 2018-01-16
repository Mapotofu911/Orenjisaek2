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
                        System.out.print("Votre login >> ");
                        this.client.getAuthtentification().setLogin(sc.nextLine());

                        System.out.print("Votre password >> ");
                        String msg = sc.nextLine();

                        this.client.getAuthtentification().setMessage(msg);
                        this.client.getAuthtentification().setMessage(this.client.getAuthtentification().getMessage());
                        out.writeObject(this.client.getAuthtentification());
                        out.flush();

                        this.client.getAuthtentification().setIsConnected(-2);
                    }
                }
                else if (this.client.getAuthtentification().getIsConnected() == -1) {
                    System.out.println("Mauvais login / mot de passe");
                    this.client.getAuthtentification().setIsConnected(0);
                }
            }

            while (true) {
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
