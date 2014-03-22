package ServerProtocol;

import Server.BackupInfo;
import Utils.Constants;

import java.util.regex.Pattern;

public class Stored extends Thread
{
    private BackupInfo backupInfo;

    private byte[] data;

    final public static Pattern pattern = Pattern.compile(
            "^STORED " +
            Constants.patternVersion +
            Constants.patternFileId +
            Constants.patternChunkNo
    );

    public Stored(byte[] data, BackupInfo backupInfo)
    {
        this.backupInfo = backupInfo;
    }

    public void run()
    {
        System.out.println("Stored, Bitch!");
    }
}
