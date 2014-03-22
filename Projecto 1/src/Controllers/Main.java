package Controllers;

import Peer.ChunkManager;
import Reactors.Reactor;
import Peer.BackupFileInfo;
import Peer.BackupInfo;
import Server.Protocol.PutChunk;
import Utils.Channels;

import java.io.IOException;
import java.util.Random;

/**
 * Created by atduarte on 13-03-2014.
 */
public class Main
{
    public static void main(String [] args) throws IOException
    {
        // Base Data; TODO: From ARGS
        String MCaddress = "229.60.60.8";
        Integer MCport = 6789;
        String MDBaddress = "229.60.60.18";
        Integer MDBport = 6790;
        String MDRaddress = "229.60.60.28";
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

        BackupFileInfo file = new BackupFileInfo();
        Random rand = new Random();
        file.setHash("akjhdsasd" + rand.nextInt(10));
        backupInfo.addFile(file);
        byte[] body = "sáfoda".getBytes();
        PutChunk cenas = new PutChunk(channels, file.getHash(), 5, 8, body);
        cenas.send();

        // Backup Example
        //Backup backup = new Backup(channels, backupInfo);
        //backup.sendFolder("...");

        // Restore Examples
//        Restore restore = new Restore(backupInfo);
        // ...
    }
}
