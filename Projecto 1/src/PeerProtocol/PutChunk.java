package PeerProtocol;

import Utils.Channel;
import Utils.Channels;
import Utils.Constants;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by atduarte on 13-03-2014.
 */
public class PutChunk  extends Thread
{
    Channel channel;
    private byte[] data;

    private String version;
    private String fileId;
    private Integer chunkNo;
    private Integer replicationDegree;
    private byte[] body;

    final public static Pattern pattern = Pattern.compile(
            "^PUTCHUNK " +
            Constants.patternVersion +
            Constants.patternFileId +
            Constants.patternChunkNo

    );

    public PutChunk(Channel channel,byte[] data)
    {

        this.data = data;
        this.channel = channel;
        Matcher matches = pattern.matcher(new String(data));

        if (matches.find()) {
            this.version = matches.group(1);
            this.fileId = matches.group(2);
            this.chunkNo = Integer.parseInt(matches.group(3));

        }
    }

    public void run()
    {

        // TODO: Store

        String sMessage = "STORED " + this.version + " " + this.fileId + " " + this.chunkNo;
        sMessage += "\r\n \r\n ";

        byte[] message = sMessage.getBytes();

        String address = channel.getAddress();
        Integer port = channel.getPort();

        InetAddress group = null;
        try {
            group = InetAddress.getByName(address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        MulticastSocket socket = null;
        try {
            socket = new MulticastSocket(port);
            socket.joinGroup(group);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DatagramPacket packet = new DatagramPacket(message, message.length, group, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Sent STORED");
    }
}
