package Reactions.Normal;

import Peer.DependencyInjection;
import Peer.BackupInfo;
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
        BackupInfo backupInfo = di.getBackupInfo();

        // Check it's ours
        if (!backupInfo.isMine(this.fileId))
            return;

        backupInfo.incrementRealRepDegree(this.fileId, this.chunkNo);

        System.out.println("Someone Stored our Chunk");
    }
}
