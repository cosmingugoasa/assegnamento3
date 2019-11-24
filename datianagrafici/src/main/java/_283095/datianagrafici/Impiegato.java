package _283095.datianagrafici;

import java.io.Serializable;
import java.util.Date;

public class Impiegato implements Serializable
{

  String name;
  String surname;
  String taxCode;
  String hqAddress;
  String job;
  Date start;
  Date end;

  Impiegato()
  {
    name = "";
    surname = "";
    taxCode = "";
    hqAddress = null;
    job = "";
    start = null;
    end = null;
  }

  Impiegato(String _name, String _surname, String _taxCode, String _hqAddress,
      String _job, Date _start, Date _end)
  {
    name = _name;
    surname = _surname;
    taxCode = _taxCode;
    hqAddress = _hqAddress;
    job = _job;
    start = _start;
    end = _end;
  }

  public void printDetails()
  {
    System.out
        .println("\n Name:" + name + "\nSurname:" + surname + "\nTax code: "
            + taxCode + "\nJob: " + job + "\nhqAddres: " + hqAddress + "\njob: "
            + job + "\nStart Date : " + start + "\nEnd Date:" + end);
  }
}
