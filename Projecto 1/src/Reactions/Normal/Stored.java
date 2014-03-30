package Reactions.Normal;

import Peer.ChunkManager;
import Peer.DependencyInjection;
import Reactions.Reaction;
import Utils.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Stored extends Reaction
{
    final public static Pattern pattern = Pattern.compile(
            "^STORED "
                    + Constants.version + " "
                    + Constants.patternFileId + " "
                    + Constants.patternChunkNo
    );

    public Stored(DependencyInjection di, byte[] data) {
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
        chunkManager.incrementRealRepDegree(fileId, chunkNo);

        System.out.println("Someone Stored our Chunk");
    }
}
