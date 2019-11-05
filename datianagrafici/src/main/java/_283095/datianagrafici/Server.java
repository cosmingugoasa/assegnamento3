package _283095.datianagrafici;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	static final int server_port = 7777;
	
	Server(){
		System.out.println("Server starting...");
		Reply();
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Server server = new Server();
	}
	
	public void Reply() {
		System.out.println("Server trying to Reply...");
		try {
			ServerSocket server = new ServerSocket(server_port);	//crea socket
			Socket client = server.accept();	//aspetta che gli arrivino richieste dal client

			BufferedReader is = new BufferedReader(new InputStreamReader(client.getInputStream()));
			DataOutputStream os = new DataOutputStream(client.getOutputStream());

			System.out.println("Server received : " + is.readLine());
			// server reply
			os.writeBytes("Received\n");

			client.close();
			server.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
