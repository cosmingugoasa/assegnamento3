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

  String email = "rr@gmail.com";
  String pwd = "rr";

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

      Login(oos, ois);
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
    catch (IOException e) // | ClassNotFoundException e)
    {
      System.out.println("Error connecting to server");
      e.printStackTrace();
    }
  }

  public void Login(ObjectOutputStream _oos, ObjectInputStream _ois)
      throws IOException
  {
    System.out.println("trying to login");
    Packet _p = new Packet("login,rr@gmail.com", "rr");
    _oos.writeObject(_p);

    try
    {
      Impiegato _impiegato = ((Packet) _ois.readObject()).getImpiegato();
      switch (_impiegato.job)
      {
        case "Funzionario":
          fuser = new Funzionario(_impiegato.name, _impiegato.surname,
              _impiegato.taxCode, _impiegato.hqAddress, _impiegato.start,
              _impiegato.end, email, pwd);
          if (fuser != null)
          {
            System.out.println("\nLogin eseguito :");
            fuser.printDetails();
          }
          break;
        case "Dirigente":
          duser = new Dirigente(_impiegato.name, _impiegato.surname,
              _impiegato.taxCode, _impiegato.hqAddress, _impiegato.start,
              _impiegato.end, email, pwd);
          if (duser != null)
          {
            System.out.println("\nLogin eseguito :");
            duser.printDetails();
          }
          break;
        case "Amministratore":
          auser = new Amministratore(_impiegato.name, _impiegato.surname,
              _impiegato.taxCode, _impiegato.hqAddress, _impiegato.start,
              _impiegato.end, email, pwd);
          if (auser != null)
          {
            System.out.println("\nLogin eseguito :");
            auser.printDetails();
          }
          break;
        default:
          System.out.println("Login Fallito");
      }
    }
    catch (ClassNotFoundException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

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
