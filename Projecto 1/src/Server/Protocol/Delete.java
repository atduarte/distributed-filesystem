package Server.Protocol;

import Utils.Channels;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by atduarte on 13-03-2014.
 */
public class Delete {


    Channels channels;
    String fileId;
    Integer chunkNo;


    public Delete(Channels channels, String fileId) {
        this.channels = channels;
        this.fileId = fileId;

    }

    private byte[] createMessage() {
        String sMessage = "DELETE ";
        sMessage += fileId + " ";

        sMessage += "\r\n \r\n ";

        byte[] one = sMessage.getBytes();

        return one;
    }


    public boolean send() throws IOException {
        byte[] message = this.createMessage();

        String address = channels.getMDB().getAddress();
        Integer port = channels.getMDB().getPort();

        InetAddress group = InetAddress.getByName(address);
        MulticastSocket socket = new MulticastSocket(port);
        socket.joinGroup(group);

        DatagramPacket packet = new DatagramPacket(message, message.length, group, port);
        socket.send(packet);

        socket.receive(packet);

        System.out.println(new String(packet.getData()));

        return true;
    }
}
