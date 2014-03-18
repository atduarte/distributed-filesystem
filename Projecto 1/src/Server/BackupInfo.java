package Server;

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

    public boolean save()
    {
        // TODO

        return false;
    }
}
