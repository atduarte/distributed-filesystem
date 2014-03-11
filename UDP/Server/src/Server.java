import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class Server {

	public static void main(String[] args) throws IOException {
			
		DatagramSocket s = new DatagramSocket(Integer.parseInt(args[0]));
		byte[] message = new byte[1024];
		
		while(true)
		{
			DatagramPacket mp = new DatagramPacket(message, message.length);
			s.receive(mp);
			System.out.println("message: " + new String(mp.getData()));
			
			int port = mp.getPort();
			InetAddress address = mp.getAddress();
			
			DatagramPacket op = new DatagramPacket("Y".getBytes(), 1, address, port);
			//s.connect(address, port);
			s.send(op);
			
			
		}

	}

}
