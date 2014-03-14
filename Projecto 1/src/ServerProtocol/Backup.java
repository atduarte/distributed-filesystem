package ServerProtocol;

import Controllers.BackupInfo;
import Utils.FilesManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by atduarte on 13-03-2014.
 */
public class Backup
{
    FilesManager filesManager = new FilesManager();
    BackupInfo backupInfo = null;

    public Backup(BackupInfo _backupInfo)
    {
        filesManager = new FilesManager();
        backupInfo = _backupInfo;
    }

    public void sendFolder(String path) throws IOException
    {
        ArrayList<File> listOfFiles = filesManager.getFilesList(path);

        for (int i = 0; i < listOfFiles.size(); i++) {
            sendFile(listOfFiles.get(i), path);
        }

        // TODO: Save Hashes
    }

    public void sendFile(File file, String path) throws IOException
    {
        String hash = filesManager.generateHash(path, file);
        String chunksFolder = filesManager.saveChunks(file);

        // TODO: Envias a tua cena

        // TODO: Remove chunk folder

        // TODO: Save Hash
    }
}
