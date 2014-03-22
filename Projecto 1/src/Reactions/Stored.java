package Reactions;

import Peer.BackupInfo;
import Utils.Channels;
import Utils.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Stored extends Reaction
{
    final public static Pattern pattern = Pattern.compile(
            "^STORED " +
            Constants.patternVersion +
            Constants.patternFileId +
            Constants.patternChunkNo
    );

    public Stored(Channels channels, BackupInfo backupInfo, byte[] data) {
        super(channels, backupInfo, data);
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
        System.out.println("Someone Stored our Chunk");
    }
}