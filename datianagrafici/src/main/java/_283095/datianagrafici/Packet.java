package _283095.datianagrafici;

import java.io.Serializable;
import java.util.List;

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
  private String taxCode;
  private String job;
  private List<Impiegato> searched;
  Boolean value;

  // costruttore per aggiungere impiegato
  public Packet(String _action, Impiegato _Impiegato)
  {
    action = _action;
    impiegato = _Impiegato;
  }

  // costruttore per aggiungere funzionario
  public Packet(String _action, Funzionario _Funzionario)
  {
    action = _action;
    funzionario = _Funzionario;
  }

  // costruttore per aggiungere dirigente
  public Packet(String _action, Dirigente _Dirigente)
  {
    action = _action;
    dirigente = _Dirigente;
  }

  // costruttore per aggiungere amministratore
  public Packet(String _action, Amministratore _Amministratore)
  {
    action = _action;
    amministratore = _Amministratore;
  }

  // costruttore per Login
  public Packet(String _action, String _email, String _pwd)
  {
    action = _action;
    email = _email;
    pwd = _pwd;
  }

  // costruttore per modifica
  public Packet(String _action, String _taxCode, Impiegato _impiegato)
  {
    action = _action;
    taxCode = _taxCode;
    impiegato = _impiegato;
  }

  // costruttore per ritorno ricerca
  public Packet(String _action, List<Impiegato> _searched)
  {
    action = _action;
    searched = _searched;
  }

  // Costruttore per ricerca
  public Packet(String _action, String _Job)
  {
    action = _action;
    job = _Job;
  }
  
  public Packet(String _action, Boolean _value)
  {
    action = _action;
    value = _value;
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

  public String getJob()
  {
    return this.job;
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

  public String getTaxCode()
  {
    return this.taxCode;
  }

  public List<Impiegato> getSearched()
  {
    return this.searched;
  }
}
