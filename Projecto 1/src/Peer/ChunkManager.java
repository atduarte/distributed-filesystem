package Peer;

import Server.Protocol.Normal.Delete;
import Server.Protocol.Normal.Removed;
import Utils.Constants;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

public class ChunkManager
{
    String chunksPath;

    public ChunkManager(String _chunksPath)
    {
        chunksPath = _chunksPath;

        File dir = new File(chunksPath);
        if(!dir.exists()) {
            dir.mkdirs();
        }
    }

    public String getChunksPath()
    {
        return chunksPath;
    }

    public void setChunksPath(String chunksPath) {
        this.chunksPath = chunksPath;
    }

    public boolean hasChunk(String fileId, Integer chunkNo)
    {
        String personalChunksFile = chunksPath+File.separator+fileId+File.separator+chunkNo;
        File saveChunk = new File(personalChunksFile);
        return saveChunk.exists();
    }

    public File deleteRandomChunk()
    {
        return deleteRandomChunkAux(new File(chunksPath));
    }

    public File deleteRandomChunkAux(File folder)
    {
        int i = folder.listFiles().length;

        Random rn = new Random();
        int n = i;
        int fileorder = rn.nextInt() % n;

        for(int j=0;j<i;j++) {
            if(j==fileorder) {
                int auxi = folder.listFiles()[i].listFiles().length;
                int chunkorder = rn.nextInt() % auxi;
                return folder.listFiles()[i].listFiles()[chunkorder];

            }
        }

        return null;
    }

    public void deleteChunk(String fileId, Integer chunkNo) {

        if(hasChunk(fileId,chunkNo))
        {
            String personalchunksFile = chunksPath+File.separator+fileId+File.separator+chunkNo;
            File saveChunk = new File(personalchunksFile);
            if(saveChunk.exists())
            {
                saveChunk.delete();
            }
        }
    }

    public void deleteFile(String fileId) {
        String path = chunksPath+File.separator+fileId;
        File file = new File(path);
        deleteDirectory(file);
    }

    // TODO: Remover. Repliquei isto para o Files Manager como static, porque dá jeito
    private boolean deleteDirectory(File path) {
        if( path.exists() ) {
            File[] files = path.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        return( path.delete() );
    }

    public void addChunk(String fileId, Integer chunkNo, byte[] data) throws IOException {
        String personalChunksPath = chunksPath + File.separator + fileId;
        File saveDir = new File(personalChunksPath);

        if(!saveDir.exists()) {
            saveDir.mkdirs();
        }

        Path path = Paths.get(personalChunksPath + File.separator + chunkNo);
        Files.write(path, data);
    }

    public byte[] getChunk(String fileId, Integer chunkNo)
    {
        byte[] chunk = null;
        byte[] buffer = new byte[Constants.chunkSize];

        if(hasChunk(fileId,chunkNo))
        {
            FileInputStream out = null;
            try {
                out = new FileInputStream(new File(chunksPath + File.separator + fileId + File.separator + chunkNo));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            if (out != null) {
                try {
                    Integer length = out.read(buffer);
                    chunk = new byte[length];
                    System.arraycopy(buffer, 0, chunk, 0, length);
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return chunk;
    }
}
