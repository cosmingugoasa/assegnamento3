package _283095.datianagrafici;

import java.io.*;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
    // Leggo da file e creo un gruppo di sedi ed un gruppo di impiegati.
    List<Impiegato> employeesList = new ArrayList<Impiegato>(); // lista
                                                                // impiegati
    List<Sede> HqList = new ArrayList<Sede>(); // lista sedi
    String[] data;
    String line;
    BufferedReader csvReader;
    
    System.out.println("client on " + socket + " has own thread");
    
    try
    {
      
      System.out.println(inputStream.readUTF());
    }
    catch (IOException e)
    {
      System.out.println("Could not read from stream");
      e.printStackTrace();
    }
    
  }

  public Impiegato Login(String _email, String _pwd)
  {
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
          System.out.println("Login Effettuato correttamente");
          csvReader.close();
          return new Impiegato(data[0], data[1], data[2], data[3], data[4],
              new SimpleDateFormat("dd/MM/yyyy").parse(data[5]),
              new SimpleDateFormat("dd/MM/yyyy").parse(data[6]));
        }
      }

      csvReader.close();
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

}
