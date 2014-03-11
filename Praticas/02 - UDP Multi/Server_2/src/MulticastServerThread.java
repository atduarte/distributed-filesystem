import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class MulticastServerThread extends Thread {
	
	static String port;
	static String mcastaddr;
	static String mcastport;
	
	public void run()
	{
		while(true)
		{
			try {
				DatagramSocket s = new DatagramSocket();			
				byte[] message = new byte[1024];
				
				// Message Creation
				String serverAddress = InetAddress.getLocalHost().getHostAddress();
				message = (serverAddress + ":" + port).getBytes();
				
				// Address Creation
				InetAddress multicastAddress = InetAddress.getByName(mcastaddr);
				
				// Send Multicast Packet
				DatagramPacket mp = new DatagramPacket(message, message.length, multicastAddress, Integer.parseInt(mcastport));
				s.send(mp);
				
				Thread.sleep(10000);
				
			} catch (UnknownHostException | NumberFormatException | SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	public void setParams(String p1, String mcadd, String mcport)
	{
		
		port=p1;
		mcastaddr=mcadd;
		mcastport=mcport;
	}
}
