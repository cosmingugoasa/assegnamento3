package _283095.datianagrafici;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{

  static final int server_port = 7777;

  Server() throws Exception
  {
    System.out.println("Server starting...");
    Reply();
  }

  public static void main(String[] args) throws Exception
  {
    @SuppressWarnings("unused")
    Server server = new Server();
  }

  /*
   *il server aspetta che un client si connetta, dopodichè manda un stringa "Received"
  */
  public void Reply() throws Exception
  {
    System.out.println("Waiting for something to reply at...");
    ServerSocket server = new ServerSocket(server_port); // crea socket
    while (true)
    {

      Socket client = null; // aspetta che gli arrivino richieste
                            // dal
                            // client

      try
      {
        client = server.accept();

        DataInputStream _is = new DataInputStream(client.getInputStream());
        DataOutputStream _os = new DataOutputStream(client.getOutputStream());

        System.out.println("Server received : " + _is.readUTF());
        // server replies to client
        _os.writeUTF("Received\n");

        // creo nuovo thread per il client che si è connesso
        Thread _ch = new ConnectionHandler(client, _is, _os);
        _ch.start();

      }
      catch (IOException e)
      {
        client.close();
        e.printStackTrace();
      }
    }
  }
}
