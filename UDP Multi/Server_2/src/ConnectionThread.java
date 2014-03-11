import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class ConnectionThread extends Thread {
	
	static String port;
	static String mcastaddr;
	static String mcastport;
	
	public void run()
	{

		try {
			//System.out.print(mcastaddr.);
			//byte[] b1 = new byte[100] (225,001,001,001);
			InetAddress address = InetAddress.getByName(mcastaddr);
			DatagramSocket s = new DatagramSocket();
			//s.connect(address, Integer.parseInt(mcastport));
			
			byte[] message = new byte[1024];
			
			String address1 = InetAddress.getLocalHost().getHostAddress();
			message = (address1+":"+port).getBytes();
			
			DatagramPacket mp = new DatagramPacket(message,message.length,address,Integer.parseInt(mcastport));
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
	public void setParams(String p1, String mcadd, String mcport)
	{
		
		port=p1;
		mcastaddr=mcadd;
		mcastport=mcport;
	}
}
