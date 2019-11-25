package _283095.datianagrafici;

import java.util.Date;

public class Funzionario extends Impiegato
{

  Funzionario(String _name, String _surname, String _taxCode, String _hqAddress,
      Date _start, Date _end, String _email, String _pwd)
  {
    super(_name, _surname, _taxCode, _hqAddress, "Funzionario", _start, _end,
        _email, _pwd);
  }

  public Impiegato addImpiegato(String _name, String _surname, String _taxCode,
      String _hqAddress, String _job, Date _start, Date _end, String _email,
      String _pwd)
  {
    return new Impiegato(_name, _surname, _taxCode, _hqAddress, _job, _start,
        _end, _email, _pwd);
  }

  public Impiegato ModifyImpiegato(String _name, String _surname,
      String _taxCode, String _hqAddress, String _job, Date _start, Date _end,
      String _email, String _pwd)
  {

    return new Impiegato(_name, _surname, _taxCode, _hqAddress, _job, _start,
        _end, _email, _pwd);
  }
}
