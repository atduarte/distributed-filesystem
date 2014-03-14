package Controllers;

import java.util.ArrayList;

/**
 * Created by atduarte on 13-03-2014.
 */
public class BackupInfo
{
    String path;
    ArrayList<BackupFileInfo> files;

    public BackupInfo(String path) {
        this.path = path;
        files = new ArrayList<BackupFileInfo>();
    }

    public void addFile(BackupFileInfo file) {
        files.add(file);
    }
}
