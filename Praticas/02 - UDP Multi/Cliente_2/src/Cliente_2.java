import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Cliente_2 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		String mcast_addr =  "228.5.6.7";
		String mcast_port = "6789";
		
		// Create Socket
		
		InetAddress group = InetAddress.getByName(mcast_addr);
		MulticastSocket s = new MulticastSocket(Integer.parseInt(mcast_port));
		s.joinGroup(group);
		
		// Receive Packet
		 
		byte[] buf = new byte[1024];
		DatagramPacket p = new DatagramPacket(buf, buf.length);
		s.receive(p);
		
		// Create a Pattern object
		Pattern r = Pattern.compile("(.*):(.*)");

		// Now create matcher object.
		Matcher m = r.matcher(new String(p.getData()));
		if (m.find()) {
			System.out.println("Found value: " + m.group(0) );
			System.out.println("Found value: " + m.group(1) );
			System.out.println("Found value: " + m.group(2) );
		} else {
			System.out.println("NO MATCH");
		}
		
		/*byte[] message = new byte[1024];

		message = "X".getBytes();
		
		InetAddress address = InetAddress.getByName(p.getData());
		DatagramPacket mp = new DatagramPacket(message, message.length, address, Integer.parseInt(args[1]));
		
		//s.connect(address, Integer.parseInt(args[1]));
		s.send(mp);
		
		s.receive(mp);			
		System.out.println("message" + new String(mp.getData()));*/
		
		
	}
}
