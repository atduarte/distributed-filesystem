package ServerProtocol;

import Utils.Channels;
import Utils.Constants;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by atduarte on 13-03-2014.
 */
public class PutChunk
{
    Channels channels;
    String fileId;
    Integer chunkNo;
    Integer replicationDegree;
    byte[] body;

    public PutChunk(Channels channels, String fileId, Integer chunkNo, Integer replicationDegree, byte[] body)
    {
        this.channels = channels;
        this.fileId = fileId;
        this.chunkNo = chunkNo;
        this.replicationDegree = replicationDegree;
        this.body = body;
    }

    private byte[] createMessage()
    {
        String sMessage = "PUTCHUNK ";
        sMessage += Constants.version + " ";
        sMessage += fileId + " ";
        sMessage += chunkNo + " ";
        sMessage += replicationDegree + " ";
        sMessage += "\r\n \r\n ";

        byte[] one = sMessage.getBytes();
        byte[] message = new byte[one.length + body.length];

        System.arraycopy(one, 0, message, 0, one.length);
        System.arraycopy(body, 0, message, one.length, body.length);

        return message;
    }

    public boolean send() throws IOException
    {
    	// Create Message
    	
        byte[] message = this.createMessage();

        String address = channels.getMDB().getAddress();
        Integer port = channels.getMDB().getPort();

        InetAddress group = InetAddress.getByName(address);
        MulticastSocket socket = new MulticastSocket(port);
        socket.joinGroup(group);

        DatagramPacket packet = new DatagramPacket(message, message.length, group, port);
        socket.send(packet);

        System.out.println("Sent PUTCHUNK");

        return true;
    }
}
