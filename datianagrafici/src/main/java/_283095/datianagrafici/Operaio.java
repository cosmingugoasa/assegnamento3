package _283095.datianagrafici;

import java.util.Date;

public class Operaio extends Impiegato
{

  private static final long serialVersionUID = 1L;

  Operaio(String _name, String _surname, String _taxCode, String _hqAddress,
      Date _start, Date _end, String _email, String _pwd)
  {
    super(_name, _surname, _taxCode, _hqAddress, "Funzionario", _start, _end,
        _email, _pwd);
  }
}
