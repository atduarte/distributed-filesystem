package Peer;

import PeerProtocol.GetChunk;
import PeerProtocol.PutChunk;
import Utils.Channel;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by atduarte on 15-03-2014.
 */
public class MCReactor extends Thread
{
    String address;
    Integer port;
    InetAddress group;

    public MCReactor(Channel channel) throws IOException {
        this.address = channel.getAddress();
        this.port = channel.getPort();

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

            byte[] data = packet.getData();
            String message = new String(data); // Received

            if(GetChunk.pattern.matcher(message).find()) {
                GetChunk thread = new GetChunk();
                thread.start();
            } else {
                System.out.println("Error on MCReactor: " + message);
            }
        }
    }
}
