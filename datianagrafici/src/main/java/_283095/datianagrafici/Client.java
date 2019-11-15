package _283095.datianagrafici;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
	static final int server_port = 7777;
	static final String server_host = "localhost";

	Client(){
		System.out.println("Client starting...");
		Reach();
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Client cliente = new Client();
	}
	
	/*
	 * il client tenta una connessione al server all indirizzo-porta di default
	 */
	public void Reach() {
		System.out.println("Client trying to reach...");
		try {
			Socket client = new Socket(server_host, server_port);
			BufferedReader is = new BufferedReader(new InputStreamReader(client.getInputStream()));
			DataOutputStream os = new DataOutputStream(client.getOutputStream());

			os.writeBytes("Client Reaching...\n");

			System.out.println("Client received: " + is.readLine());
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
