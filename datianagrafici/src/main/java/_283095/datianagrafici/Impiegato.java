package _283095.datianagrafici;

import java.util.Date;

public class Impiegato {
	
	String nome;
	String cognome;
	String codiceFiscale;
	String indirizzoSede;
	String mansione;
	Date inizio;
	Date fine;
	
	Impiegato(){
		nome = "";
		cognome = "";
		codiceFiscale ="";
		indirizzoSede = null;
		mansione = "";
		inizio = null;
		fine = null;
	}
	
	Impiegato(String _nome, String _cognome, String _codiceFiscale, String _indirizzoSede, String _mansione, Date _inizio,  Date _fine){
		nome = _nome;
		cognome = _cognome;
		codiceFiscale = _codiceFiscale;
		indirizzoSede = _indirizzoSede;
		mansione = _mansione;
		inizio = _inizio;
		fine = _fine;
	}
}
