package Reactions;

import Peer.BackupFileInfo;
import Peer.DependencyInjection;
import Server.Protocol.*;
import Utils.Constants;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Removed extends Reaction
{
    final public static Pattern pattern = Pattern.compile(
            "^REMOVED "
            + Constants.patternVersion
            + Constants.patternFileId
            + Constants.patternChunkNo
    );

    public Removed(DependencyInjection di, byte[] data) {
        super(di, data);
    }

    public void decodeData()
    {
        Matcher matches = pattern.matcher(new String(this.data));

        if (matches.find()) {
            this.version = matches.group(1);
            this.fileId = matches.group(2);
            this.chunkNo = Integer.parseInt(matches.group(3));
        }
    }

    public void run()
    {
        // Verificar se é meu
        if (di.getBackupInfo().isMine(fileId)) {
            // Diminuir replication degree
            di.getBackupInfo().getFile(fileId).decrementRealReplicationDegree(chunkNo);

            System.out.println("PROCESSED Removed");

            // Verificar replication degree
            BackupFileInfo file = di.getBackupInfo().getFile(fileId);
            if(file.getRealReplicationDegree(chunkNo)< file.getReplicationDegree())
            {
                // Get Body
                // TODO: E se nao houver na rede?
                Server.Protocol.GetChunk getChunk = new Server.Protocol.GetChunk(di, fileId, chunkNo);
                byte[] data = getChunk.run();

                Server.Protocol.PutChunk putChunk = new Server.Protocol.PutChunk(di, fileId, chunkNo, file.getReplicationDegree(), data);
                try {
                    putChunk.run();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


    }
}
