package Reactors;

import Controllers.DependencyInjection;
import Controllers.Injectable;
import Peer.BackupInfo;
import Utils.Channels;

import java.io.IOException;

public class Reactor extends Injectable
{
    public Reactor(DependencyInjection di)
    {
        super(di);
    }

    public void run() throws IOException
    {
        Channels channels = di.getChannels();

        if (channels.getMC() != null) {
            MCReactor MCReactor = new MCReactor(di);
            MCReactor.start();
        }

        if (channels.getMDB() != null) {
            MDBReactor MDBReactor = new MDBReactor(di);
            MDBReactor.start();
        }

    }
}
