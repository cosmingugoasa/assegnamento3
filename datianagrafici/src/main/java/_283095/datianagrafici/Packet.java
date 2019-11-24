package _283095.datianagrafici;

import java.io.Serializable;

@SuppressWarnings("serial")
class Packet implements Serializable{

  public String action;
  public int n;
  
  public Packet(String _a, int _n) {
    action = _a;
    n = _n;
  }
  
 }
