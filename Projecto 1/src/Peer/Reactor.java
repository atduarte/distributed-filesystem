package Peer;

import java.io.IOException;

public class Reactor
{
    String MCaddress = null;
    Integer MCport = null;
    String MDBaddress = null;
    Integer MDBport = null;
    String MDRaddress = null;
    Integer MDRport = null;

    public Reactor()
    {
    }

    public void setMC(String address, Integer port)
    {
        MCaddress = address;
        MCport = port;
    }

    public void setMDB(String address, Integer port)
    {
        MDBaddress = address;
        MDBport = port;
    }

    public void setMDR(String address, Integer port)
    {
        MDRaddress = address;
        MDRport = port;
    }

    public void run() throws IOException
    {
        if (MCaddress != null && MCport != null) {
            MCReactor MCReactor = new MCReactor(MCaddress, MCport);
            MCReactor.run();
        }

        if (MDBaddress != null && MDBport != null) {
            MDBReactor MDBReactor = new MDBReactor(MDBaddress, MDBport);
            MDBReactor.run();
        }
    }
}
