package Server;

import Peer.BackupInfo;
import Peer.ChunkManager;
import Peer.DependencyInjection;
import Peer.Injectable;

import java.io.File;

public class ReclaimSpace extends Injectable
{
    public ReclaimSpace(DependencyInjection di)
    {
        super(di);
    }

    public void run()
    {
        BackupInfo backupInfo = di.getBackupInfo();
        ChunkManager chunkManager = di.getChunkManager();
        File chunksFolder = new File(chunkManager.getChunksPath());

        while(this.getFolderSize(chunksFolder) > backupInfo.getUsedDiskSpace() || this.getFolderSize(chunksFolder) == 0) {
            chunkManager.deleteRandomChunk();
        }
    }

    private Integer getFolderSize(File folder)
    {
        // TO DO
        return 0;
    }
}
