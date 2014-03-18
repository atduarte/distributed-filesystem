package PeerProtocol;

import java.util.regex.Pattern;

/**
 * Created by atduarte on 13-03-2014.
 */
public class Removed extends Thread
{
    final public static Pattern pattern = Pattern.compile("^REMOVED");
    private byte[] data;

    public Removed(byte[] data)
    {
        this.data = data;
    }

    public void run()
    {
        // TODO: Process Message
        System.out.println("Removed");
    }
}
