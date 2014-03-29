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

    public long getFolderSize(File folder)
    {

        long size = 0;
        for (File file : folder.listFiles()) {
            if (file.isFile()) {

                size += file.length();
            } else
                size += getFolderSize(file);
        }
        return size;
    }





}
