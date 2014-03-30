package Reactions.Normal;

import Peer.*;
import Reactions.Reaction;
import Server.Protocol.Normal.PutChunk;
import Utils.Constants;

import java.io.IOException;
import java.util.Random;
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
        ChunkManager chunkManager = di.getChunkManager();

        // Decrement Local Count
        ChunkInfo chunkInfo = chunkManager.getChunkInfo(fileId, chunkNo);
        chunkInfo.realRepDegree--;

        if (chunkManager.hasChunk(fileId, chunkNo) && chunkInfo.realRepDegree < chunkInfo.repDegree) {
            chunkInfo.startPutChunk = true;
            byte[] chunk = chunkManager.getChunk(fileId, chunkNo);

            // Wait Random Delay
            int randWait = (new Random()).nextInt(400);
            try {
                Thread.sleep(randWait);
            } catch (InterruptedException ignored) {
            }

            if (chunkInfo.startPutChunk) {
                PutChunk putChunk = new PutChunk(di, fileId, chunkNo, chunkInfo.repDegree, chunk);
                try {
                    putChunk.run();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                return;
            }
        }
    }
}
