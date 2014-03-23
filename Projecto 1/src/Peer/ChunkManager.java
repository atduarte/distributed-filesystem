package Peer;

import Utils.Constants;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ChunkManager
{
    String chunksPath;

    public ChunkManager(String _chunksPath)
    {
        chunksPath = _chunksPath;
    }

    public boolean hasChunk(String fileId, Integer chunkNo) {


        String personalchunksFile = chunksPath+File.separator+fileId+File.separator+chunkNo;
        File saveChunk = new File(personalchunksFile);
        if(saveChunk.exists())
        {
            return true;
        }

        return false;
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


    private boolean deleteDirectory(File path) {
        if( path.exists() ) {
            File[] files = path.listFiles();
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                }
                else {
                    files[i].delete();
                }
            }
        }
        return( path.delete() );
    }

    public void addChunk(String fileId, Integer chunkNo, byte[] data) throws IOException {
        String personalChunksPath = chunksPath + File.separator + fileId;
        File saveDir = new File(personalChunksPath);

        if(!saveDir.exists()) {
            saveDir.mkdir();
        }

        Path path = Paths.get(personalChunksPath + "/" + chunkNo);
        Files.write(path, data);
    }

    public byte[] getChunk(String fileId, Integer chunkNo) {

        byte[] chunk = new byte[Constants.chunkSize];
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
                    out.read(chunk);
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }





        }
        return chunk;

    }



}
