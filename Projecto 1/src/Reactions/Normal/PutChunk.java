package Reactions.Normal;

import Peer.ChunkInfo;
import Peer.DependencyInjection;
import Peer.ChunkManager;
import Reactions.Reaction;
import Utils.Channels;
import Utils.Constants;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by atduarte on 13-03-2014.
 */
public class PutChunk extends Reaction
{
    final public static Pattern pattern = Pattern.compile(
            "^PUTCHUNK " +
            Constants.version  + " " +
            Constants.patternFileId  + " " +
            Constants.patternChunkNo  + " " +
            Constants.patternReplicationDeg

    );

	public PutChunk(DependencyInjection di, byte[] data) {
        super(di, data);
        this.version = Constants.version;
	}

    public void decodeData()
    {
        Matcher matches = pattern.matcher(new String(data));

        if (matches.find()) {
            this.fileId = matches.group(1);
            this.chunkNo = Integer.parseInt(matches.group(2));
            this.replicationDegree = Integer.parseInt(matches.group(3));
            this.body = Constants.getBodyFromMessage(data);
        }
    }

    public void run()
    {
    	// Check if mine
    	if (di.getBackupInfo().isMine(this.fileId)) {
    		return;
    	}


        // Reset Real Replication Degree
        // && Stop Possible Removed-Reactions Waiting
        ChunkManager chunkManager = di.getChunkManager();
        chunkManager.resetChunkInfo(fileId, chunkNo, replicationDegree);

        // Wait Random Delay
        int randWait = (new Random()).nextInt(400);
        try {
            Thread.sleep(randWait);
        } catch (InterruptedException ignored) {
        }

        // Check if it's necessary to store
        ChunkInfo chunkInfo = chunkManager.getChunkInfo(fileId, chunkNo);
        if (chunkInfo.realRepDegree >= replicationDegree) {
            return;
        }

        // Store
        try {
            chunkManager.deleteChunk(fileId, chunkNo);
            chunkManager.addChunk(fileId, chunkNo, body);
        } catch (IOException e) {
            return;
        }

        //
        // Send STORED Message
        //

        String sMessage = "STORED " + this.version + " " + this.fileId + " " + this.chunkNo + Constants.separator;
        byte[] message = sMessage.getBytes();

        Channels channels = di.getChannels();
        String address = channels.getMC().getAddress();
        Integer port = channels.getMC().getPort();

        InetAddress group = null;
        try {
            group = InetAddress.getByName(address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return;
        }

        MulticastSocket socket = null;
        try {
            socket = new MulticastSocket(port);
            socket.joinGroup(group);
        } catch (IOException e) {
            e.printStackTrace();
            return;
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
