package PeerProtocol;

import java.util.regex.Pattern;

/**
 * Created by atduarte on 13-03-2014.
 */
public class Delete extends Thread
{
    final public static Pattern pattern = Pattern.compile("^DELETE");

    public void run()
    {
        // TODO: Process Message
        System.out.println("Delete");
    }
}
