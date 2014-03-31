package Controllers;

import Peer.BackupFileInfo;
import Peer.DependencyInjection;
import Peer.Injectable;
import Server.Backup;
import Server.ReclaimSpace;
import Server.Restore;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Sergio Esteves on 27/03/2014.
 */
public class Menu extends Injectable
{

    public Menu(DependencyInjection di){
        super(di);
        exit=false;
    }

    private int op;


    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    private boolean exit;


    public void show()
    {
        System.out.println("1-Backup File");
        System.out.println("2-Restore File");
        System.out.println("3-Reclaim Space");
        System.out.println("4-Exit");
    }

    public void readOption()
    {
        Scanner in = new Scanner(System.in);
        op = in.nextInt();
        if(op==1) {
            Backup backup = new Backup(di);
            System.out.println("Backup File Path: ");

            String path = new String();
            path = in.nextLine();
            path = in.nextLine();
            File file = new File(path);

            int repdegree;

            System.out.print("Replication Degree: ");
            repdegree =  in.nextInt();

            try {
                backup.sendFile(file, repdegree);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(op==2)
        {
            Restore restore = new Restore(di);
            ArrayList<BackupFileInfo> totalfiles = di.getBackupInfo().getFiles();

            for(int i=0;i<totalfiles.size();i++)
            {
                System.out.println(i+"-"+" "+totalfiles.get(i).getName());
            }

            int order = in.nextInt();
            //String path = new String("S:\\backups");
            try {
                restore.receiveFile(totalfiles.get(order).getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(op==3)
        {
            this.askUsedSpace();
            ReclaimSpace r1 = new ReclaimSpace(di);
            r1.run();

        }
        else if(op==4)
        {
            exit=true;
        }
    }


    public void askUsedSpace() {
        System.out.print("Disk Space (kb) : ");
        Scanner in = new Scanner(System.in);
        int diskspace = in.nextInt();
        di.getBackupInfo().setUsedDiskSpace(diskspace);
    }
}
