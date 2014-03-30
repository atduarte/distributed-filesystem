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



        /*while(this.getFolderSize(chunksFolder) > backupInfo.getUsedDiskSpace() || this.getFolderSize(chunksFolder) == 0) */
            String fileid = chunkManager.getRandomFolder();
            int chunkNo = chunkManager.getRandomChunkNo(fileid);
            System.out.println(fileid + " " + chunkNo);
            Removed rm = new Removed(di,new File(fileid).getName(),chunkNo);
            try {
                File f1 = new File(chunkManager.getChunksPath()+"\\"+new File(fileid).getName()+"\\"+chunkNo);
                System.out.println("Removed: " + f1.delete());
                rm.run();
            } catch (IOException e) {
                e.printStackTrace();
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
