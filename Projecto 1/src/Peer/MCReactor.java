package Peer;

import PeerProtocol.GetChunk;
import PeerProtocol.PutChunk;
import PeerProtocol.Removed;
import Server.BackupInfo;
import ServerProtocol.Stored;
import Utils.Channel;
import Utils.Channels;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * Created by atduarte on 15-03-2014.
 */
public class MCReactor extends Thread
{
    private BackupInfo backupInfo;
    private Channels channels;
    String address;
    Integer port;
    InetAddress group;

    public MCReactor(Channels channels, BackupInfo backupInfo) throws IOException {
        this.channels = channels;
        this.address = channels.getMC().getAddress();
        this.port = channels.getMC().getPort();
        this.backupInfo = backupInfo;

        group = InetAddress.getByName(address);
    }

    public void run()
    {
        // Create Socket

        MulticastSocket socket = null;

        try {
            socket = new MulticastSocket(port);
            socket.joinGroup(group);
            socket.setLoopbackMode(false);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while (true) {

            // Receive Packet

            byte[] buf = new byte[2048];
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

            System.out.println("MC Received:" + message + ";");

            if(GetChunk.pattern.matcher(message).find()) {
                GetChunk thread = new GetChunk(data);
                thread.start();
            } else if(Stored.pattern.matcher(message).find()) {
                Stored thread = new Stored(data, backupInfo);
                thread.start();
            } else if(Removed.pattern.matcher(message).find()) {
                Removed thread = new Removed(data);
                thread.start();
            } else {
                System.out.println("Error on MCReactor: " + message);
            }
        }
    }
}
