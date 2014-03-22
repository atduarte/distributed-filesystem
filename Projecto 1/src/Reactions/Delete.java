package Reactions;

import Peer.BackupInfo;
import Utils.Channels;
import Utils.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by atduarte on 13-03-2014.
 */
public class Delete extends Reaction
{
    final public static Pattern pattern = Pattern.compile(
            "^DELETE " +
            Constants.patternFileId
    );

    public Delete(Channels channels, BackupInfo backupInfo, byte[] data) {
        super(channels, backupInfo, data);
    }

    public void decodeData()
    {
        Matcher matches = pattern.matcher(new String(this.data));

        if (matches.find()) {
            this.fileId = matches.group(1);
        }
    }

    public void run()
    {
        // TODO: Process Message
        System.out.println("Delete");
    }
}
