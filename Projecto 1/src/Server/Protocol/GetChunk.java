package Server.Protocol;

import Utils.Channels;
import Utils.Constants;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class GetChunk {

    Channels channels;
    String fileId;
    Integer chunkNo;

    public GetChunk(Channels channels, String fileId, Integer chunkNo) {
        this.channels = channels;
        this.fileId = fileId;
        this.chunkNo = chunkNo;
    }

    private byte[] createMessage() {
        String message = "GETCHUNK ";
        message += Constants.version + " ";
        message += fileId + " ";
        message += chunkNo + " ";
        message += "\r\n \r\n ";

        byte[] one = message.getBytes();

        return one;
    }

    public byte[] run() throws IOException {
        send();
        return receive();
    }

    protected boolean send() throws IOException {
        byte[] message = this.createMessage();

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

        String address = channels.getMDR().getAddress();
        Integer port = channels.getMDR().getPort();

        InetAddress group = InetAddress.getByName(address);
        MulticastSocket socket = new MulticastSocket(port);
        socket.joinGroup(group);

        DatagramPacket packet = new DatagramPacket(message, message.length, group, port);
        socket.setSoTimeout(500);
        socket.receive(packet);

        // TODO: Just Return Chunk Data

        return packet.getData();
    }

}
