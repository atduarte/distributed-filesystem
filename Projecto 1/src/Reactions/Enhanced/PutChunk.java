package Reactions.Enhanced;

import Peer.ChunkManager;
import Peer.DependencyInjection;
import Reactions.Reaction;
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
public class PutChunk extends Reaction
{
    final public static Pattern pattern = Pattern.compile(
            "^PUTCHUNK " +
            Constants.enhancedVersion  + " " +
            Constants.patternFileId  + " " +
            Constants.patternChunkNo  + " " +
            Constants.patternReplicationDeg

    );

	public PutChunk(DependencyInjection di, byte[] data) {
        super(di, data);
	}

    public void decodeData()
    {
        Matcher matches = pattern.matcher(new String(data));

        if (matches.find()) {
            this.version = matches.group(1);
            this.fileId = matches.group(2);
            this.chunkNo = Integer.parseInt(matches.group(3));
            this.replicationDegree = Integer.parseInt(matches.group(4));
            this.body = Constants.getBodyFromMessage(data);
        }
    }

    public void run()
    {

    }
}
