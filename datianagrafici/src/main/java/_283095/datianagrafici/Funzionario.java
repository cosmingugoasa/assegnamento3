package _283095.datianagrafici;

import java.util.Date;

public class Funzionario extends Impiegato
{

  String email;
  String psw;

  Funzionario(String _name, String _surname, String _taxCode, String _hqAddress,
      Date _start, Date _end, String _email, String _psw)
  {
    super(_name, _surname, _taxCode, _hqAddress, "Funzionario", _start, _end);
    email = _email;
    psw = _psw;
  }

  public Impiegato addImpiegato(String _name, String _surname, String _taxCode,
      String _hqAddress, String _job, Date _start, Date _end)
  {
    return new Impiegato(_name, _surname, _taxCode, _hqAddress, _job, _start,
        _end);
  }

  public Impiegato ModifyImpiegato(String _name, String _surname,
      String _taxCode, String _hqAddress, String _job, Date _start, Date _end)
  {

    return new Impiegato(_name, _surname, _taxCode, _hqAddress, _job, _start,
        _end);
  }
}
