package Server.Protocol.Normal;

import Peer.DependencyInjection;
import Peer.Injectable;
import Utils.Channels;
import Utils.Constants;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by atduarte on 14-03-2014.
 */
public class Removed extends Injectable
{
    String fileId;
    Integer chunkNo;

    public Removed(DependencyInjection di, String fileId, Integer chunkNo) {
        super(di);
        this.fileId = fileId;
        this.chunkNo = chunkNo;
    }

    private byte[] createMessage() {
        String sMessage = "REMOVED ";
        sMessage += Constants.version + " ";
        sMessage += fileId + " ";
        sMessage += chunkNo;
        sMessage += Constants.separator;

        return sMessage.getBytes();
    }

    public boolean run() throws IOException {
        return this.send();
    }


    public boolean send() throws IOException {
        byte[] message = this.createMessage();

        Channels channels = di.getChannels();
        String address = channels.getMC().getAddress();
        Integer port = channels.getMC().getPort();

        InetAddress group = InetAddress.getByName(address);
        MulticastSocket socket = new MulticastSocket(port);
        socket.joinGroup(group);

        DatagramPacket packet = new DatagramPacket(message, message.length, group, port);
        socket.send(packet);

        return true;
    }
}
