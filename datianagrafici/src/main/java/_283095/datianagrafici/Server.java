package _283095.datianagrafici;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server
{
  List<ConnectionHandler> clients = new ArrayList<ConnectionHandler>();
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
      //aspetto che arrivi richiesta dal c
      Socket client = null;
      DataInputStream _is = null;
      DataOutputStream _os = null;
      try
      {
        client = server.accept();
        _is = new DataInputStream(client.getInputStream());
        _os = new DataOutputStream(client.getOutputStream());
        
        System.out.println("new client : " + client);
        // creo nuovo thread per il client che si è connesso
        Thread _ch = new ConnectionHandler(clients.size(), client, _is, _os);
        clients.add((ConnectionHandler) _ch);
        _ch.start();
        System.out.println("total client : " + clients.size());

      }
      catch (IOException e)
      {
        System.out.println("closing");
        server.close();
        client.close();
        e.printStackTrace();
      }
    }
  }
}
