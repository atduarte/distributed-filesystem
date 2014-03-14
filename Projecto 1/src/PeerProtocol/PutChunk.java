package PeerProtocol;

import java.util.regex.Pattern;

/**
 * Created by atduarte on 13-03-2014.
 */
public class PutChunk  extends Thread
{
    final public static Pattern pattern = Pattern.compile("^PUTCHUNK");

    public void run()
    {
        // TODO: Process Message
        System.out.println("Delete");
    }
}
