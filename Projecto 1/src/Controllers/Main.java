package Controllers;

import Peer.ChunkManager;
import Peer.DependencyInjection;
import Server.Protocol.Delete;
import Server.Protocol.GetChunk;
import Reactors.Reactor;
import Peer.BackupFileInfo;
import Peer.BackupInfo;
import Server.Protocol.PutChunk;
import Server.Protocol.Removed;
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

        String chunksPath = null;
        if (args[0].equals("server")) {
            chunksPath = "D:\\backups\\chunks_server";
        } else {
            chunksPath = "D:\\backups\\chunks_peer";
        }
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

        // Run Receiver
        Reactor receiver = new Reactor(di);
        receiver.run();

        if (args[0].equals("server")) {
            // Create File
            BackupFileInfo file = new BackupFileInfo();
            Random rand = new Random();
            file.setReplicationDegree(1);
            file.setHash("akjhdsasd89" /*+ rand.nextInt(100)*/);
            backupInfo.addFile(file);

            // Test Putchunk
            PutChunk putChunk = new PutChunk(di, file.getHash(), 5, file.getReplicationDegree(), "sáfoda".getBytes());
            putChunk.run();

            // Test GetChunk
            GetChunk getChunk = new GetChunk(di, file.getHash(), 5);
            getChunk.run();

            /*try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Test Delete
            Delete delete = new Delete(di, file.getHash());
            delete.run();*/
        } else {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Test Removed
            Removed removed = new Removed(di, "akjhdsasd89", 5);
            removed.run();
        }
    }
}
