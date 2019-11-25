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
  public Socket socket;
  public DataInputStream inputStream;
  public DataOutputStream outputStream;

  public ConnectionHandler(Socket _s, DataInputStream _is, DataOutputStream _os)
  {
    socket = _s;
    inputStream = _is;
    outputStream = _os;
  }

  @Override
  public void run()
  {
    /* Leggo da file e creo un gruppo di sedi ed un gruppo di impiegati.
    List<Impiegato> employeesList = new ArrayList<Impiegato>(); 
    List<Sede> HqList = new ArrayList<Sede>(); // lista sedi
    String[] data;
    String line;
    BufferedReader csvReader;*/

    try
    {
      ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
      ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

      System.out.println(inputStream.readUTF());
      outputStream.writeUTF("Hi, i'm server");
      while (true)
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
              break;
            case "Add":
              oos.writeBoolean(AddUser(_p));
              break;
            case "Modify":
              oos.writeBoolean(ModUser(_p));
              break;
            case "Search":
              oos.writeObject(new Packet("response", Search(_p)));
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
      System.out.println("Could not read from stream or invalid class");
      e.printStackTrace();
    }
  }

  public Impiegato LoginResponse(String _email, String _pwd)
  {
    System.out.println("trying to login with : " + _email + _pwd);
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
      System.out.println("Error parsin date from file on Login");
    }
    return null;
  }

  public Boolean AddUser(Packet _p)
  {
    Impiegato _user = _p.getImpiegato();
    System.out.println("trying to add : " + _user.taxCode + " " + _user.job);
    try
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
    catch (IOException e)
    {
      System.out.println("Error adding user");
      return false;
    }
  }

  public Boolean ModUser(Packet _p) throws IOException
  {
    List<Impiegato> _users = new ArrayList<Impiegato>();

    try
    {
      BufferedReader csvReader = new BufferedReader(
          new FileReader("Impiegati.csv"));
      String _row;
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
      System.out.println("Could not open users in modify.");
      e.printStackTrace();
      return false;
    }

    FileWriter fo = new FileWriter("Impiegati.csv");
    for (Impiegato _user : _users)
    {
      if (!_user.taxCode.equals(_p.getTaxCode()))
      {
        try
        {

          fo.append(_user.name + "," + _user.surname + "," + _user.taxCode + ","
              + _user.hqAddress + "," + _user.job + ","
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
              + _p.getImpiegato().hqAddress + "," + _p.getImpiegato().job + ","
              + new SimpleDateFormat("dd/MM/yyyy")
                  .format(_p.getImpiegato().start)
              + ","
              + new SimpleDateFormat("dd/MM/yyyy").format(_p.getImpiegato().end)
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
      System.out.println("Could not open users in modify.");
      e.printStackTrace();
      return null;
    }
  }

}
