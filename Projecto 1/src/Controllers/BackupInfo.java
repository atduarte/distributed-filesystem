package Controllers;

import java.util.ArrayList;

/**
 * Created by atduarte on 13-03-2014.
 */
public class BackupInfo
{
    static ArrayList<BackupFileInfo> files;

    BackupInfo() {
        files = new ArrayList<BackupFileInfo>();
    }

    public void addFile(BackupFileInfo file) {
        files.add(file);
    }
}
