package Utils;

/**
 * Created by atduarte on 13-03-2014.
 */
public class Constants {
    public final static String version = "1.0";
    public final static Integer chunkSize = 64*1000;
    public final static String patternVersion = "(\\d\\.\\d) ";
    public final static String patternFileId = "(\\w+) ";
    public final static String patternChunkNo = "(\\d+) ";
    public final static String patternReplicationDeg = "(\\d+) ";

    public static byte[] getBodyFromMessage(byte[] message)
    {
        String sMessage = new String(message);
        String separator = "\r\n \r\n ";
        int i = sMessage.indexOf(separator) + separator.length();
        return sMessage.substring(i, (i-1)+chunkSize).getBytes();
    }


    public static String getNElementFromMessage(byte[] message,int n)
    {
        String sMessage = new String(message);
        String separator = " ";

        for(int i = 0; i < n; i++)
        {
            int index = sMessage.indexOf(separator) + separator.length();
            sMessage = sMessage.substring(index);
        }
        int index = sMessage.indexOf(separator) + separator.length();

        return sMessage.substring(0, index-separator.length());

    }


}
