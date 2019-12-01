package _283095.datianagrafici;

import java.io.*;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Client
{
  private static final int server_port = 7777;
  private static final String server_host = "localhost";
  private static Socket server = null;
  private static ObjectOutputStream oos;
  private static ObjectInputStream ois;

  Funzionario fuser = null;
  Dirigente duser = null;
  Amministratore auser = null;

  List<String> emailList = new ArrayList<String>();
  List<String> pwdList = new ArrayList<String>();

  String email;
  String pwd;

  List<Impiegato> employeesList;

  Client() throws ClassNotFoundException
  {
    System.out.println("Client starting...");
  }

  public static void main(final String[] args)
  {
    try
    {
      new Client().run();
    }
    catch (ClassNotFoundException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void run()
  {
    Connect();
    try
    {
      emailList = Arrays.asList("rr@gmail.com", "admin@gmail.com",
          "dax@gmail.com", "mario@gmail.com");
      pwdList = Arrays.asList("rr", "rr", "rr", "rr");
      Random rand = new Random();
      int index = rand.nextInt(emailList.size());
      email = String.valueOf(emailList.get(index));
      pwd = String.valueOf(pwdList.get(index));
      Login(oos, ois, email, pwd);
      if (fuser != null)
      {
        AddImpiegato(new Impiegato("Mario", "Rossi", "mariorossiioiore",
            "strada ugozzolo", "Dirigente",
            new SimpleDateFormat("dd/MM/yyyy").parse("11/11/1998"),
            new SimpleDateFormat("dd/MM/yyyy").parse("11/11/1998"),
            "marior@gmail.com", "rr"), oos, ois);

        ModifyImpiegato("poeftxzasdfghjkl",
            fuser.ModifyImpiegato("Mario", "Verdi", "poefqscvgledtkla",
                "strada ugozzolo", "Operaio", new Date(), new Date(),
                "mariov@gmail.com", "pass"),
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

      CloseConnection(oos, ois);
    }
    catch (IOException | ParseException e)
    {
      e.printStackTrace();
    }
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
      if (oos == null)
        oos = new ObjectOutputStream(server.getOutputStream());
      ois = null;
      Packet request = new Packet("Hey ! I'm client ");
      oos.writeObject(request);
      oos.flush();

      if (ois == null)
      {
        ois = new ObjectInputStream(server.getInputStream());
      }
      Object o = ois.readObject();
      if (o instanceof Packet)
      {
        Packet response = (Packet) o;
        System.out.format("Client Recive form server: -%s-\n",
            response.getAction());

        if (response.getAction() == "close")
        {
          return;
        }
      }

    }
    catch (IOException | ClassNotFoundException e)
    {
      System.out.println("Error connecting to server\n");
    }

  }

  // Funzione che invia una richiesta di login con email e pwd
  public void Login(ObjectOutputStream _oos, ObjectInputStream _ois,
      String _email, String _pwd) throws IOException
  {
    System.out.println("\nTrying to login");
    Packet _p = new Packet("Login", _email, _pwd);
    _oos.writeObject(_p);
    _oos.flush();

    // Dopo Login si identifica un set i struzioni personalizzato per l'utente
    try
    {
      Impiegato _impiegato = ((Packet) _ois.readObject()).getImpiegato();
      if (_impiegato == null)
      {
        System.out.println("ritornato oggetto null");
        return;
      }
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
      System.out.println("Login fallito");
      e.printStackTrace();
    }
  }

  public void AddImpiegato(Impiegato _impiegato, ObjectOutputStream _oos,
      ObjectInputStream _ois)
  {
    System.out.println("\nInserimento Impiegato...");
    if (_impiegato.taxCode.length() == 16)
    {
      try
      {
        Packet _p = new Packet("Add", _impiegato);
        _oos.writeObject(_p);
        _oos.flush();

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
      System.out.println("\nInserimento fallito. Codice fiscale troppo corto.");
  }

  // Funzione che invia una richiesta di modifica di un utente tramite taxCode
  public void ModifyImpiegato(String _taxCode, Impiegato _impiegato,
      ObjectOutputStream _oos, ObjectInputStream _ois)
  {
    System.out.println("\nTrying to modify Impiegato");
    if (_impiegato.taxCode.length() == 16 && _taxCode.length() == 16)
    {
      try
      {
        Packet _p = new Packet("Modify", _taxCode, _impiegato);
        _oos.writeObject(_p);
        _oos.flush();

        if (((Packet) _ois.readObject()).value)
          System.out.println("\nUtente Modificato Correttamente!!!");
        else
          System.out.println(
              "\nErrore Modifca utente dati per modifica non corretti!!!");
      }
      catch (IOException | ClassNotFoundException e)
      {
        System.out.print("Could not send add comand");
      }
    }
    else
      System.out.print("Errore in Modifica. Codice fiscale troppo corto");
  }

  public List<Impiegato> Search(String _job, ObjectOutputStream _oos,
      ObjectInputStream _ois)
  {
    System.out.println("\nTento ricerca per : " + _job);
    Packet _p = new Packet("Search", _job);
    try
    {
      _oos.writeObject(_p);
      _oos.flush();

      employeesList = new ArrayList<Impiegato>(
          ((Packet) _ois.readObject()).getSearched());
      if (!employeesList.isEmpty())
      {
        System.out
            .println("\nUtenti " + _job + " trovati: " + employeesList.size());
        PrintList(employeesList);
      }
      else
        System.out.println("Nessun Utente trovato come " + _job);
    }
    catch (IOException | ClassNotFoundException e)
    {
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

  // invio richiesta di chiusura della connessione
  public void CloseConnection(ObjectOutputStream _oos, ObjectInputStream _ois)
      throws IOException
  {
    System.out.println("Chiusura connessione");
    _oos.writeObject(new Packet("Close"));
    _oos.flush();

    try
    {
      System.out.println(((Packet) _ois.readObject()).getAction());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    server.close();
  }

}
