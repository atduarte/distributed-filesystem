package Peer;

import PeerProtocol.PutChunk;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by atduarte on 15-03-2014.
 */
public class MCReactor
{
    String address;
    Integer port;
    InetAddress group;

    public MCReactor(String address, Integer port) throws IOException {
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

            if(PutChunk.pattern.matcher(message).find()) {
                PutChunk thread = new PutChunk();
                thread.run();
            } else {
                System.out.println("Error on MCReactor: " + message);
            }
        }
    }
}
