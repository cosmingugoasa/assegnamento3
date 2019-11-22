package _283095.datianagrafici;

import java.util.Date;

public class Dirigente extends Impiegato
{

  String email;
  String psw;

  Dirigente(String _name, String _surname, String _taxCode, String _hqAddress,
      Date _start, Date _end, String _email, String _psw)
  {
    super(_name, _surname, _taxCode, _hqAddress, "Dirigente", _start, _end);
    email = _email;
    psw = _psw;
  }

  public String search(String _job)
  { 
    return "Search," + _job;
  }

}
