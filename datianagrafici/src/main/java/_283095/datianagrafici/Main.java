package _283095.datianagrafici;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException, ParseException {
		// TODO Auto-generated method stub

		List<Impiegato> listaImpiegati = new ArrayList<Impiegato>();
		List<Sede> listaSedi = new ArrayList<Sede>();
		String[] data;
		String line;
		BufferedReader csvReader;
		
		try {
			
			csvReader = new BufferedReader(new FileReader("Impiegati.csv"));

			while ((line = csvReader.readLine()) != null) {
				data = line.split(",");
				Impiegato myImpiegato = new Impiegato(data[0],data[1],data[2],data[3],data[4],new SimpleDateFormat("dd/MM/yyyy").parse(data[5]),new SimpleDateFormat("dd/MM/yyyy").parse(data[6]));
				listaImpiegati.add(myImpiegato);
			}
			
			csvReader = new BufferedReader(new FileReader("Sedi.csv"));
			
			while ((line = csvReader.readLine()) != null)
				System.out.println(line);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
