package _283095.datianagrafici;

import java.io.*;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConnectionHandler extends Thread
{
  public int id;
  public Socket socket;
  public DataInputStream inputStream;
  public DataOutputStream outputStream;
  Boolean closed = false;

  public ConnectionHandler(int _id, Socket _s, DataInputStream _is,
      DataOutputStream _os)
  {
    id = _id;
    socket = _s;
    inputStream = _is;
    outputStream = _os;
  }

  @Override
  public void run()
  {
    try
    {
      ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
      ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

      System.out.println(inputStream.readUTF());
      outputStream.writeUTF("Connected to server.");
      while (closed == false)
      {
        Packet _p = (Packet) ois.readObject();
        if (_p != null)
        {
          Thread.sleep(new Random().nextInt((4000 - 1500) + 1) + 1500);

          switch (_p.getAction())
          {
            case "Login":
              oos.writeObject(new Packet("response",
                  LoginResponse(_p.getEmail(), _p.getPwd())));
              oos.flush();
              break;
            case "Add":
              oos.writeObject(new Packet("response", AddUser(_p)));
              oos.flush();
              break;
            case "Modify":
              oos.writeObject(new Packet("response", ModUser(_p)));
              oos.flush();
              break;
            case "Search":
              oos.writeObject(new Packet("response", Search(_p)));
              oos.flush();
              break;
            case "Close":
              closed = true;
              System.out
                  .println("[" + id + "]-" + "Closing connection with client");
              oos.writeObject(new Packet("Server closing connection."));
              oos.flush();
              outputStream.close();
              inputStream.close();
              oos.close();
              ois.close();
              socket.close();
              break;
            default:
              oos.writeUTF("Invalid request");
              break;
          }
        }
      }
    }
    catch (IOException | ClassNotFoundException | InterruptedException e)
    {
      System.out.println(
          "[" + id + "]-" + "Could not read from stream or invalid class");
      e.printStackTrace();
    }
  }

  public Impiegato LoginResponse(String _email, String _pwd)
  {
    System.out
        .println("[" + id + "]-" + "trying to login with : " + _email + _pwd);
    try
    {
      BufferedReader csvReader = new BufferedReader(
          new FileReader("Impiegati.csv"));
      String row;
      while ((row = csvReader.readLine()) != null)
      {
        String[] data = row.split(",");

        if (data[7].equals(_email) && data[8].equals(_pwd))
        {
          Impiegato _logged = new Impiegato(data[0], data[1], data[2], data[3],
              data[4], new SimpleDateFormat("dd/MM/yyyy").parse(data[5]),
              new SimpleDateFormat("dd/MM/yyyy").parse(data[6]), data[7],
              data[8]);
          csvReader.close();
          return _logged;
        }
      }
      csvReader.close();
      return null;
    }
    catch (IOException e)
    {
      return null;
    }
    catch (ParseException e)
    {
      System.out
          .println("[" + id + "]-" + "Error parsin date from file on Login");
    }
    return null;
  }

  public Boolean AddUser(Packet _p)
  {
    Impiegato _user = _p.getImpiegato();
    System.out.println(
        "[" + id + "]-" + "trying to add : " + _user.taxCode + " " + _user.job);
    try
    {
      if (!CheckTaxCode(_user.taxCode)) // Controllo che il taxCode non sia già
                                        // presente
      {
        BufferedReader csvReader = new BufferedReader(
            new FileReader("Impiegati.csv"));
        String row;
        while ((row = csvReader.readLine()) != null)
        {
          String[] data = row.split(",");

          if (!_user.taxCode.equals(data[2]))
          {
            try
            {
              FileWriter fo = new FileWriter("Impiegati.csv", true);

              fo.append(_user.name + "," + _user.surname + "," + _user.taxCode
                  + "," + _user.hqAddress + "," + _user.job + ","
                  + new SimpleDateFormat("dd/MM/yyyy").format(_user.start) + ","
                  + new SimpleDateFormat("dd/MM/yyyy").format(_user.end) + ","
                  + _user.email + "," + _user.pwd + "\n");
              fo.close();

              System.out.println("[" + id + "]-" + "User added");
              csvReader.close();
              return true;
            }
            catch (IOException e)
            {
              System.out
                  .println("[" + id + "]-" + "Could not update quantity.");
              return false;
            }
          }
        }
        csvReader.close();
        return false;
      }
    }
    catch (IOException e)
    {
      System.out.println("[" + id + "]-" + "Error adding user");
      return false;
    }
    return false;
  }

  public List<Impiegato> GetList()
  {
    List<Impiegato> _users = new ArrayList<Impiegato>();

    String _row;
    try
    {
      BufferedReader csvReader = new BufferedReader(
          new FileReader("Impiegati.csv"));

      while ((_row = csvReader.readLine()) != null)
      {
        String[] _data = _row.split(",");
        Impiegato _user = new Impiegato(_data[0], _data[1], _data[2], _data[3],
            _data[4], new SimpleDateFormat("dd/MM/yyyy").parse(_data[5]),
            new SimpleDateFormat("dd/MM/yyyy").parse(_data[6]), _data[7],
            _data[8]);
        _users.add(_user);
      }
      csvReader.close();

    }
    catch (IOException | ParseException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return _users;
  }

  public Boolean CheckTaxCode(String _taxCode)
  {
    List<Impiegato> employees = new ArrayList<Impiegato>(GetList());

    for (Impiegato item : employees)
    {
      if (item.taxCode.equals(_taxCode))
      {
        System.out.println("[" + id + "]-" + "Codice Fiscale gia' esistente");
        return true;
      }
    }
    return false;
  }

  public Boolean ModUser(Packet _p) throws IOException
  {

    List<Impiegato> _users = new ArrayList<Impiegato>(GetList());

    if (!CheckTaxCode(_p.getImpiegato().taxCode)) // Controllo che il taxCode
                                                  // non sia già
    // presente
    {

      FileWriter fo = new FileWriter("Impiegati.csv");
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
                + new SimpleDateFormat("dd/MM/yyyy")
                    .format(_p.getImpiegato().end)
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
    return false;
  }

  public List<Impiegato> Search(Packet _p)
  {
    List<Impiegato> _searched = new ArrayList<Impiegato>();

    try
    {
      BufferedReader csvReader = new BufferedReader(
          new FileReader("Impiegati.csv"));
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
    catch (IOException | ParseException e)
    {
      System.out.println("[" + id + "]-" + "Could not open users in modify.");
      e.printStackTrace();
      return null;
    }
  }

}
