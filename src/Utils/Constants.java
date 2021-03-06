package Utils;

/**
 * Created by atduarte on 13-03-2014.
 */
public class Constants {
    public final static String version = "1.0";
    public final static String enhancedVersion = "1.1";
    public final static boolean enableEnhancements = false;
    public final static Integer chunkSize = 64*1000;
    public final static String patternVersion = "(\\d\\.\\d)";
    public final static String patternFileId = "(\\w+)";
    public final static String patternChunkNo = "(\\d+)";
    public final static String patternReplicationDeg = "(\\d+)";
    public final static String separator = "\r\n\r\n";

    public static byte[] getBodyFromMessage(byte[] message)
    {
        int i = 0;
        int j = 0;
        for(; j < message.length; j++) {
            byte character = message[j];

            if ((i == 0 || i == 2) && character == '\r') {
                i++;
            } else if ((i == 1 || i == 3) && character == '\n') {
                i++;
            } else {
                i = 0;
            }

            if (i == 4)
                break;
        }

        byte[] body = new byte[message.length-j-1];
        System.arraycopy(message, j+1, body, 0, body.length);

        return body;
    }


    public static String getNElementFromMessage(byte[] message,int n)
    {
        String sMessage = new String(message);
        String separator = " ";
        String separator2= Constants.separator;

        // Remove Firsts

        for(int i = 0; i < n; i++)
        {
           int index = sMessage.indexOf(separator) + separator.length();
           if(index == -1){
               index = sMessage.indexOf(separator2) + separator2.length();
           }
           sMessage = sMessage.substring(index);
        }

        // Last

        int index = sMessage.indexOf(separator);
        int index2 = sMessage.indexOf(separator2);

        if(index == -1 || index2 < index) {
            return sMessage.substring(0, index2);
        } else {
            return sMessage.substring(0, index);
        }
    }


}
