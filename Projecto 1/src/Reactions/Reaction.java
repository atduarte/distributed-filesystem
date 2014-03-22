package Reactions;

import Peer.BackupInfo;
import Utils.Channels;

public class Reaction extends Thread
{
	protected Channels channels;
	protected BackupInfo backupInfo;
    protected byte[] data;

    protected String version;
    protected String fileId;
    protected Integer chunkNo;
    protected Integer replicationDegree;
    protected byte[] body;

	public Reaction(Channels channels, BackupInfo backupInfo, byte[] data)
    {
    	this.channels = channels;
    	this.backupInfo = backupInfo;
        this.data = data;
        decodeData();
    }

	public void decodeData() {

	}

}
