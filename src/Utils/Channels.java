package Utils;

/**
 * Created by atduarte on 18-03-2014.
 */
public class Channels
{
    Channel MC;
    Channel MDB;
    Channel MDR;

    public void setMC(String address, Integer port)
    {
        MC = new Channel(address, port);
    }

    public void setMDB(String address, Integer port)
    {
        MDB = new Channel(address, port);
    }

    public void setMDR(String address, Integer port)
    {
        MDR = new Channel(address, port);
    }

    public Channel getMC() {
        return MC;
    }

    public Channel getMDB() {
        return MDB;
    }

    public Channel getMDR() {
        return MDR;
    }
}
