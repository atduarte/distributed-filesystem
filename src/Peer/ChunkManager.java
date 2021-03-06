package Peer;

import Server.Protocol.Normal.Delete;
import Server.Protocol.Normal.Removed;
import Utils.Constants;
import Utils.FilesManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ChunkManager
{
    String chunksPath;
    ArrayList<ChunkInfo> chunksInfo = new ArrayList<ChunkInfo>();

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

    public void setChunksPath(String chunksPath)
    {
        this.chunksPath = chunksPath;
    }

    public boolean hasChunk(String fileId, Integer chunkNo)
    {
        String personalChunksFile = chunksPath+File.separator+fileId+File.separator+chunkNo;
        File saveChunk = new File(personalChunksFile);
        return saveChunk.exists();
    }

    public ArrayList<File> getChunksList()
    {
        File folder = new File(chunksPath);
        ArrayList<File> files = listFiles(folder);

        if (files == null || files.size() == 0) {
            return null;
        }

        return files;
    }

    private ArrayList<File> listFiles(File file) {
        File[] files = file.listFiles();

        if (files == null) {
            return null;
        }

        ArrayList<File> children = new ArrayList<File>(Arrays.asList(files));
        int n = children.size();
        for (int i = 0; i < n; i++) {
            File child = children.get(i);
            ArrayList<File> tmpChildren = listFiles(child);
            if (tmpChildren != null) {
                for (File cenas : tmpChildren) {
                    children.add(cenas);
                }
            }
        }

        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).isDirectory()) {
                children.remove(i);
                i--;
            }
        }

        return children;
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
        this.deleteFileInfo(fileId);

        String path = chunksPath+File.separator+fileId;
        File file = new File(path);
        FilesManager.deleteDirectory(file);
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

    public void addChunkInfo(ChunkInfo info)
    {
        chunksInfo.add(info);
    }

    public void resetChunkInfo(String fileId, Integer chunkNo, Integer replicationDegree)
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

    public ChunkInfo getChunkInfo(String fileId, Integer chunkNo)
    {
        for (ChunkInfo info : chunksInfo) {
            if (info.is(fileId, chunkNo)) {
                return info;
            }
        }

        return null;
    }

    public void deleteFileInfo(String fileId) {
        for (int i = 0; i < chunksInfo.size(); i++)
        {
            if (chunksInfo.get(i).fileId.equals(fileId)) {
                chunksInfo.remove(i);
                i--;
            }
        }
    }

    public long getFolderSize() {
        return FilesManager.getFolderSize(new File(chunksPath)) / 1024;
    }
}
