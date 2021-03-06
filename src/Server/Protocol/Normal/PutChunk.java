package Server.Protocol.Normal;

import Peer.*;
import Utils.Channels;
import Utils.Constants;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by atduarte on 13-03-2014.
 */
public class PutChunk extends Injectable
{
    String fileId;
    Integer chunkNo;
    Integer replicationDegree;
    byte[] body;

    public PutChunk(DependencyInjection di, String fileId, Integer chunkNo, Integer replicationDegree, byte[] body) {
        super(di);
        this.fileId = fileId;
        this.chunkNo = chunkNo;
        this.replicationDegree = replicationDegree;
        this.body = body;
    }

    private byte[] createMessage() {
        String sMessage = "PUTCHUNK ";
        sMessage += Constants.version + " ";
        sMessage += fileId + " ";
        sMessage += chunkNo + " ";
        sMessage += replicationDegree;
        sMessage += Constants.separator;

        byte[] one = sMessage.getBytes();
        byte[] message = new byte[one.length + body.length];

        System.arraycopy(one, 0, message, 0, one.length);
        System.arraycopy(body, 0, message, one.length, body.length);

        return message;
    }

    public boolean run() throws IOException {
        return this.send();
    }

    public boolean send() throws IOException
    {
        ChunkManager chunkManager = di.getChunkManager();

        // Create Message
        byte[] message = this.createMessage();

        Channels channels = di.getChannels();
        String address = channels.getMDB().getAddress();
        Integer port = channels.getMDB().getPort();

        InetAddress group = InetAddress.getByName(address);

        // Just 5 times. Different waiting times
        Integer timeoutTime = 500;
        Integer n = 1;
        while (n < 6) {
            // Reset Replication Degree
            chunkManager.resetChunkInfo(fileId, chunkNo, replicationDegree);

            MulticastSocket socket = new MulticastSocket(port);
            socket.joinGroup(group);

            DatagramPacket packet = new DatagramPacket(message, message.length, group, port);
            socket.send(packet);

            socket.close();

            System.out.println("Sent PUTCHUNK");

            // Wait for replies
            try {
                Thread.sleep(timeoutTime);
            } catch (InterruptedException e) {
                break;
            }

            // Verify Real Replication Degrees
            ChunkInfo chunkInfo = chunkManager.getChunkInfo(fileId, chunkNo);
            if (chunkInfo.realRepDegree >= replicationDegree) {
                return true;
            } else {
                timeoutTime *= 2;
                n++;
            }
        }

        return false;
    }
}
