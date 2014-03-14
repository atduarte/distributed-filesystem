package Controllers;

import PeerProtocol.RequestInterpreter;
import ServerProtocol.Backup;
import ServerProtocol.Restore;
import Utils.FilesManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by atduarte on 13-03-2014.
 */
public class Main
{
    public static void main(String [] args) throws IOException
    {
        // Base Data; TODO: From ARGS
        String address = "228.5.6.7";
        Integer port = 6789;
        String backupPath = null;

        BackupInfo backupInfo = new BackupInfo(backupPath);

        // Run Receiver
        RequestInterpreter receiver = new RequestInterpreter(address, port);
        receiver.run();

        // TODO

        // Backup Example
        Backup backup = new Backup(backupInfo);
        backup.sendFolder("...");

        // Restore Examples
        Restore restore = new Restore(backupInfo);
        // ...
    }
}
