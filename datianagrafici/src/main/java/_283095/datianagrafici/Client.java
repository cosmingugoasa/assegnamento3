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

  Funzionario fuser = null;
  Dirigente duser = null;
  Amministratore auser = null;

  Client() throws ClassNotFoundException
  {
    System.out.println("Client starting...");
    Connect();
  }

  public static void main(String[] args)
      throws ClassNotFoundException, IOException
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
      ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream());
      ObjectInputStream ois = new ObjectInputStream(server.getInputStream());

      os.writeUTF("Hey ! I'm client " + new Random().nextInt(10));
      System.out.println(is.readUTF());
      
      Login(oos,ois);
      /*Packet _p = new Packet("email", "pwd");
      oos.writeObject(_p);

      while (true)
      {
        try
        {
          System.out.println(((Packet) ois.readObject()).getAction());
        }
        catch (ClassNotFoundException e)
        {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }*/

    }
    catch (IOException e) //| ClassNotFoundException e)
    {
      System.out.println("Error connecting to server");
      e.printStackTrace();
    }
  }

  public void Login(ObjectOutputStream _oos, ObjectInputStream _ois ) throws IOException
  {
    System.out.println("trying to login");
    Packet _p = new Packet("login,rr@gmail.com","rr");
    _oos.writeObject(_p);
    
    while (true)
    {
      try
      {
        System.out.println(((Packet) _ois.readObject()).getAction());
      }
      catch (ClassNotFoundException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
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

  public void Add()
  {
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
