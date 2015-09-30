package Server;

import Peer.BackupFileInfo;
import Peer.DependencyInjection;
import Peer.Injectable;
import Server.Protocol.Normal.GetChunk;
import Utils.Constants;

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

    public void receiveFile(String s) throws IOException
    {
        BackupFileInfo bck = di.getBackupInfo().getFilebyName(s);
        String fileid = bck.getHash();
        long numChunks = bck.getChunkNo();

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(s);
        } catch (FileNotFoundException e) {
            return;
        }

        System.out.println("ChunkNo: " + numChunks);
        for (int i = 0; i < numChunks; i++) {
            byte [] body = null;


            if (Constants.enableEnhancements) {
                Server.Protocol.Enhanced.GetChunk getchunk = new Server.Protocol.Enhanced.GetChunk(di, fileid, i+1);
                body = getchunk.run();
            } else {
                for (int j = 0; j < 5; j++) {
                    Server.Protocol.Normal.GetChunk getchunk = new Server.Protocol.Normal.GetChunk(di, fileid, i+1);
                    try {
                    body = getchunk.run();
                    } catch (IOException e) {
                        body = null;
                    }

                    if(body != null) {
                        break;
                    }
                }
            }

            out.write(body);
        }

        try {
            out.close();
        } catch (IOException ignored) {
        }
    }
}
