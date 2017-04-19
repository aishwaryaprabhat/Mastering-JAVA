import java.io.*;
import java.net.Socket;

public class Client {

    public final static int SOCKET_PORT = 13267;      // you may change this
    public final static String SERVER = "localhost";  // localhost
    public final static String FILE_TO_SEND = "testlocalhost11";  // you may change this


    public static void main(String[] args) throws IOException {

        Socket sock = null;
        try {
            sock = new Socket(SERVER, SOCKET_PORT);
            System.out.println("Connecting...");
            //send file
            sendfile(sock);
        }finally {
            if (sock != null) sock.close();
        }

    }
    public static void sendfile(Socket sock) throws IOException {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;

        try{
            // send file
            File myFile = new File (FILE_TO_SEND);
            byte [] mybytearray  = new byte [(int)myFile.length()];
            fis = new FileInputStream(myFile);
            bis = new BufferedInputStream(fis);
            bis.read(mybytearray,0,mybytearray.length);
            os = sock.getOutputStream();
            System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
            os.write(mybytearray,0,mybytearray.length);
            os.flush();
            System.out.println("Done.");
        } finally {
            if (bis != null) bis.close();
            if (os != null) os.close();

        }
    }
}