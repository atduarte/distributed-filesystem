package Peer;

import PeerProtocol.Delete;
import PeerProtocol.GetChunk;
import PeerProtocol.PutChunk;
import PeerProtocol.Removed;
import Utils.Channel;
import Utils.Channels;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by atduarte on 15-03-2014.
 */
public class MDBReactor extends Thread
{
    private Channels channels;
    private String address;
    private Integer port;
    private InetAddress group;

    public MDBReactor(Channels channels) throws IOException
    {
        this.channels = channels;
        this.address = channels.getMDB().getAddress();
        this.port = channels.getMDB().getPort();

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

            if(Delete.pattern.matcher(message).find()) {
                Delete thread = new Delete(data);
                thread.start();
            } else if(PutChunk.pattern.matcher(message).find()) {
                PutChunk thread = new PutChunk(channels, data);
                thread.start();
            } else if(Removed.pattern.matcher(message).find()) {
                Removed thread = new Removed(data);
                thread.start();
            } else {
                System.out.println("Error on MDBReactor: " + message);
            }
        }
    }
}
