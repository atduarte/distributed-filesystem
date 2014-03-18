package PeerProtocol;

import java.util.regex.Pattern;

/**
 * Created by atduarte on 13-03-2014.
 */
public class GetChunk extends Thread
{
    final public static Pattern pattern = Pattern.compile("^GETCHUNK");
    private byte[] data;

    public GetChunk(byte[] data)
    {
        this.data = data;
    }

    public void run()
    {
        // TODO: Process Message
        System.out.println("Delete");
    }
}
