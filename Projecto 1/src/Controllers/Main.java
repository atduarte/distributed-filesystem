package Controllers;

import Peer.BackupFileInfo;
import Peer.ChunkManager;
import Peer.DependencyInjection;
import Reactors.Reactor;
import Peer.BackupInfo;
import Utils.Channels;
import Utils.Constants;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

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
        BackupInfo backupInfo = null;
        try
        {
            FileInputStream fileIn = new FileInputStream(backupInfoPath + "\\backupInfo.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            backupInfo = (BackupInfo) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("BackupInfo Loaded");
        }catch(IOException i) {
            backupInfo = new BackupInfo(backupInfoPath);
        } catch (ClassNotFoundException e) {
            backupInfo = new BackupInfo(backupInfoPath);
        }
        di.setBackupInfo(backupInfo);

        // Run Receiver
        Reactor receiver = new Reactor(di);
        receiver.run();

        Menu menu = new Menu(di);
        menu.ask();

        menu.readanswer();

        if (menu.isServer()) {
            chunkManager.setChunksPath("D:\\backups\\chunks_server");
        } else {
            chunkManager.setChunksPath("D:\\backups\\chunks_server");
        }

        while(!menu.isExit()) {
            menu.show();
            menu.readoption();
        }

        System.out.println("André Duarte, Sérgio Esteves Version:"+ Constants.version);
    }
}
