package _283095.datianagrafici;

import java.io.Serializable;

@SuppressWarnings("serial")
class Packet implements Serializable
{
  private String action;
  private Impiegato impiegato;
  private Funzionario funzionario;
  private Dirigente dirigente;
  private Amministratore amministratore;
  private String email;
  private String pwd;

  public Packet(String _action, Impiegato _Impiegato)
  {
    action = _action;
    impiegato = _Impiegato;
  }

  public Packet(String _action, Funzionario _Funzionario)
  {
    action = _action;
    funzionario = _Funzionario;
  }

  public Packet(String _action, Dirigente _Dirigente)
  {
    action = _action;
    dirigente = _Dirigente;
  }

  public Packet(String _action, Amministratore _Amministratore)
  {
    action = _action;
    amministratore = _Amministratore;
  }

  public Packet(String _email, String _pwd)
  {
    action = "Login";
    email = _email;
    pwd = _pwd;
  }

  public String getAction()
  {
    return this.action;
  }

  public String getEmail()
  {
    return this.email;
  }

  public String getPwd()
  {
    return this.pwd;
  }

  public Impiegato getImpiegato()
  {
    return this.impiegato;
  }

  public Funzionario getFunzionario()
  {
    return this.funzionario;
  }

  public Dirigente getDirigente()
  {
    return this.dirigente;
  }

  public Amministratore getAmministartore()
  {
    return this.amministratore;
  }

}
