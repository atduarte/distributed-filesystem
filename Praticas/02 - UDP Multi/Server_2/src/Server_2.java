import java.net.DatagramSocket;


public class Server_2 {

	public static void main(String[] args) {
		
		
		String srvc_port = "5487";
		String mcast_addr =  "228.5.6.7";
		String mcast_port = "6789";
		
		MulticastServerThread t1 = new MulticastServerThread();
		t1.setParams(srvc_port, mcast_addr, mcast_port);
		t1.run();
		
		ResponseThread t2 = new ResponseThread(srvc_port);
		t2.run();
		
	
	}

}

