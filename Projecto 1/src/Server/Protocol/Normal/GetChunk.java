package Server.Protocol.Normal;

import Peer.DependencyInjection;
import Peer.Injectable;
import Utils.Channels;
import Utils.Constants;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Random;

public class GetChunk extends Injectable
{
    String fileId;
    Integer chunkNo;

    public GetChunk(DependencyInjection di, String fileId, Integer chunkNo) {
        super(di);
        this.fileId = fileId;
        this.chunkNo = chunkNo;
    }

    private byte[] createMessage() {
        String message = "GETCHUNK ";
        message += Constants.version + " ";
        message += fileId + " ";
        message += chunkNo;
        message += Constants.separator;

        return message.getBytes();
    }

    public byte[] run() {
        try {
            send();
            return receive();
        } catch (IOException e) {
            return this.run();
        }
    }

    protected boolean send() throws IOException {
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

    protected byte[] receive() throws IOException {
        byte[] message = new byte[Constants.chunkSize + 2084];

        Channels channels = di.getChannels();
        String address = channels.getMDR().getAddress();
        Integer port = channels.getMDR().getPort();

        InetAddress group = InetAddress.getByName(address);
        MulticastSocket socket = new MulticastSocket(port);
        socket.joinGroup(group);

        DatagramPacket packet = new DatagramPacket(message, message.length, group, port);
        socket.setSoTimeout(1000);
        socket.receive(packet);

        System.out.println("Received one packet");
        if(Constants.getNElementFromMessage(message,2).equals(fileId) && Constants.getNElementFromMessage(message,3).equals(chunkNo.toString())) {
            System.out.println("GetChunk: Correct packet");
            return Constants.getBodyFromMessage(packet.getData());
        } else {
            System.out.println("GetChunk: Wrong packet");
            return receive();
        }

    }

}
