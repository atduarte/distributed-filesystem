package Utils;

import java.io.*;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by atduarte on 13-03-2014.
 */
public class FilesManager
{
    public ArrayList<File> getFilesList(String path)
    {
        File folder = new File(path);
        ArrayList<File> listOfFiles = new ArrayList<File>();
        File[] tmpList = folder.listFiles();

        for (int i = 0; i < tmpList.length; i++) {
            if (tmpList[i].isDirectory()) {
                listOfFiles.addAll(getFilesList(tmpList[i].getPath()));
            } else if (tmpList[i].isFile()) {
                listOfFiles.add(tmpList[i]);
            }
        }

        return listOfFiles;
    }

    public String generateHash(File file) throws UnsupportedEncodingException
    {
        String id = file.getName();

        // Remove First '\'
        if (id.charAt(0) == '\\') {
            id = id.substring(1, id.length());
        }

        // Add Date Modified
        id += "|" + file.lastModified();

        // Add Owner if existent

        String owner = null;
        try {
            owner = java.nio.file.Files.getOwner(file.toPath()).getName();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (owner != null) {
            id += "|" + owner;
        }

        // Add a random number;
        id += "|" + (new Random()).nextInt();

        // System.out.println(id);

        // Hash Id
        // System.out.println(hash(id));

        return hash(id);
    }

    private String hash(String base)
    {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public static boolean deleteDirectory(File path) {
        if( path.exists() ) {
            File[] files = path.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        return( path.delete() );
    }

    public File saveChunks(File file) throws IOException {
        String chunkPath = null;
        File chunksFolder = null;

        // Create ChunksFolder

        do {
            chunkPath = file.getParent() + "\\tmp_" + Math.abs((new Random()).nextInt());
            chunksFolder = new File(chunkPath);
        } while(chunksFolder.exists());

        chunksFolder.mkdir();

        // Save Each Chunk

        FileInputStream is = new FileInputStream(file);
        byte[] chunk = new byte[Constants.chunkSize];
        int chunkNo = 1;
        int chunkLen = 0;

        while ((chunkLen = is.read(chunk)) != -1) {
            byte[] tmpChunk = new byte[chunkLen];
            System.arraycopy(chunk, 0, tmpChunk, 0, chunkLen);
            saveChunk(chunkPath, chunkNo, tmpChunk);
            chunkNo++;
        }

        // Save extra Chunk if last has the exact size
        if (chunkLen == Constants.chunkSize) {
            chunk = new byte[0];
            saveChunk(chunkPath, chunkNo, chunk);
        }

        is.close();

        // Return
        return chunksFolder;
    }

    private boolean saveChunk(String path, int chunkNo, byte[] chunk) throws IOException {
        FileOutputStream out = new FileOutputStream(path + "\\" + chunkNo);
        out.write(chunk);
        out.close();
        return true;
    }


    public static long getFolderSize(File folder) {

        long size = 0;
        for (File file : folder.listFiles()) {
            if (file.isFile()) {

                size += file.length();
            } else
                size += getFolderSize(file);
        }
        return size;
    }
}

