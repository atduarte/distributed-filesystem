package Peer;

public class ChunkManager
{
    String chunksPath;

    public ChunkManager(String _chunksPath)
    {
        chunksPath = _chunksPath;
    }

    public boolean hasChunk(String fileId, Integer chunkNo) {
        // Return true if has Chunk
        return true;
    }

    public void deleteChunk(String fileId, Integer chunkNo) {

    }

    public void deleteFile(String fileId) {

    }

    public void addChunk(String fileId, Integer chunkNo, byte[] data) {

    }

    public byte[] getChunk(String fileId, Integer chunkNo) {
        return new byte[0];
    }


}
