import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class WebServer {
	public static void main (String[] args) throws Exception {
		ServerSocket socket = new ServerSocket(4321, 1000);
		long startTime = 0;
		while (true) {
			Socket connection = socket.accept();

			System.out.println("Connected");
			if (startTime == 0) {
				startTime = System.currentTimeMillis();
			}
			handleRequest(connection);
		}
	}
	
	private static void handleRequest (Socket connection) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		BigInteger input = new BigInteger(String.valueOf(br.read()));


		BigInteger answer = factor(input);
		synchronized (answer){
			OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
			PrintWriter pw = new PrintWriter(osw);
			pw.write(answer.toString());
			osw.flush();
		}
	}
	
	private static BigInteger factor(BigInteger n) {
		BigInteger i = new BigInteger("2");
		BigInteger zero = new BigInteger("0");
		
		while (i.compareTo(n) < 0) {			
			if (n.remainder(i).compareTo(zero) == 0) {
				System.out.println(i);
				return i;

			}
			
			i = i.add(new BigInteger("1"));
		}
		
		assert(false);
		return null;
	}
}
