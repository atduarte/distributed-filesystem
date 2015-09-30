package Server;

import Peer.BackupFileInfo;
import Peer.BackupInfo;
import Peer.DependencyInjection;
import Peer.Injectable;
import Server.Protocol.Normal.PutChunk;
import Utils.FilesManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by atduarte on 13-03-2014.
 */
public class Backup extends Injectable
{
    FilesManager filesManager = new FilesManager();

    public Backup(DependencyInjection di)
    {
        super(di);
        filesManager = new FilesManager();
    }

   /* public void sendFolder(String path) throws IOException
    {
        ArrayList<File> listOfFiles = filesManager.getFilesList(path);

        for (int i = 0; i < listOfFiles.size(); i++) {
            sendFile(listOfFiles.get(i), path);
        }

    }
*/
    public void sendFile(File file, Integer replicationDegree) throws IOException
    {
        String hash = filesManager.generateHash(file);
        File chunksFolder = filesManager.saveChunks(file);

        if (chunksFolder == null || !chunksFolder.isDirectory()){
            throw new IOException();
        }

        // Get Chunks List
        File[] chunks = chunksFolder.listFiles();
        if (chunks == null) {
            throw new IOException();
        }

        // Save Hash
        BackupInfo backupInfo = di.getBackupInfo();
        BackupFileInfo newFile = new BackupFileInfo();
        newFile.setHash(hash);
        newFile.setName(file.getAbsolutePath());
        newFile.setNumChunks(file.length());

        backupInfo.addFile(newFile);
        backupInfo.save();

        // Send Each Chunk
        for (File chunk : chunks) {
            if (chunk.isFile()) { //this line weeds out other directories/folders
                FileInputStream is = new FileInputStream(chunk);

                // Read File to Data
                byte[] data = new byte[(int)chunk.length()];
                is.read(data);
                is.close();

                // Send Chunk
                Integer chunkNo = Integer.parseInt(chunk.getName());
                PutChunk putChunk = new PutChunk(di, hash, chunkNo, replicationDegree, data);
                if (!putChunk.run()) {
                    throw new IOException();
                }
            }
        }

        // Remove chunk folder
        FilesManager.deleteDirectory(chunksFolder);

    }
}
