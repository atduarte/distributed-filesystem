package Peer;

import java.io.*;
import java.util.ArrayList;


public class BackupInfo implements Serializable
{
    public String path;

    public ArrayList<BackupFileInfo> getFiles() {
        return files;
    }

    public ArrayList<BackupFileInfo> files;
    public Integer usedDiskSpace;



    public BackupInfo(String path) {
        this.path = path;
        files = new ArrayList<BackupFileInfo>();
    }

    public void addFile(BackupFileInfo newFile)
    {
        for (int i = 0; i < files.size(); i++) {
            if (files.get(i).getHash().equals(newFile.getHash()) ||
                files.get(i).getName().equalsIgnoreCase(newFile.getName()))
            {
                files.remove(i);
                i--;
            }
        }

        // Add
        files.add(newFile);
    }
/*
    public boolean incrementRealRepDegree(String fileId, Integer chunkNo) {
        BackupFileInfo file = this.getFile(fileId);

        if (file == null) {
            return false;
        }

        file.incrementRealReplicationDegree(chunkNo);

        return true;
    }
*/
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
        // Create Dir if doesn't exist
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        try {
            FileOutputStream fileOut = new FileOutputStream(path + "\\backupInfo.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in " + path + "\\backupInfo.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }

        return false;
    }
}
