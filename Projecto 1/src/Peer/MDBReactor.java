package Peer;

import PeerProtocol.Delete;
import PeerProtocol.GetChunk;
import PeerProtocol.PutChunk;
import PeerProtocol.Removed;
import Utils.Channel;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by atduarte on 15-03-2014.
 */
public class MDBReactor extends Thread
{
    String address;
    Integer port;
    InetAddress group;

    public MDBReactor(Channel channel) throws IOException {
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

            String message = new String(packet.getData()); // Received

            System.out.println(message);

            if(Delete.pattern.matcher(message).find()) {
                Delete thread = new Delete();
                thread.run();
            } else if(PutChunk.pattern.matcher(message).find()) {
                PutChunk thread = new PutChunk();
                thread.run();
            } else if(Removed.pattern.matcher(message).find()) {
                Removed thread = new Removed();
                thread.run();
            } else {
                System.out.println("Error on MDBReactor: " + message);
            }
        }
    }
}
