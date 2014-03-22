package Controllers;

import Peer.ChunkManager;
import Peer.Reactor;
import Server.Backup;
import Server.BackupInfo;
import Server.Restore;
import ServerProtocol.PutChunk;
import Utils.Channels;
import Utils.Constants;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String MDBaddress = "228.15.16.17";
        Integer MDBport = 6790;
        String MDRaddress = "228.25.26.27";
        Integer MDRport = 6791;
        String chunksPath = "D:\\backupChunks";
        String backupInfoPath = "D:\\backupInfo";

        // Channels
        Channels channels = new Channels();
        channels.setMC(MCaddress, MCport);
        channels.setMDB(MDBaddress, MDBport);
        channels.setMDR(MDRaddress, MDRport);

        ChunkManager chunkManager = new ChunkManager(chunksPath);
        BackupInfo backupInfo = new BackupInfo(backupInfoPath);

        // Run Receiver
        Reactor receiver = new Reactor(channels, backupInfo);
        receiver.run();

        // TODO

        byte[] body = "sáfoda".getBytes();
        PutChunk cenas = new PutChunk(channels, "akjhdsasd", 5, 8, body);
        cenas.send();

        // Backup Example
        //Backup backup = new Backup(channels, backupInfo);
        //backup.sendFolder("...");

        // Restore Examples
//        Restore restore = new Restore(backupInfo);
        // ...
    }
}
