package _283095.datianagrafici;

import java.util.Date;

public class Impiegato {
	
	String name;
	String surname;
	String taxCode;
	String HqAddress;
	String job;
	Date start;
	Date end;
	
	Impiegato(){
		name = "";
		surname = "";
		taxCode ="";
		HqAddress = null;
		job = "";
		start = null;
		end = null;
	}
	
	Impiegato(String _nome, String _cognome, String _codiceFiscale, String _indirizzoSede, String _mansione, Date _inizio,  Date _fine){
		name = _nome;
		surname = _cognome;
		taxCode = _codiceFiscale;
		HqAddress = _indirizzoSede;
		job = _mansione;
		start = _inizio;
		end = _fine;
	}
}
