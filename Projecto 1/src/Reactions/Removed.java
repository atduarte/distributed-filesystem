package Reactions;

import Peer.DependencyInjection;
import Utils.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Removed extends Reaction
{
    final public static Pattern pattern = Pattern.compile(
            "^REMOVED"
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
        System.out.println("Removed");

        // TODO: Verificar se é meu
        if(di.getBackupInfo().isMine(fileId))
        {
            // TODO: Diminuir replication degree
            di.getBackupInfo().getFile(fileId).decrementRealReplicationDegree(chunkNo);


            // TODO: Verificar replication degree

            if(di.getBackupInfo().getFile(fileId).getRealReplicationDegree(chunkNo)< di.getBackupInfo().getFile(fileId).getReplicationDegree())
            {
                // TODO: Se for menor que o necessário, iniciar Putchunk
                PutChunk thread = new PutChunk(di, data);
                thread.start();
            }
        }
        else
        {
            return;
        }
        // Se não é, return


    }
}
