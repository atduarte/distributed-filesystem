package Reactions;

import Controllers.DependencyInjection;
import Peer.BackupInfo;
import Utils.Channels;
import Utils.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetChunk extends Reaction
{
    final public static Pattern pattern = Pattern.compile(
            "^GETCHUNK " +
            Constants.patternVersion +
            Constants.patternFileId +
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
        // TODO: Process Message
        System.out.println("GetChunk");
    }
}
