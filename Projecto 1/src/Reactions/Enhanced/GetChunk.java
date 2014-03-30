package Reactions.Enhanced;

import Peer.ChunkManager;
import Peer.DependencyInjection;
import Reactions.Reaction;
import Utils.Channels;
import Utils.Constants;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetChunk extends Reaction
{
    private DatagramPacket packet;
    final public static Pattern pattern = Pattern.compile(
            "^GETCHUNK " +
            Constants.enhancedVersion + " " +
            Constants.patternFileId + " " +
            Constants.patternChunkNo
    );

    public GetChunk(DependencyInjection di, DatagramPacket packet, byte[] data) {
        super(di, data);
        this.packet = packet;
    }

    public void decodeData()
    {
        Matcher matches = pattern.matcher(new String(this.data));

        if (matches.find()) {
            this.version = matches.group(1);
            this.fileId = matches.group(2);
            this.chunkNo = Integer.parseInt(matches.group(3));
        }
    }

    public void run()
    {
        ChunkManager chunkManager = di.getChunkManager();

        if (!chunkManager.hasChunk(fileId, chunkNo)) {
            return;
        }

        byte[] chunkData = chunkManager.getChunk(fileId, chunkNo);

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
            if(data == "HAVECHUNK".getBytes()) {
                System.out.println("Dont send Chunk. Someone already sent.");
                return;
            }

            // Send MultiCast
            byte[] message = "HAVECHUNK".getBytes();
            packet = new DatagramPacket(message, message.length, group, port);
            socket.send(packet);
        } catch (IOException ignored) {
        }

        // Send TCP
        DataOutputStream dos = null;
        try {
            Socket connectionSocket = new Socket(packet.getAddress(), port);
            OutputStream out = connectionSocket.getOutputStream();
            dos = new DataOutputStream(out);
            dos.writeInt(chunkData.length);
            dos.write(chunkData, 0, chunkData.length);
        } catch (IOException e) {
            return;
        }


    }
}
