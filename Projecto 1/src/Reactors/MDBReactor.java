package Reactors;

import Peer.DependencyInjection;
import Utils.Channels;

public class MDBReactor extends ChannelReactor
{
    public MDBReactor(DependencyInjection di) {
        super(di);

        Channels channels = di.getChannels();
        this.address = channels.getMDB().getAddress();
        this.port = channels.getMDB().getPort();
    }

	protected void processMessage(byte[] data, String message)
    {
        // Enhancements
		if(Reactions.Enhanced.PutChunk.pattern.matcher(message).find()) {
        	System.out.println("MDBReceived: PutChunk");
            Reactions.Enhanced.PutChunk thread = new Reactions.Enhanced.PutChunk(di, data);
            thread.start();

        // Normal
        } else if(Reactions.Normal.PutChunk.pattern.matcher(message).find()) {
            System.out.println("MDBReceived: PutChunk");
            Reactions.Normal.PutChunk thread = new Reactions.Normal.PutChunk(di, data);
            thread.start();

        // Error
        } else {
            System.out.println("Error on MDBReactor: " + message);
        }
	}
}
