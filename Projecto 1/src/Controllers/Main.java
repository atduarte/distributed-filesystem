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
import Utils.Constants;

import java.io.IOException;
import java.util.Random;

/**
 * Created by atduarte on 13-03-2014.
 */
public class Main
{
    public static void main(String [] args) throws IOException {
        // Base Data; TODO: From ARGS
        String MCaddress = "239.0.0.1";
        Integer MCport = 8765;
        String MDBaddress = "239.0.0.1";
        Integer MDBport = 8766;
        String MDRaddress = "239.0.0.1";
        Integer MDRport = 8767;

        String chunksPath = null;


        // Dependency Injection
        DependencyInjection di = new DependencyInjection();

        // Channels
        Channels channels = new Channels();
        channels.setMC(MCaddress, MCport);
        channels.setMDB(MDBaddress, MDBport);
        channels.setMDR(MDRaddress, MDRport);
        di.setChannels(channels);

        String backupInfoPath = "D:\\backups\\info";


        // Chunk Manager
        ChunkManager chunkManager = new ChunkManager(chunksPath);
        di.setChunkManager(chunkManager);

        // Backup Info
        BackupInfo backupInfo = new BackupInfo(backupInfoPath);
        di.setBackupInfo(backupInfo);

        // Run Receiver
        Reactor receiver = new Reactor(di);
        receiver.run();

        Menu menu = new Menu();
        menu.ask();

        menu.readanswer();
        if (menu.isServer()) {
            chunksPath = "D:\\backups\\chunks_server";
            while(!menu.isExit()) {
                menu.show();
                menu.readoption();
            }

        } else {
            chunksPath = "D:\\backups\\chunks_peer";
        }
        System.out.println("André Duarte, Sérgio Esteves Version:"+ Constants.version);
    }
}
