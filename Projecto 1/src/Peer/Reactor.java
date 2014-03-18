package Peer;

import Utils.Channels;

import java.io.IOException;

public class Reactor
{
    Channels channels;

    public Reactor(Channels channels)
    {
        this.channels = channels;
    }

    public void run() throws IOException
    {
        if (channels.getMC() != null) {
            MCReactor MCReactor = new MCReactor(channels.getMC());
            MCReactor.start();
        }

        if (channels.getMDB() != null) {
            MDBReactor MDBReactor = new MDBReactor(channels);
            MDBReactor.start();
        }

    }
}
