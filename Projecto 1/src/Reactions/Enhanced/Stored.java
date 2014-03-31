package Reactions.Enhanced;

import Peer.BackupInfo;
import Peer.DependencyInjection;
import Reactions.Reaction;
import Utils.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Stored extends Reaction
{
    final public static Pattern pattern = Pattern.compile(
            "^STORED "
                    + Constants.enhancedVersion + " "
                    + Constants.patternFileId + " "
                    + Constants.patternChunkNo
    );

    public Stored(DependencyInjection di, byte[] data) {
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
