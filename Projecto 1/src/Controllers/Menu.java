package Controllers;

import Peer.DependencyInjection;
import Peer.Injectable;
import Server.Backup;
import Server.ReclaimSpace;
import Server.Restore;

import java.io.File;
import java.io.IOException;
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

            File file = new File("D:\\Teste\\1.txt");
            try {
                backup.sendFile(file, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(op==2)
        {
            Restore restore = new Restore(di);
            String s = "D:\\Teste\\1.txt";
            //String path = new String("S:\\backups");
            try {
                restore.receiveFile(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(op==3)
        {
            ReclaimSpace r1 = new ReclaimSpace(di);
            r1.run();

        }
        else if(op==4)
        {
            exit=true;
        }
    }




}
