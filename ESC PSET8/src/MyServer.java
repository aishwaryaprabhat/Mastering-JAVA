import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Lenovo on 4/2/2017.
 */
public class MyServer {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(4321);
        Socket socket = ss.accept();
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println(br.readLine());
    }
}
