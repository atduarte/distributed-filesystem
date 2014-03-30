package Peer;

import java.util.ArrayList;

public class ChunksInfo
{
    ArrayList<ChunkInfo> chunksInfo = new ArrayList<ChunkInfo>();

    public ChunksInfo()
    {

    }

    public void addChunksInfo(ChunkInfo info)
    {
        chunksInfo.add(info);
    }

    public void resetChunk(String fileId, Integer chunkNo, Integer replicationDegree)
    {
        for (ChunkInfo info : chunksInfo) {
            if (info.is(fileId, chunkNo)) {
                info.realRepDegree = 0;
                info.startPutChunk = false;
                return;
            }
        }

        chunksInfo.add(new ChunkInfo(fileId, chunkNo, replicationDegree, 0));
    }

    public void incrementRealRepDegree(String fileId, Integer chunkNo)
    {
        for (ChunkInfo info : chunksInfo) {
            if (info.is(fileId, chunkNo)) {
                info.realRepDegree++;
                return;
            }
        }

        chunksInfo.add(new ChunkInfo(fileId, chunkNo, 0, 1));
    }

    public ChunkInfo getChunk(String fileId, Integer chunkNo)
    {
        for (ChunkInfo info : chunksInfo) {
            if (info.is(fileId, chunkNo)) {
                return info;
            }
        }

        return null;
    }

    public void deleteFile(String fileId) {
        for (int i = 0; i < chunksInfo.size(); i++)
        {
            if (chunksInfo.get(i).fileId.equals(fileId)) {
                chunksInfo.remove(i);
                i--;
            }
        }
    }
}
