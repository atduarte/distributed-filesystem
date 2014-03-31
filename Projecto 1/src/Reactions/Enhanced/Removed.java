package Reactions.Enhanced;

import Peer.BackupFileInfo;
import Peer.DependencyInjection;
import Reactions.Reaction;
import Server.Protocol.Normal.GetChunk;
import Server.Protocol.Normal.PutChunk;
import Utils.Constants;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Removed extends Reaction
{
    final public static Pattern pattern = Pattern.compile(
            "^REMOVED "
            + Constants.enhancedVersion + " "
            + Constants.patternFileId + " "
            + Constants.patternChunkNo
    );

    public Removed(DependencyInjection di, byte[] data) {
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
        // ...
    }
}
