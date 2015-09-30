package Utils;

/**
 * Created by atduarte on 18-03-2014.
 */
public class Channel
{
    String address = null;
    Integer port = null;

    public Channel(String address, Integer port)
    {
        this.address = address;
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

}
