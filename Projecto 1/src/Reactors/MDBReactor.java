package Reactors;

import Peer.DependencyInjection;
import Reactions.Delete;
import Reactions.PutChunk;
import Utils.Channels;

public class MDBReactor extends ChannelReactor
{
    public MDBReactor(DependencyInjection di) {
        super(di);

        Channels channels = di.getChannels();
        this.address = channels.getMDB().getAddress();
        this.port = channels.getMDB().getPort();
    }

	protected void processMessage(byte[] data, String message) {
		if(Delete.pattern.matcher(message).find()) {
        	System.out.println("MDBReceived: Delete");
            Delete thread = new Delete(di, data);
            thread.start();
        } else if(PutChunk.pattern.matcher(message).find()) {
        	System.out.println("MDBReceived: PutChunk");
            PutChunk thread = new PutChunk(di, data);
            thread.start();
        } else {
            System.out.println("Error on MDBReactor: " + message);
        }
	}
}
