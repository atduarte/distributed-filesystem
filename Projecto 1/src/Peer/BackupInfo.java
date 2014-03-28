package Peer;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by atduarte on 13-03-2014.
 */
public class BackupInfo implements Serializable
{
    public String path;
    public ArrayList<BackupFileInfo> files;
    public Integer usedDiskSpace;

    public BackupInfo(String path) {
        this.path = path;
        files = new ArrayList<BackupFileInfo>();

        // TODO: Unserialize Data from path
        // TODO: If Data doesn't exist create
    }

    public void addFile(BackupFileInfo newFile) {
        String fileId = newFile.getHash();
        for (int i = 0; i < files.size(); i++) {
            if (files.get(i).getHash().equals(fileId)) {
                // Replace
                files.set(i, newFile);
                return;
            }
        }

        // or Add
        files.add(newFile);

    }

    public boolean incrementRealRepDegree(String fileId, Integer chunkNo) {
        BackupFileInfo file = this.getFile(fileId);

        if (file == null) {
            return false;
        }

        file.incrementRealReplicationDegree(chunkNo);

        return true;
    }

    public Integer getUsedDiskSpace() {
        return usedDiskSpace;
    }

    public void setUsedDiskSpace(Integer usedDiskSpace) {
        this.usedDiskSpace = usedDiskSpace;
    }

    public String getPath()
    {
        return this.path;
    }

    public BackupFileInfo getFile(String fileId) {
        for (BackupFileInfo file : files) {
            if (file.getHash().equals(fileId)) {
                return file;
            }
        }

        return null;
    }


    public BackupFileInfo getFilebyName(String name) {
        for (BackupFileInfo file : files) {
            if (file.getName().equals(name)) {
                return file;
            }
        }

        return null;
    }
    public boolean isMine(String fileId)
    {
        for (BackupFileInfo file : files) {
            if (file.getHash().equals(fileId)) {
                return true;
            }
        }
    	return false;
    }

    public boolean save()
    {
        try
        {
            FileOutputStream fileOut = new FileOutputStream(path + "\\backupInfo.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in " + path + "\\backupInfo.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }

        return false;
    }
}
