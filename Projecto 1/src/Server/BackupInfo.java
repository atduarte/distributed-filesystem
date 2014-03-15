package Server;

import java.util.ArrayList;

/**
 * Created by atduarte on 13-03-2014.
 */
public class BackupInfo
{
    String path;
    ArrayList<BackupFileInfo> files;
    Integer usedDiskSpace;

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
