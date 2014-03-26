package Reactors;

import Peer.DependencyInjection;
import Reactions.Delete;
import Reactions.GetChunk;
import Reactions.Removed;
import Reactions.Stored;
import Utils.Channels;

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

	protected void processMessage(byte[] data, String message) {
        if(Delete.pattern.matcher(message).find()) {
            System.out.println("MDBReceived: Delete");
            Delete thread = new Delete(di, data);
            thread.start();
        } else if(GetChunk.pattern.matcher(message).find()) {
			System.out.println("MCReceived: GetChunk");
		    GetChunk thread = new GetChunk(di, data);
		    thread.start();
		} else if(Stored.pattern.matcher(message).find()) {
			System.out.println("MCReceived: Stored");
		    Stored thread = new Stored(di, data);
		    thread.start();
		} else if(Removed.pattern.matcher(message).find()) {
			System.out.println("MCReceived: Removed");
		    Removed thread = new Removed(di, data);
		    thread.start();
		} else {
		    System.out.println("Error on MCReactor: " + message);
		}
	}
}
