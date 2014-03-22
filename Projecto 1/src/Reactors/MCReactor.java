package Reactors;

import Reactions.GetChunk;
import Reactions.Removed;
import Peer.BackupInfo;
import Reactions.Stored;
import Utils.Channels;

import java.io.IOException;

/**
 * Created by atduarte on 15-03-2014.
 */
public class MCReactor extends ChannelReactor
{
	public MCReactor(Channels channels, BackupInfo backupInfo) 	throws IOException {
		super(channels, backupInfo);

        this.address = channels.getMC().getAddress();
        this.port = channels.getMC().getPort();
	}

	protected void processMessage(byte[] data, String message) {
		if(GetChunk.pattern.matcher(message).find()) {
			System.out.println("MCReceived: GetChunk");
		    GetChunk thread = new GetChunk(channels, backupInfo, data);
		    thread.start();
		} else if(Stored.pattern.matcher(message).find()) {
			System.out.println("MCReceived: Stored");
		    Stored thread = new Stored(channels, backupInfo, data);
		    thread.start();
		} else if(Removed.pattern.matcher(message).find()) {
			System.out.println("MCReceived: Removed");
		    Removed thread = new Removed(channels, backupInfo, data);
		    thread.start();
		} else {
		    System.out.println("Error on MCReactor: " + message);
		}
	}
}
