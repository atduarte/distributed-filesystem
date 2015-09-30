package Reactors;

import Peer.DependencyInjection;
import Utils.Channels;

import java.net.DatagramPacket;

public class MDBReactor extends ChannelReactor
{
    public MDBReactor(DependencyInjection di) {
        super(di);

        Channels channels = di.getChannels();
        this.address = channels.getMDB().getAddress();
        this.port = channels.getMDB().getPort();
    }

	protected void processMessage(DatagramPacket packet, byte[] data)
    {
        String message = new String(data);

        // Normal
        if(Reactions.Normal.PutChunk.pattern.matcher(message).find()) {
            System.out.println("MDBReceived: PutChunk");
            Reactions.Normal.PutChunk thread = new Reactions.Normal.PutChunk(di, data);
            thread.start();

        // Error
        } else {
            System.out.println("Error on MDBReactor: " + message);
        }
	}
}
