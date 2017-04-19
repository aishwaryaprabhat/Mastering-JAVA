import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SecStoreRSA {

    public final static int SOCKET_PORT = 13267;  // you may change this
    public final static String FILE_TO_RECEIVED = "received_file";  // you may change this, I give a

    public final static int FILE_SIZE = 6022386;
    public static void main (String [] args ) throws IOException {

        ServerSocket servsock = null;
        Socket sock = null;
        try {
            servsock = new ServerSocket(SOCKET_PORT);
            while (true) {
                System.out.println("Waiting...");
                try {
                    sock = servsock.accept();
                    receivefile(sock);
                    System.out.println("Accepted connection : " + sock);
                }finally {
                    if (sock!=null) sock.close();
                }
            }
        }finally {

                if (servsock != null) servsock.close();
            }

    }

    public static void receivefile(Socket sock) throws IOException {

        int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        try{
            // receive file
            byte[] mybytearray = new byte[FILE_SIZE];
            InputStream is = sock.getInputStream();
            fos = new FileOutputStream(FILE_TO_RECEIVED);
            bos = new BufferedOutputStream(fos);
            bytesRead = is.read(mybytearray, 0, mybytearray.length);
            current = bytesRead;

            do {
                bytesRead =
                        is.read(mybytearray, current, (mybytearray.length - current));
                if (bytesRead >= 0) current += bytesRead;
            } while (bytesRead > -1);

            bos.write(mybytearray, 0, current);
            bos.flush();
            System.out.println("File " + FILE_TO_RECEIVED
                    + " downloaded (" + current + " bytes read)");
        } finally {
            if (fos != null) fos.close();
            if (bos != null) bos.close();

        }
    }
}


