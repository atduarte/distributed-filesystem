package Controllers;

import Peer.ChunkManager;
import Peer.DependencyInjection;
import Reactors.Reactor;
import Peer.BackupInfo;
import Server.ReclaimSpace;
import Utils.Channels;
import Utils.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

/**
 * Created by atduarte on 13-03-2014.
 */
public class Main
{
    public static void main(String [] args) throws IOException {

        boolean isServer =false;


        System.out.println("André Duarte, Sérgio Esteves Version:"+ Constants.version);

        // Base Data; TODO: From ARGS
        String MCaddress = "239.0.0.1";
        Integer MCport = 8765;
        String MDBaddress = "239.0.0.1";
        Integer MDBport = 8766;
        String MDRaddress = "239.0.0.1";
        Integer MDRport = 8767;

        String chunksPathServer = "S:\\serverfolder";
        String chunksPathPeer = "S:\\backups";

        String backupInfoPathServer = "S:\\serverfolder";
        String backupInfoPathPeer = "S:\\backups";

        // Dependency Injection
        DependencyInjection di = new DependencyInjection();



        // Menu
        Menu menu = new Menu(di);


        // Channels
        Channels channels = new Channels();
        channels.setMC(MCaddress, MCport);
        channels.setMDB(MDBaddress, MDBport);
        channels.setMDR(MDRaddress, MDRport);
        di.setChannels(channels);

        // Chunk Manager
        String chunksPath = null;
        if (isServer) {
            chunksPath = chunksPathServer;
        } else {
            chunksPath = chunksPathPeer;
        }
        ChunkManager chunkManager = new ChunkManager(chunksPath);
        di.setChunkManager(chunkManager);

        // Backup Info

        BackupInfo backupInfo = null;
        String backupInfoPath = null;

        if (isServer) {
            backupInfoPath = backupInfoPathServer;
        } else {
            backupInfoPath = backupInfoPathPeer;
        }

        try {
            FileInputStream fileIn = new FileInputStream(backupInfoPath + "\\backupInfo.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            backupInfo = (BackupInfo) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("BackupInfo Loaded");
        } catch(IOException i) {
            backupInfo = new BackupInfo(backupInfoPath);
        } catch (ClassNotFoundException e) {
            backupInfo = new BackupInfo(backupInfoPath);
        }
        di.setBackupInfo(backupInfo);

        System.out.print("Disk Space (kb) : ");
        Scanner in = new Scanner(System.in);
        int diskspace = in.nextInt();
        di.getBackupInfo().setUsedDiskSpace(diskspace);


        // Run Receiver
        Reactor receiver = new Reactor(di);
        receiver.run();

       /* ReclaimSpace n1 = new ReclaimSpace(di);
        System.out.println(n1.getFolderSize(new File("S:\\serverfolder")));
        n1.run();*/

        // Menu Options
        while (true) {
            menu.show();
            menu.readOption();
        }


    }
}
