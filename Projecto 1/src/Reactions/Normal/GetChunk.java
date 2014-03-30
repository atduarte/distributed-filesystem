package Reactions.Normal;

import Peer.DependencyInjection;
import Peer.ChunkManager;
import Reactions.Reaction;
import Utils.Channels;
import Utils.Constants;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetChunk extends Reaction
{
    final public static Pattern pattern = Pattern.compile(
            "^GETCHUNK " +
            Constants.version + " " +
            Constants.patternFileId + " " +
            Constants.patternChunkNo
    );

    public GetChunk(DependencyInjection di, byte[] data) {
        super(di, data);
        this.version = Constants.version;
    }

    public void decodeData()
    {
        Matcher matches = pattern.matcher(new String(this.data));

        if (matches.find()) {
            this.fileId = matches.group(1);
            this.chunkNo = Integer.parseInt(matches.group(2));
        }
    }

    public void run()
    {
        ChunkManager chunkManager = di.getChunkManager();

        if (!chunkManager.hasChunk(fileId, chunkNo)) {
            return;
        }

        byte[] chunkData = chunkManager.getChunk(fileId, chunkNo);
        byte[] message = createMessage(chunkData);

        // Wait Random Delay
        int randWait = (new Random()).nextInt(400);

        // Check Network, for repeat packets
        Channels channels = di.getChannels();
        String address = channels.getMDR().getAddress();
        Integer port = channels.getMDR().getPort();

        InetAddress group = null;
        MulticastSocket socket = null;
        try {
            group = InetAddress.getByName(address);
            socket = new MulticastSocket(port);
            socket.joinGroup(group);
        } catch (IOException e) {
            return;
        }

        try {
            byte[] buf = new byte[Constants.chunkSize + 2048];
            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, port);
            socket.setSoTimeout(randWait);
            socket.receive(packet);

            byte[] data = new byte[packet.getLength()];
            System.arraycopy(packet.getData(), 0, data, 0, packet.getLength());

            // Check if it's the same packet
            if(Constants.getNElementFromMessage(data,1).equals("CHUNK") &&
               Constants.getNElementFromMessage(data,2).equals(fileId) &&
               Constants.getNElementFromMessage(data,3).equals(chunkNo.toString()))
            {
                System.out.println("Dont send Chunk. Someone already sent.");
                return;
            }
        } catch (IOException e) {
        }

        // Send
        try {
            DatagramPacket packet = new DatagramPacket(message, message.length, group, port);
            socket.send(packet);
            System.out.println("Sent CHUNK");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] createMessage(byte[] body) {
        String sMessage = "CHUNK " + version + " " + fileId + " " + chunkNo + Constants.separator;

        byte[] one = sMessage.getBytes();
        byte[] message = new byte[one.length + body.length];

        System.arraycopy(one, 0, message, 0, one.length);
        System.arraycopy(body, 0, message, one.length, body.length);

        return message;
    }
}
