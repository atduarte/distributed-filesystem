package Controllers;

import Peer.ChunkManager;
import Reactors.Reactor;
import Peer.BackupFileInfo;
import Peer.BackupInfo;
import Server.Protocol.PutChunk;
import Utils.Channels;
import Utils.Constants;

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
        String chunksPath = "D:\\backups\\chunks";
        String backupInfoPath = "D:\\backups\\info";

        // Dependency Injection
        DependencyInjection di = new DependencyInjection();

        // Channels
        Channels channels = new Channels();
        channels.setMC(MCaddress, MCport);
        channels.setMDB(MDBaddress, MDBport);
        channels.setMDR(MDRaddress, MDRport);
        di.setChannels(channels);

        // Chunk Manager
        ChunkManager chunkManager = new ChunkManager(chunksPath);
        di.setChunkManager(chunkManager);

        // Backup Info
        BackupInfo backupInfo = new BackupInfo(backupInfoPath);
        di.setBackupInfo(backupInfo);

        /*
        String s=new String("aaaaa bbbbbb ddddd eeeee ");
        System.out.println(new String(Constants.getNElementFromMessage(s.getBytes(),0)));
        System.out.println(new String(Constants.getNElementFromMessage(s.getBytes(),1)));
        System.out.println(new String(Constants.getNElementFromMessage(s.getBytes(),2)));
        System.out.println(new String(Constants.getNElementFromMessage(s.getBytes(),3)));

        */

        // Run Receiver
        Reactor receiver = new Reactor(di);
        receiver.run();

        // TODO

        BackupFileInfo file = new BackupFileInfo();
        Random rand = new Random();
        file.setHash("akjhdsasd" + rand.nextInt(10));
        backupInfo.addFile(file);
        byte[] body = "sáfoda".getBytes();

        PutChunk cenas = new PutChunk(di, file.getHash(), 5, 1, body);
        cenas.send();

        // Backup Example
        //Backup backup = new Backup(channels, backupInfo);
        //backup.sendFolder("...");

        // Restore Examples
//        Restore restore = new Restore(backupInfo);
        // ...




    }
}
