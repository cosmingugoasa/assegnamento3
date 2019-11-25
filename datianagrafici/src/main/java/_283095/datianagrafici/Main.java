package _283095.datianagrafici;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Main
{

  public static void main(String[] args) throws IOException, ParseException
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

      csvReader = new BufferedReader(new FileReader("Impiegati.csv"));

      while ((line = csvReader.readLine()) != null)
      {
        data = line.split(",");
        switch (data[4])
        {
          case "Operaio":
            employeesList.add(new Operaio(data[0], data[1], data[2], data[3],
                new SimpleDateFormat("dd/MM/yyyy").parse(data[5]),
                new SimpleDateFormat("dd/MM/yyyy").parse(data[6]),data[7],
                data[8]));
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
        item.printDetails();
      }

    }
    catch (FileNotFoundException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

}
