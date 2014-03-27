package Controllers;

import java.util.Scanner;

/**
 * Created by Sergio Esteves on 27/03/2014.
 */
public class Menu {

    Menu(){
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
    public void readoption()
    {
        Scanner in = new Scanner(System.in);
        op = in.nextInt();
        if(op==1) {
            //TODO: CALL BACKUP
        }
        else if(op==2)
        {
            //TODO: CALL RESTORE
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
