package PeerProtocol;

import Utils.Constants;

import java.util.regex.Pattern;

/**
 * Created by atduarte on 13-03-2014.
 */
public class Delete extends Thread
{
    private byte[] data;

    private String version;
    private String fileId;
    private Integer chunkNo;
    private Integer replicationDegree;
    private byte[] body;

    final public static Pattern pattern = Pattern.compile(
            "^DELETE " +
            Constants.patternVersion +
            Constants.patternFileId +
            Constants.patternChunkNo +
            Constants.patternReplicationDeg
    );

    public Delete(byte[] data)
    {
        this.data = data;
    }

    public void run()
    {
        // TODO: Process Message
        System.out.println("Delete");
    }
}
