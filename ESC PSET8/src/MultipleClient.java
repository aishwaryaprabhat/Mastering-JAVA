import java.io.*;
import java.math.BigInteger;
import java.net.*;
 
public class MultipleClient {
    public static void main(String[] args) throws Exception {
    	int numberOfClients = 2; //vary this number here
    	long startTime = System.currentTimeMillis();
//        BigInteger n = new BigInteger("5");
    	BigInteger n = new BigInteger("4294967297");

    	Thread[] clients = new Thread[numberOfClients];
    	
    	for (int i = 0; i < numberOfClients; i++) {
        	clients[i] = new Thread(new Client(n));
        	clients[i].start();
        }
        
        for (int i = 0; i < numberOfClients; i++) {
        	clients[i].join();
        }

        System.out.println("Spent time: " + (System.currentTimeMillis() - startTime));
    }
}

class Client implements Runnable {
	private final BigInteger n;
	
	public Client (BigInteger n) {
		this.n = n;
	}
	
	public void run() {
		String hostName = "localhost";
        int portNumber = 4321;

        try {

        	Socket socket = new Socket(hostName, portNumber);        	
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        	BufferedReader in =
                    new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
        	out.println(n.toString());
        	out.flush();
            System.out.println("Receiving input");
        	System.out.println(in.readLine());

            out.close();
            in.close();
            socket.close();
        }
        catch (Exception e) {
            System.out.println("Server got problem");        	
        }
	}
}






