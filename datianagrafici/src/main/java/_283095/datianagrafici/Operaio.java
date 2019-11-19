package _283095.datianagrafici;

import java.util.Date;

public class Operaio extends Impiegato
{
  Operaio(String _name, String _surname, String _taxCode, String _hqAddress,
      Date _start, Date _end)
  {
    super(_name, _surname, _taxCode, _hqAddress, "Operaio", _start, _end);
  }
}
