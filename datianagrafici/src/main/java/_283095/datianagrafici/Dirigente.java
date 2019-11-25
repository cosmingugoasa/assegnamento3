package _283095.datianagrafici;

import java.util.Date;

public class Dirigente extends Impiegato
{

  Dirigente(String _name, String _surname, String _taxCode, String _hqAddress,
      Date _start, Date _end, String _email, String _pwd)
  {
    super(_name, _surname, _taxCode, _hqAddress, "Funzionario", _start, _end,
        _email, _pwd);
  }

  public String search(String _job)
  {
    return _job;
  }

}
