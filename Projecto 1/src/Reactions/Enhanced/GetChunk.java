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
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetChunk extends Reaction
{
    final public static Pattern pattern = Pattern.compile(
            "^GETCHUNK " +
            Constants.enhancedVersion + " " +
            Constants.patternFileId + " " +
            Constants.patternChunkNo
    );

    public GetChunk(DependencyInjection di, byte[] data) {
        super(di, data);
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
        //...
    }
}
