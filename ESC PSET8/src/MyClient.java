import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MyClient {
    public static void main(String[] args) throws IOException {
        String ip= "localhost";
        int port = 4321;
        Socket mySocket = new Socket(ip,port);

        String myString = "I am connected";

        OutputStreamWriter osw = new OutputStreamWriter(mySocket.getOutputStream());

        PrintWriter pw = new PrintWriter(osw);

        osw.write(myString);
        osw.flush();
        osw.close();

    }
}
