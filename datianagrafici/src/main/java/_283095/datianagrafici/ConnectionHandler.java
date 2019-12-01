package _283095.datianagrafici;

import java.io.*;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ConnectionHandler implements Runnable
{
  private static final int sleepTime = 2000;
  private static final int TimeOffServer = 10000;
  String id;
  private Server server;
  public Socket socket;
  public DataInputStream inputStream;
  public DataOutputStream outputStream;
  ObjectInputStream ois = null;
  ObjectOutputStream oos = null;
  Boolean closed = false;
  File file = new File("Impiegati.csv");

  public ConnectionHandler(final Server s, final Socket _s)
  {
    this.server = s;
    this.socket = _s;
  }

  @Override
  public void run()
  {
    // assegno un id al thread
    id = String.valueOf(this.hashCode());

    try
    {
      if (ois == null)
        ois = new ObjectInputStream(
            new BufferedInputStream(socket.getInputStream()));

      Object objetcInput = ois.readObject();
      if (objetcInput instanceof Packet)
      {
        Packet request = (Packet) objetcInput;
        System.out.format("Serve Thread %s recives from client: %s  \n", id,
            request.getAction());
        if (oos == null)
        {
          oos = new ObjectOutputStream(
              new BufferedOutputStream(socket.getOutputStream()));
        }

        Packet response = new Packet("Connessione Riuscita");
        System.out.format("Server Thread %s sends to server: %s \n", id,
            response.getAction());
        oos.writeObject(response);
        oos.flush();

        // finche non ricevo un pacchetto di chiusura continuo a ricevere
        while (!request.getAction().equals("Close"))
        {
          request = (Packet) ois.readObject();
          if (request != null)
          {
            Thread.sleep(sleepTime); // pausa tra richieste

            // classificazione della richiesta ricevuta
            switch (request.getAction())
            {
              case "Login":
                System.out.format(
                    "Serve Thread %s recives request client: %s \n", id,
                    request.getAction());
                oos.writeObject(new Packet("response",
                    LoginResponse(request.getEmail(), request.getPwd())));
                oos.flush();
                break;
              case "Add":
                System.out.format(
                    "Serve Thread %s recives request client: %s \n", id,
                    request.getAction());
                oos.writeObject(new Packet("response", AddUser(request)));
                oos.flush();
                break;
              case "Modify":
                System.out.format(
                    "Serve Thread %s recives request client: %s \n", id,
                    request.getAction());
                oos.writeObject(new Packet("response", ModUser(request)));
                oos.flush();
                break;
              case "Search":
                System.out.format(
                    "Serve Thread %s recives request client: %s \n", id,
                    request.getAction());
                oos.writeObject(new Packet("response", Search(request)));
                oos.flush();
                break;
              case "Close":
                System.out.format(
                    "Serve Thread %s recives request client: %s \n", id,
                    request.getAction());
                closed = true;
                oos.writeObject(new Packet("Server closing connection."));
                oos.flush();
                oos.close();
                ois.close();
                /* Se dopo 10 secondi dall'ultimo client non si collega nessuno
                * allora si spegne il server
                */
                Thread.sleep(TimeOffServer);
                this.socket.close();
                if (this.server.getPool().getActiveCount() == 1)
                {
                  this.server.close();
                }
                break;

              default:
                oos.writeUTF("Invalid request");
                break;
            }
          }
        }

      }
    }
    catch (IOException | ClassNotFoundException | InterruptedException e)
    {
      System.out.println("Could not read from stream or invalid class");
      e.printStackTrace();
      return;
    }
  }

  // Accede al file e controlla le credenziali restituendo l'impiegato
  public Impiegato LoginResponse(String _email, String _pwd)
  {
    System.out.println("trying to login with : " + _email + _pwd);
    try
    {
      if (file.exists())// Controllo che il file esista
      {
        System.out.println("File trovato \n");
        // sincronizzazione del file fra thread
        synchronized (file)
        {

          BufferedReader csvReader = new BufferedReader(new FileReader(file));
          String row;
          while ((row = csvReader.readLine()) != null)
          {
            String[] data = row.split(",");

            if (data[7].equals(_email) && data[8].equals(_pwd))
            {
              Impiegato _logged = new Impiegato(data[0], data[1], data[2],
                  data[3], data[4],
                  new SimpleDateFormat("dd/MM/yyyy").parse(data[5]),
                  new SimpleDateFormat("dd/MM/yyyy").parse(data[6]), data[7],
                  data[8]);
              csvReader.close();
              return _logged;
            }
          }
          csvReader.close();
          return null;
        }
      }
      else
        System.out.println("File non trovato \n");
    }
    catch (IOException e)
    {
      return null;
    }
    catch (ParseException e)
    {
      System.out.println("Error parsin date from file on Login");
    }
    return null;
  }

  // Accede al file e aggiunge l'Impiegato
  public Boolean AddUser(Packet _p)
  {
    Impiegato _user = _p.getImpiegato();
    System.out.println("trying to add : " + _user.taxCode + " " + _user.job);
    try
    {
      // Controllo che il taxCode non sia già presente
      if (!CheckTaxCode(_user.taxCode))
      {
        synchronized (file)
        {

          BufferedReader csvReader = new BufferedReader(new FileReader(file));
          String row;
          Thread.sleep(sleepTime);
          while ((row = csvReader.readLine()) != null)
          {
            String[] data = row.split(",");

            if (!_user.taxCode.equals(data[2]))
            {
              try
              {
                // apro file in scrittura per aggiungere utente
                FileWriter fo = new FileWriter(file, true);

                fo.append(_user.name + "," + _user.surname + "," + _user.taxCode
                    + "," + _user.hqAddress + "," + _user.job + ","
                    + new SimpleDateFormat("dd/MM/yyyy").format(_user.start)
                    + "," + new SimpleDateFormat("dd/MM/yyyy").format(_user.end)
                    + "," + _user.email + "," + _user.pwd + "\n");
                fo.close();

                System.out.println("User added");
                csvReader.close();
                return true;
              }
              catch (IOException e)
              {
                System.out.println("Could not update quantity.");
                return false;
              }
            }
          }
          csvReader.close();
          return false;
        }
      }
    }
    catch (IOException | InterruptedException e)
    {
      System.out.println("Error adding user");
      return false;
    }
    return false;
  }

  // si ottiene la lista degli impiegati dal file
  public List<Impiegato> GetList()
  {
    List<Impiegato> _users = new ArrayList<Impiegato>();

    String _row;
    try
    {
      synchronized (file)
      {

        BufferedReader csvReader = new BufferedReader(new FileReader(file));

        while ((_row = csvReader.readLine()) != null)
        {
          String[] _data = _row.split(",");
          Impiegato _user = new Impiegato(_data[0], _data[1], _data[2],
              _data[3], _data[4],
              new SimpleDateFormat("dd/MM/yyyy").parse(_data[5]),
              new SimpleDateFormat("dd/MM/yyyy").parse(_data[6]), _data[7],
              _data[8]);
          _users.add(_user);
        }
        csvReader.close();
      }
    }
    catch (IOException | ParseException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return _users;
  }

  // Controlla dalla lista degli utenti se taxCode è presente
  public Boolean CheckTaxCode(String _taxCode)
  {
    List<Impiegato> employees = new ArrayList<Impiegato>(GetList());

    for (Impiegato item : employees)
    {
      if (item.taxCode.equals(_taxCode))
      {
        System.out.println("Codice Fiscale gia' esistente");
        return true;
      }
    }
    return false;
  }

  // Modifica l'utente dopo aver controllato che esista
  public Boolean ModUser(Packet _p) throws IOException
  {
    List<Impiegato> _users = new ArrayList<Impiegato>(GetList());

    // controllo che la modifica al taxCode non sia già presente
    if (!CheckTaxCode(_p.getImpiegato().taxCode))
    {
      synchronized (file)
      {

        FileWriter fo = new FileWriter(file);
        for (Impiegato _user : _users)
        {
          if (!_user.taxCode.equals(_p.getTaxCode()))
          {
            try
            {

              fo.append(_user.name + "," + _user.surname + "," + _user.taxCode
                  + "," + _user.hqAddress + "," + _user.job + ","
                  + new SimpleDateFormat("dd/MM/yyyy").format(_user.start) + ","
                  + new SimpleDateFormat("dd/MM/yyyy").format(_user.end) + ","
                  + _user.email + "," + _user.pwd + "\n");
            }
            catch (IOException e)
            {
              e.printStackTrace();
              fo.close();
              return false;
            }
          }
          else
          {
            try
            {
              // TODO Solo dove non non sono nulli cambio
              fo.append(_p.getImpiegato().name + "," + _p.getImpiegato().surname
                  + "," + _p.getImpiegato().taxCode + ","
                  + _p.getImpiegato().hqAddress + "," + _p.getImpiegato().job
                  + ","
                  + new SimpleDateFormat("dd/MM/yyyy")
                      .format(_p.getImpiegato().start)
                  + ","
                  + new SimpleDateFormat("dd/MM/yyyy").format(
                      _p.getImpiegato().end)
                  + "," + _p.getImpiegato().email + "," + _p.getImpiegato().pwd
                  + "\n");
            }
            catch (IOException e)
            {
              e.printStackTrace();
              fo.close();
              return false;
            }
          }
        }
        fo.close();
        return true;
      }
    }
    System.out.format(
        "Serve Thread %s: Errore TaxCode già esistente per la modifica \n", id);
    return false;
  }

  // Ricerca degli impiegati per il lavoro
  public List<Impiegato> Search(Packet _p)
  {
    List<Impiegato> _searched = new ArrayList<Impiegato>();

    try
    {
      synchronized (file)
      {
        BufferedReader csvReader = new BufferedReader(new FileReader(file));
        String _row;
        while ((_row = csvReader.readLine()) != null)
        {
          String[] _data = _row.split(",");
          if (_data[4].equals(_p.getJob()))
          {
            Impiegato _user = new Impiegato(_data[0], _data[1], _data[2],
                _data[3], _data[4],
                new SimpleDateFormat("dd/MM/yyyy").parse(_data[5]),
                new SimpleDateFormat("dd/MM/yyyy").parse(_data[6]), _data[7],
                _data[8]);
            _searched.add(_user);
          }
        }
        csvReader.close();

        return _searched;
      }
    }
    catch (IOException | ParseException e)
    {
      e.printStackTrace();
      return null;
    }
  }

}
