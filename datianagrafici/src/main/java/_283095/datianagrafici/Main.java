package _283095.datianagrafici;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException, ParseException {

		// Leggo da file e creo un gruppo di sedi ed un gruppo di impiegati.

		List<Impiegato> listaImpiegati = new ArrayList<Impiegato>();
		List<Sede> listaSedi = new ArrayList<Sede>();
		String[] data;
		String line;
		BufferedReader csvReader;

		try {

			csvReader = new BufferedReader(new FileReader("Impiegati.csv"));

			while ((line = csvReader.readLine()) != null) {
				data = line.split(",");
				listaImpiegati.add(new Impiegato(data[0], data[1], data[2], data[3], data[4],
						new SimpleDateFormat("dd/MM/yyyy").parse(data[5]),
						new SimpleDateFormat("dd/MM/yyyy").parse(data[6])));
			}

			csvReader = new BufferedReader(new FileReader("Sedi.csv"));

			while ((line = csvReader.readLine()) != null) {
				data = line.split(",");
				listaSedi.add(new Sede(data[0], data[1]));
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
