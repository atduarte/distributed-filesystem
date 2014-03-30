package Reactions.Normal;

import Peer.BackupFileInfo;
import Peer.DependencyInjection;
import Reactions.Reaction;
import Server.Protocol.Normal.GetChunk;
import Server.Protocol.Normal.PutChunk;
import Utils.Constants;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Removed extends Reaction
{
    final public static Pattern pattern = Pattern.compile(
            "^REMOVED "
            + Constants.version + " "
            + Constants.patternFileId + " "
            + Constants.patternChunkNo
    );

    public Removed(DependencyInjection di, byte[] data) {
        super(di, data);
        this.version = Constants.version;
    }

    public void decodeData()
    {
        Matcher matches = pattern.matcher(new String(this.data));

        if (matches.find()) {
            this.fileId = matches.group(1);
            this.chunkNo = Integer.parseInt(matches.group(2));
        }
    }

    public void run()
    {
        // Verificar se é meu
        if (di.getBackupInfo().isMine(fileId)) {
            // Diminuir replication degree
            di.getBackupInfo().getFile(fileId).decrementRealReplicationDegree(chunkNo);

            System.out.println("PROCESSED Removed");

            // Verificar replication degrees
            BackupFileInfo file = di.getBackupInfo().getFile(fileId);
            if(file.getRealReplicationDegree(chunkNo)< file.getReplicationDegree())
            {
                // TODO: Random Delay 0 - 400ms

                // Get Body
                // TODO: E se nao houver na rede?
                Server.Protocol.Normal.GetChunk getChunk = new GetChunk(di, fileId, chunkNo);
                byte[] data = getChunk.run();

                PutChunk putChunk = new PutChunk(di, fileId, chunkNo, file.getReplicationDegree(), data);
                try {
                    putChunk.run();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


    }
}
