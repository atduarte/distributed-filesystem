import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class Client {

	public static void main(String[] args) throws IOException {
		
		DatagramSocket s = new DatagramSocket();
		byte[] message = new byte[1024];
		
		while(true)
		{
			message = "X".getBytes();
			InetAddress address = InetAddress.getByName(args[0]);
			DatagramPacket mp = new DatagramPacket(message, message.length, address, Integer.parseInt(args[1]));
			
			//s.connect(address, Integer.parseInt(args[1]));
			s.send(mp);
			
			s.receive(mp);			
			System.out.println("message" + new String(mp.getData()));			
			
		}


	}

}
