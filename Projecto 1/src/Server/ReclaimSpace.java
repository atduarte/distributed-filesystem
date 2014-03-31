package Server;

import Peer.*;
import Server.Protocol.Normal.Removed;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

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

        ArrayList<File> chunkFiles = chunkManager.getChunksList();

        long a = chunkManager.getFolderSize();
        long b = backupInfo.getUsedDiskSpace();

        while(chunkManager.getFolderSize() > backupInfo.getUsedDiskSpace()) {
            if (chunkFiles == null || chunkFiles.size() == 0) {
                break;
            }

            int i = (new Random()).nextInt(chunkFiles.size());
            File chunk = chunkFiles.get(i);

            ChunkInfo chunkInfo = chunkManager.getChunkInfo(chunk.getParentFile().getName(), Integer.parseInt(chunk.getName()));

            if (chunkInfo != null &&
                chunkInfo.repDegree <= 1)
            {
                chunkFiles.remove(i);
                continue;
            }

            Removed rm = new Removed(di, chunk.getParentFile().getName(), Integer.parseInt(chunk.getName()));
            try {
                chunkFiles.remove(i);
                chunk.delete();
                rm.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
