package Controllers;

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

    public void sendFolder(String path) throws IOException {
        ArrayList<File> listOfFiles = filesManager.getFilesList(path);

        for (int i = 0; i < listOfFiles.size(); i++) {
            sendFile(listOfFiles.get(i), path);
        }

        // TODO: Save Hashes
    }

    public void sendFile(File file, String path) throws IOException {
        filesManager.generateHash(path, file);
        String chunksFolder = filesManager.saveChunks(file);

        // TODO: Envias a tua cena

        // TODO: Remove chunk folder

        // TODO: Save Hash
    }
}
