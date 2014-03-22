package Peer;

import java.util.ArrayList;

/**
 * Created by atduarte on 13-03-2014.
 */
public class BackupInfo
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

    public BackupFileInfo getFile(String fileId) {
        for (BackupFileInfo file : files) {
            if (file.getHash().equals(fileId)) {
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
        // TODO

        return false;
    }
}
