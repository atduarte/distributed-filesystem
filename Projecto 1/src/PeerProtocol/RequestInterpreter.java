package PeerProtocol;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class RequestInterpreter extends Thread
{
    String address;
    Integer port;
    InetAddress group;

    public RequestInterpreter(String address, Integer port) throws IOException {
        this.address = address;
        this.port = port;

        group = InetAddress.getByName(address);
    }

    public void run()
    {
        // Create Socket

        MulticastSocket socket = null;

        try {
            socket = new MulticastSocket(port);
            socket.joinGroup(group);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while (true) {

            // Receive Packet

            byte[] buf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);

            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

            // Process Packet

            String message = new String(packet.getData()); // Received

            if(Delete.pattern.matcher(message).find()) {
                Delete thread = new Delete();
                thread.run();
            } else if(GetChunk.pattern.matcher(message).find()) {
                GetChunk thread = new GetChunk();
                thread.run();
            } else if(PutChunk.pattern.matcher(message).find()) {
                PutChunk thread = new PutChunk();
                thread.run();
            } else if(Removed.pattern.matcher(message).find()) {
                Removed thread = new Removed();
                thread.run();
            } else {
                System.out.println("Error: " + message);
            }
       }
    }
}
