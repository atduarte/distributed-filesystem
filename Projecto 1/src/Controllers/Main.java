package Controllers;

import Peer.ChunkManager;
import Peer.DependencyInjection;
import Reactors.Reactor;
import Peer.BackupInfo;
import Utils.Channels;
import Utils.Constants;

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

        boolean isServer = true;

        System.out.println("André Duarte, Sérgio Esteves Version:"+ Constants.version);

        // Base Data; TODO: From ARGS
        String MCaddress = args[0];
        Integer MCport = Integer.parseInt(args[1]);
        String MDBaddress = args[2];
        Integer MDBport = Integer.parseInt(args[3]);
        String MDRaddress = args[4];
        Integer MDRport = Integer.parseInt(args[5]);

        String chunksPath = args[7];
        String backupInfoPath = args[6];

        // Dependency Injection
        DependencyInjection di = new DependencyInjection();

        // Menu
        Menu menu = new Menu(di);

        // Disk Space




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

        BackupInfo backupInfo = null;
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
        backupInfo.save();
        di.setBackupInfo(backupInfo);


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
