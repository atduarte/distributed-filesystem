package Peer;

import PeerProtocol.GetChunk;
import PeerProtocol.PutChunk;
import PeerProtocol.Removed;
import Server.BackupInfo;
import ServerProtocol.Stored;
import Utils.Channel;
import Utils.Channels;
import Utils.Constants;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public abstract class ChannelReactor extends Thread
{
	protected BackupInfo backupInfo;
    protected Channels channels;
    protected String address;
    protected Integer port;
    protected InetAddress group;

    public ChannelReactor(Channels channels, BackupInfo backupInfo) throws IOException {
        this.channels = channels;
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
            socket.setLoopbackMode(true);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while (true) {

            // Receive Packet

            byte[] buf = new byte[Constants.chunkSize + 2048];
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
            
            processMessage(data, message);
        }
    }

	protected abstract void processMessage(byte[] data, String message);
    
}
