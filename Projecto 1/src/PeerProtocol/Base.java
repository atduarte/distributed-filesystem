package PeerProtocol;

import java.util.regex.Matcher;

import Server.BackupInfo;
import Utils.Channels;

public class Base extends Thread
{
	protected Channels channels;
	protected BackupInfo backupInfo;
    protected byte[] data;
	
	public Base(Channels channels, BackupInfo backupInfo, byte[] data)
    {
    	this.channels = channels;
    	this.backupInfo = backupInfo;
        this.data = data;
        decodeData();
    }
	
	public void decodeData() {
		
	}

}
