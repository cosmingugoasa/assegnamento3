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

  public Impiegato InsertDipendente(String _name, String _surname,
      String _taxCode, String _hqAddress, String _job, Date _start, Date _end)
  {
    return new Impiegato(_name, _surname, _taxCode, _hqAddress, _job, _start,
        _end);
  }

  public Impiegato ModifyDipendente(Impiegato _myImpiegato, String _name,
      String _surname, String _taxCode, String _hqAddress, String _job,
      Date _start, Date _end)
  {
    if (_name != "")
      _myImpiegato.name = _name;

    if (_surname != "")
      _myImpiegato.surname = _surname;

    if (_taxCode != "")
      _myImpiegato.taxCode = _taxCode;

    if (_hqAddress != "")
      _myImpiegato.hqAddress = _hqAddress;

    if (_job != "")
      _myImpiegato.job = _job;

    if (_start != _myImpiegato.start)
      _myImpiegato.start = _start;

    if (_start != _myImpiegato.end)
      _myImpiegato.end = _end;

    return _myImpiegato;
  }
}
