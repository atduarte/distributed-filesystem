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

    public void addFile(BackupFileInfo file) {
        files.add(file);
    }

    public boolean isMine(String fileId)
    {
    	for (int i = 0; i < files.size(); i++) {
    		BackupFileInfo file = files.get(i);
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
