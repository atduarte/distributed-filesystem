package Server;

import Peer.BackupFileInfo;
import Peer.DependencyInjection;
import Peer.Injectable;
import Server.Protocol.Normal.GetChunk;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by atduarte on 14-03-2014.
 */
public class Restore extends Injectable
{
    public Restore(DependencyInjection di)
    {
        super(di);
    }

    public void receiveFile(String s) throws IOException {
        BackupFileInfo bck = di.getBackupInfo().getFilebyName(s);

        String fileid = bck.getHash();

        long numChunks = bck.getChunkNo();
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(s);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("ChunkNo: "+numChunks);
        for(int i=0;i<numChunks;i++)
        {

            GetChunk getchunk = new GetChunk(di, fileid, i+1);
            byte [] body = getchunk.run();
            out.write(body);

        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
