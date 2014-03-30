package Peer;

/**
 * Created by atduarte on 30-03-2014.
 */
public class ChunkInfo
{
    public String fileId;
    public Integer chunkNo;
    public Integer repDegree;
    public Integer realRepDegree;
    public boolean startPutChunk = false;

    public ChunkInfo(String fileId, Integer chunkNo, Integer repDegree, Integer realRepDegree)
    {
        this.fileId = fileId;
        this.chunkNo = chunkNo;
        this.repDegree = repDegree;
        this.realRepDegree = realRepDegree;
    }

    public boolean is(String fileId, Integer chunkNo)
    {
        return this.fileId.equals(fileId) && this.chunkNo.equals(chunkNo);
    }
}
