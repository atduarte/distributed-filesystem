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
 * Created by atduarte on 13-03-2014.
 */
public class Delete extends Injectable
{
    String fileId;

    public Delete(DependencyInjection di, String fileId) {
        super(di);
        this.fileId = fileId;
    }

    private byte[] createMessage() {
        String sMessage = "DELETE " + fileId + Constants.separator;
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
