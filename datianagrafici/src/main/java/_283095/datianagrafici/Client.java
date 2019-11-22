package _283095.datianagrafici;

import java.io.*;
import java.net.Socket;
import java.util.Random;

public class Client
{
  static final int server_port = 7777;
  static final String server_host = "localhost";
  static Socket server = null;
  static DataInputStream is = null;
  static DataOutputStream os = null;

  Client()
  {
    System.out.println("Client starting...");
    Connect();
  }

  public static void main(String[] args)
  {
    @SuppressWarnings("unused")
    Client cliente = new Client();
  }

  /*
   * il client tenta una connessione al server all indirizzo-porta di default
   */
  public void Connect()
  {
    System.out.println("Client trying to reach...");
    try
    {
      server = new Socket(server_host, server_port);
      is = new DataInputStream(server.getInputStream());
      os = new DataOutputStream(server.getOutputStream());

      os.writeUTF("client reaching...\n");

      System.out.println("received : " + is.readUTF());

      System.out.println(is.readUTF());
      os.writeUTF("Hey ! I'm client " + new Random().nextInt(10));

      while (true)
      {
        System.out.println("client working...");
        System.in.read();
      }

    }
    catch (IOException e)
    {
      System.out.println("Server non raggiungibile.");
    }
  }
}
