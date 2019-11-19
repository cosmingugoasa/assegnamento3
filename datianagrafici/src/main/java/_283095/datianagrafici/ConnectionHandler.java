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

    try
    {
      System.out.println("client " + socket + " has own thread");

      csvReader = new BufferedReader(new FileReader("Impiegati.csv"));

      while ((line = csvReader.readLine()) != null)
      {
        data = line.split(",");
        switch (data[4])
        {
          case "Operaio":
            employeesList.add(new Operaio(data[0], data[1], data[2], data[3],
                new SimpleDateFormat("dd/MM/yyyy").parse(data[5]),
                new SimpleDateFormat("dd/MM/yyyy").parse(data[6])));
            break;
          case "Funzionario":
            employeesList.add(new Funzionario(data[0], data[1], data[2],
                data[3], new SimpleDateFormat("dd/MM/yyyy").parse(data[5]),
                new SimpleDateFormat("dd/MM/yyyy").parse(data[6]), data[7],
                data[8]));
            break;
          case "Dirigente":
            employeesList.add(new Dirigente(data[0], data[1], data[2], data[3],
                new SimpleDateFormat("dd/MM/yyyy").parse(data[5]),
                new SimpleDateFormat("dd/MM/yyyy").parse(data[6]), data[7],
                data[8]));
            break;
          case "Amministratore":
            employeesList.add(new Amministratore(data[0], data[1], data[2],
                data[3], new SimpleDateFormat("dd/MM/yyyy").parse(data[5]),
                new SimpleDateFormat("dd/MM/yyyy").parse(data[6]), data[7],
                data[8]));
            break;
        }
      }

      csvReader = new BufferedReader(new FileReader("Sedi.csv"));

      while ((line = csvReader.readLine()) != null)
      {
        data = line.split(",");
        HqList.add(new Sede(data[0], data[1]));
      }

      for (Impiegato item : employeesList)
      {
        // item.printDetails();
        outputStream.writeUTF(
            "Tax code:" + item.taxCode + "\nJob:" + item.job + "\n\n");
      }

      
      System.out.println(inputStream.readUTF());
      
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }
  }
}
