package Server.Protocol.Enhanced;

import Peer.DependencyInjection;
import Peer.Injectable;
import Utils.Channels;
import Utils.Constants;

import java.io.*;
import java.net.*;

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
        message += Constants.enhancedVersion + " ";
        message += fileId + " ";
        message += chunkNo;
        message += Constants.separator;

        return message.getBytes();
    }

    public byte[] run() throws IOException {
        send();
        return receive();
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
        Integer port = di.getChannels().getMDR().getPort();
        /*byte[] buffer = new byte[Constants.chunkSize + 2084];

        Channels channels = di.getChannels();
        String address = channels.getMDR().getAddress();

        InetAddress group = InetAddress.getByName(address);
        MulticastSocket socket = new MulticastSocket(port);
        socket.joinGroup(group);

        // Verify Packet

        while(true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, port);
            socket.setSoTimeout(1000);
            socket.receive(packet);

            if (packet.getData() == "HAVECHUNK".getBytes()) {
                break;
            }
        }

        socket.close();*/

        // Open Socket
        ServerSocket srvSocket = new ServerSocket(port);

        // Accept Socket
        Socket connectionSocket = srvSocket.accept();


        InputStream in = connectionSocket.getInputStream();
        DataInputStream dis = new DataInputStream(in);

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int len = dis.readInt();
        byte[] data = new byte[len];
        if (len > 0) {
            dis.readFully(data);
        }

        connectionSocket.close();
        srvSocket.close();

        return data;

    }

}
