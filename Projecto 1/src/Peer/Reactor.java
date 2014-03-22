package Peer;

import Server.BackupInfo;
import Utils.Channels;

import java.io.IOException;

public class Reactor
{
    private BackupInfo backupInfo;
    Channels channels;

    public Reactor(Channels channels, BackupInfo backupInfo)
    {
        this.channels = channels;
        this.backupInfo = backupInfo;
    }

    public void run() throws IOException
    {
        if (channels.getMC() != null) {
            MCReactor MCReactor = new MCReactor(channels, backupInfo);
            MCReactor.start();
        }

        if (channels.getMDB() != null) {
            MDBReactor MDBReactor = new MDBReactor(channels, backupInfo);
            MDBReactor.start();
        }

    }
}
