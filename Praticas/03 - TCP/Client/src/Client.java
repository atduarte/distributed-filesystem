import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;


public class Client {

	public static void main(String[] args) throws IOException 
	{		    
		// Accept Socket
		Socket connectionSocket = new Socket("localhost", 7584);		
		System.out.println("Accepted");
		
		// Open Stream		
		DataOutputStream outToServer = new DataOutputStream(connectionSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		
		// Each Line
		while (true) {

			// Send
			String capitalizedSentence = "mensagem" + '\n';
			outToServer.writeBytes(capitalizedSentence);
			System.out.println("Sent: " + capitalizedSentence);
			
			// Receive Line
			String clientSentence = inFromServer.readLine();
			System.out.println("Received: " + clientSentence);
			
		}
		
		//connectionSocket.close();

	}

}
