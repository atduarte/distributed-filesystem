package Reactors;

import Peer.DependencyInjection;
import Reactions.*;
import Reactions.Normal.Delete;
import Utils.Channels;

import java.net.DatagramPacket;

/**
 * Created by atduarte on 15-03-2014.
 */
public class MCReactor extends ChannelReactor
{
	public MCReactor(DependencyInjection di) {
		super(di);

        Channels channels = di.getChannels();
        this.address = channels.getMC().getAddress();
        this.port = channels.getMC().getPort();
	}

	protected void processMessage(DatagramPacket packet, byte[] data)
    {
        String message = new String(data);

        // Enhancements
        if (Reactions.Enhanced.GetChunk.pattern.matcher(message).find()) {
            Reactions.Enhanced.GetChunk thread = new Reactions.Enhanced.GetChunk(di, packet, data);
            thread.start();
            System.out.println("MCReceived: Enhanced GetChunk");

        // Normal
        } else if(Reactions.Normal.Delete.pattern.matcher(message).find()) {
            System.out.println("MDBReceived: Delete");
            Reactions.Normal.Delete thread = new Reactions.Normal.Delete(di, data);
            thread.start();

        } else if(Reactions.Normal.GetChunk.pattern.matcher(message).find()) {
            System.out.println("MCReceived: GetChunk");
            Reactions.Normal.GetChunk thread = new Reactions.Normal.GetChunk(di, data);
            thread.start();

        } else if(Reactions.Normal.Stored.pattern.matcher(message).find()) {
            System.out.println("MCReceived:: Stored");
            Reactions.Normal.Stored thread = new Reactions.Normal.Stored(di, data);
            thread.start();

        } else if(Reactions.Normal.Removed.pattern.matcher(message).find()) {
            System.out.println("MCReceived: Removed");
            Reactions.Normal.Removed thread = new Reactions.Normal.Removed(di, data);
            thread.start();

        // Error
        } else {
            System.out.println("Error on MCReactor: " + message);
        }
	}
}
