package Controllers;

import Utils.FilesManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by atduarte on 13-03-2014.
 */
public class Main
{
    public static void main(String [] args) throws IOException {
        System.out.println(args[0]);

        FilesManager mainFolder = new FilesManager();
        ArrayList<File> listOfFiles = mainFolder.getFilesList(args[0]);

        System.out.println(listOfFiles.toString());

        for (int i = 0; i < listOfFiles.size(); i++) {
            mainFolder.generateHash(args[0], listOfFiles.get(i));
            String chunksFolder = mainFolder.saveChunks(listOfFiles.get(i));

            // Envias a tua cena

            // Eliminas a pasta
        }

    }
}
