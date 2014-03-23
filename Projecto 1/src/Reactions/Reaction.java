package Reactions;

import Controllers.DependencyInjection;
import Controllers.InjectableThread;
import Peer.BackupInfo;
import Utils.Channels;

public class Reaction extends InjectableThread
{
    protected byte[] data;

    protected String version;
    protected String fileId;
    protected Integer chunkNo;
    protected Integer replicationDegree;
    protected byte[] body;

	public Reaction(DependencyInjection di, byte[] data)
    {
        super(di);
        this.data = data;
        decodeData();
    }

	public void decodeData()
    {

	}
}
