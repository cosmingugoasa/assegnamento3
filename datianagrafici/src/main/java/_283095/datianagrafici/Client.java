package _283095.datianagrafici;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Client
{
  static final int server_port = 7777;
  static final String server_host = "localhost";
  static Socket server = null;
  static DataInputStream is = null;
  static DataOutputStream os = null;
  
  Funzionario fuser = null;
  Dirigente duser = null;
  Amministratore auser = null;

  Client() throws ClassNotFoundException
  {
    System.out.println("Client starting...");
    Connect();
  }

  public static void main(String[] args) throws ClassNotFoundException, IOException
  {
    @SuppressWarnings("unused")
    Client client = new Client();
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
      
      System.out.println("received : " + is.readUTF());

      System.out.println(is.readUTF());
      os.writeUTF("Hey ! I'm client " + new Random().nextInt(10));
      
    }
    catch (IOException e)
    {
      System.out.println("Error connecting to server");
      e.printStackTrace();
    }
    
    
    /*try
    {
      server = new Socket(server_host, server_port);
      is = new ObjectInputStream(server.getInputStream());
      os = new ObjectOutputStream(server.getOutputStream());

      os.writeObject("client reaching...\n");

      System.out.println("received : " + is.readObject());

      System.out.println(is.readUTF());
      os.writeObject("Hey ! I'm client " + new Random().nextInt(10));

      while (true)
      {
        System.out.println("client working...");
        System.in.read();
      }
      
    }
    catch (IOException e)
    {
      System.out.println("Server non raggiungibile.");
    }*/
  }

  public void Login() throws IOException {
    System.out.println("trying to login");    
    
    /*os.writeUTF("login,rr@gmail.com,rr");
    ObjectInputStream _ois = new ObjectInputStream(is);
    switch (is.readUTF()) {
      case "Funzionario":
        fuser = (Funzionario)_ois.readObject();
        if(fuser != null)
          System.out.println("Login eseguito");
        break;
      case "Dirigente":
        duser = (Dirigente)_ois.readObject();
        if(duser != null)
          System.out.println("Login eseguito");
        break;
      case "Admin":
        auser = (Amministratore)_ois.readObject();
        if(auser != null)
          System.out.println("Login eseguito");
        break;
      default:
        System.out.println("Login Fallito");
    }*/
  }
  
  public void Add() {
    try
    {
      os.writeUTF("add");
      System.out.println(is.readUTF());
      
      
    }
    catch (IOException e)
    {
      System.out.print("Could not send add comand");
    }
  }
  
}
