import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class ResponseThread extends Thread 
{
	public String port;
	
	public ResponseThread(String autoport) {
		port = autoport;
	}
	
	public void run()
	{
		byte[] message = new byte[1024];
		DatagramSocket s = null;

		// Create Socket
		
		try {
			s = new DatagramSocket(Integer.parseInt(port));
		} catch (NumberFormatException | SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		};
		
		// Init Server
		
		while(true)
		{
			// Receive Packet
			
			DatagramPacket mp = new DatagramPacket(message, message.length);
			try {
				s.receive(mp);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// Get Response Packet Info
			
			int port = mp.getPort();
			InetAddress address = mp.getAddress();
			
			// Send Response Packet
			
			DatagramPacket op = new DatagramPacket("Y".getBytes(), 1, address, port);
			try {
				s.send(op);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
