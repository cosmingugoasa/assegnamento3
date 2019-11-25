package _283095.datianagrafici;

import java.io.*;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        AddImpiegato(new Impiegato("Mario", "g", "prova3", "fe", "Dirigente",
            new SimpleDateFormat("dd/MM/yyyy").parse("11/11/1998"),
            new SimpleDateFormat("dd/MM/yyyy").parse("11/11/1998"),
            "mario@gmail.com", "rr"), oos, ois);

        ModifyImpiegato("prova",
            fuser.ModifyImpiegato("name", "_surname", "taxCode2", "_hqAddress",
                "Operaio", new Date(), new Date(), "email@gmail.com", "pass"),
            oos, ois);

        employeesList = Search("Operaio", oos, ois);

      }
      else if (duser != null)
      {
        employeesList = Search(duser.search("Operaio"), oos, ois);
        employeesList = Search(duser.search("Funzionario"), oos, ois);
        // TODO: METODO CHE STAMPA OGNI ELEMENTO
      }
      else if (auser != null)
      {
        employeesList = Search(auser.search("Operaio"), oos, ois);
        employeesList = Search(auser.search("Funzionario"), oos, ois);
        employeesList = Search(auser.search("Dirigente"), oos, ois);
        employeesList = Search(auser.search("Amministratore"), oos, ois);
      }

    }
    catch (IOException | ParseException e) // | ClassNotFoundException e)
    {
      System.out.println("Error connecting to server");
      e.printStackTrace();
    }

  }

  public void Login(ObjectOutputStream _oos, ObjectInputStream _ois)
      throws IOException
  {
    System.out.println("\nTrying to login");
    Packet _p = new Packet("Login", email, pwd);
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
    if (_impiegato.taxCode.length() == 16)
    {
      try
      {
        System.out.println("\nTrying to add Impiegato");
        Packet _p = new Packet("Add", _impiegato);
        _oos.writeObject(_p);

        if (((Packet) _ois.readObject()).value)
          System.out.println("\nUtente Inserito Correttamente!!!");
        else
          System.out.println("\nErrore inserimento utente!!!");
      }
      catch (IOException | ClassNotFoundException e)
      {
        System.out.print(e);
      }
    }
    else
      System.out.println("\nErrore inserimento Codice Fiscale!!!");
  }

  public void ModifyImpiegato(String _taxCode, Impiegato _impiegato,
      ObjectOutputStream _oos, ObjectInputStream _ois)
  {
    if (_impiegato.taxCode.length() == 16)
    {
      try
      {
        System.out.println("\nTrying to modify Impiegato");
        Packet _p = new Packet("Modify", _taxCode, _impiegato);
        _oos.writeObject(_p);

        if (((Packet) _ois.readObject()).value)
          System.out.println("\nUtente Modificato Correttamente!!!");
        else
          System.out.println("\nErrore Modifca utente!!!");
      }
      catch (IOException | ClassNotFoundException e)
      {
        System.out.print("Could not send add comand");
      }
    }
    else
      System.out.print("Errore inserimento Codice Fiscale");
  }

  public List<Impiegato> Search(String _job, ObjectOutputStream _oos,
      ObjectInputStream _ois)
  {
    System.out.println("\nTrying to Search by " + _job);
    Packet _p = new Packet("Search", _job);
    try
    {
      _oos.writeObject(_p);

      employeesList = new ArrayList<Impiegato>(
          ((Packet) _ois.readObject()).getSearched());
      if (!employeesList.isEmpty())
      {
        System.out.println("\nUtenti " + _job + " trovati: ");
        PrintList(employeesList);
      }
      else
        System.out.println("Nessun Utente trovato come " + _job);
    }
    catch (IOException | ClassNotFoundException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return null;
  }

  // fuzione che stampa ogni elemento della lista di impiegati
  public void PrintList(List<Impiegato> _list)
  {
    for (Impiegato item : _list)
    {
      item.printDetails();
    }
  }
}
