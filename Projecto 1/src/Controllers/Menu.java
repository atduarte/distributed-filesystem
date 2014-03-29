package Controllers;

import Peer.DependencyInjection;
import Peer.Injectable;
import Server.Backup;
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
        isServer=false;
        isPeer=false;
        exit=false;
    }

    private int op;

    private boolean isServer;
    private boolean isPeer;

    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    private boolean exit;


    public boolean isServer() {
        return isServer;
    }

    public void setServer(boolean isServer) {
        this.isServer = isServer;
    }

    public boolean isPeer() {
        return isPeer;
    }

    public void setPeer(boolean isPeer) {
        this.isPeer = isPeer;
    }


    public void show()
    {
        System.out.println("1-Backup File");
        System.out.println("2-Restore File");
        System.out.println("3-Exit");
    }
    public void ask()
    {
        System.out.println("1-Server");
        System.out.println("2-Peer");
    }
    public void readOption()
    {
        Scanner in = new Scanner(System.in);
        op = in.nextInt();
        if(op==1) {
            Backup backup = new Backup(di);

            File file = new File("S:\\serverfolder\\1.txt");
            try {
                backup.sendFile(file, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(op==2)
        {
            Restore restore = new Restore(di);
            String s = "S:\\serverfolder\\1.txt";
            //String path = new String("S:\\backups");
            try {
                restore.receiveFile(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(op==3)
        {
            exit=true;
        }
    }

    public void readanswer()
    {
        Scanner in = new Scanner(System.in);
        op = in.nextInt();
        if(op==1) {
            isServer = true;
        }
        else if(op==2)
        {
            isPeer = true;
        }
    }


}
