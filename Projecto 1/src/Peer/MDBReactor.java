package Peer;

import PeerProtocol.GetChunk;
import PeerProtocol.PutChunk;
import PeerProtocol.Removed;
import Server.BackupInfo;
import ServerProtocol.Stored;
import Utils.Channel;
import Utils.Channels;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class MDBReactor extends ChannelReactor
{
	public MDBReactor(Channels channels, BackupInfo backupInfo) 	throws IOException {
		super(channels, backupInfo);

        this.address = channels.getMDB().getAddress();
        this.port = channels.getMDB	().getPort();
	}

	protected void processMessage(byte[] data, String message) {
		/*if(Delete.pattern.matcher(message).find()) {
        	System.out.println("MDBReceived: Delete");
            Delete thread = new Delete(data);
            thread.start();
        } else */
		if(PutChunk.pattern.matcher(message).find()) {
        	System.out.println("MDBReceived: PutChunk");
            PutChunk thread = new PutChunk(channels, backupInfo, data);
            thread.start();
        } else {
            System.out.println("Error on MDBReactor: " + message);
        }
	}   
}
