package _283095.datianagrafici;

import java.io.*;
import java.net.Socket;
import java.util.Date;
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

  String email = "rr@gmail.com";
  String pwd = "rr";

  List<Impiegato> employeesList;

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

      // TODO: tra una azione ed un altra implementare un tempo di attesa

      if (fuser != null)
      {
        AddImpiegato(new Impiegato("Martina", "g", "fefef", "fe", "Operaio",
            new Date(), new Date()), oos, ois);
        
        //TODO AGGIUNGERE QUI STAMPA DELLA RISPOSTA DAL SERVER

        ModifyImpiegato("taxcode", fuser.ModifyImpiegato("name", "_surname",
            "taxCode", "_hqAddress", "_job", new Date(), new Date()), oos, ois);

      //TODO AGGIUNGERE QUI STAMPA DELLA RISPOSTA DAL SERVER
        
        employeesList = Search("Impiegato", oos, ois);
        // TODO: METODO CHE STAMPA OGNI ELEMENTO della lista
        //TODO: AGGIUNGERE QUI CICLO DI STAMPA CON RISPOSTA DAL SERVER
      }
      else if (duser != null)
      {
        employeesList = Search(duser.search("Operaio"), oos, ois);
        employeesList = Search(duser.search("Funzionario"), oos, ois);
        // TODO: METODO CHE STAMPA OGNI ELEMENTO
      }
      else if (auser != null)
      {
        employeesList = Search(duser.search("Operaio"), oos, ois);
        employeesList = Search(duser.search("Funzionario"), oos, ois);
        employeesList = Search(duser.search("Dirigente"), oos, ois);
        employeesList = Search(duser.search("Amministratore"), oos, ois);
        // TODO: METODO CHE STAMPA OGNI ELEMENTO
      }

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
    System.out.println("\nTrying to login");
    Packet _p = new Packet(email, pwd);
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
          System.out.println("\nLogin Fallito");
      }
    }
    catch (ClassNotFoundException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  public void AddImpiegato(Impiegato _impiegato, ObjectOutputStream _oos,
      ObjectInputStream _ois)
  {
    try
    {
      System.out.println("\nTrying to add Impiegato");
      Packet _p = new Packet("Add", _impiegato);
      _oos.writeObject(_p);

      // TODO: comunicare l'esito dell'inserimento
    }
    catch (IOException e)
    {
      System.out.print("Could not send add comand");
    }
  }

  public void ModifyImpiegato(String _taxCode, Impiegato _impiegato,
      ObjectOutputStream _oos, ObjectInputStream _ois)
  {
    try
    {
      System.out.println("\nTrying to modify Impiegato");
      Packet _p = new Packet("Modify", _taxCode, _impiegato);
      _oos.writeObject(_p);

      // TODO: comunicare l'esito dell'inserimento
    }
    catch (IOException e)
    {
      System.out.print("Could not send add comand");
    }
  }

  public List<Impiegato> Search(String _job, ObjectOutputStream _oos,
      ObjectInputStream _ois)
  {
    System.out.println("\nTrying to add Impiegato");
    Packet _p = new Packet("Search", _job);
    try
    {
      _oos.writeObject(_p);

      //TODO: verificare che la lista non sia NULL e poi stamparla
      //TODO: inserire qui ciclo di stampa della risposta del server
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return null;
  }

  // fuzione che stampa ogni element della lista di impiegati
  public void PrintList(List<Impiegato> _list)
  {
    for (Impiegato item : _list)
    {
      item.printDetails();
    }
  }
}
