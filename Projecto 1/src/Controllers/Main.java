package Controllers;

import Peer.Reactor;
import Server.Backup;
import Server.BackupInfo;
import Server.Restore;

import java.io.IOException;

/**
 * Created by atduarte on 13-03-2014.
 */
public class Main
{
    public static void main(String [] args) throws IOException
    {
        // Base Data; TODO: From ARGS
        String MCaddress = "228.5.6.7";
        Integer MCport = 6789;
        String MDBaddress = "228.5.6.7";
        Integer MDBport = 6790;
        String MDRaddress = "228.5.6.7";
        Integer MDRport = 6791;
        String backupPath = null;

        BackupInfo backupInfo = new BackupInfo(backupPath);

        // Run Receiver
        Reactor receiver = new Reactor();
        receiver.setMC(MCaddress, MCport);
        receiver.setMDB(MDBaddress, MDBport);
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
