package _283095.datianagrafici;

import java.io.*;
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
			Socket server = new Socket(server_host, server_port);
			DataInputStream _is = new DataInputStream(server.getInputStream());
			DataOutputStream _os = new DataOutputStream(server.getOutputStream());
			
			//os.writeBytes("Client Reaching...\n");
			_os.writeUTF("Client Reaching...\n");

			System.out.println("Client received : " + _is.readUTF());
			
			while(true) {
			  System.out.println(_is.readUTF());
			  _os.writeUTF("Hey ! I'm client " + Math.random());
			}
			
			//server.close();
		} catch (IOException e) {
		  e.printStackTrace();
		}
	}
}
