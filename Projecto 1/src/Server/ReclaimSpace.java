package Server;

import Peer.BackupInfo;
import Peer.ChunkManager;
import Peer.DependencyInjection;
import Peer.Injectable;
import Server.Protocol.Normal.Removed;

import java.io.File;
import java.io.IOException;

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

        while(chunkManager.getFolderSize() > backupInfo.getUsedDiskSpace() || chunkManager.getFolderSize() == 0) {
            String fileid = new String();
            int chunkNo = 0;
            while(true) {
                 fileid = chunkManager.getRandomFolder();
                 chunkNo = chunkManager.getRandomChunkNo(fileid);
                System.out.println(fileid + " " + chunkNo);
                if(chunkManager.getChunkInfo(new File(fileid).getName(),chunkNo).repDegree>1)
                {
                    break;
                }
            }
            Removed rm = new Removed(di,new File(fileid).getName(),chunkNo);
            try {
                File f1 = new File(chunkManager.getChunksPath()+"\\"+new File(fileid).getName()+"\\"+chunkNo);
                System.out.println("Removed: " + f1.delete());
                rm.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
