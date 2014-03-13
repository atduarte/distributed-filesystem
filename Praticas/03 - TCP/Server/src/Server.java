import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws IOException {
			
		// Open Socket
		ServerSocket srvSocket = new ServerSocket(7584);
		           
		// Accept Socket
		Socket connectionSocket = srvSocket.accept();		
		System.out.println("Accepted");
		
		// Open Stream
		BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
		
		// Each Line
		while (!inFromClient.ready()) {
			// Receive Line
			String clientSentence = inFromClient.readLine();
			System.out.println("Received: " + clientSentence);
			
			// Send
			String capitalizedSentence = clientSentence.toUpperCase() + '\n';
			outToClient.writeBytes(capitalizedSentence);
			System.out.println("Sent: " + capitalizedSentence);
		}
		
		connectionSocket.close();
	}

}
