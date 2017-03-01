package ESC_W5;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class SocketTimeout {
	 
	public static void main(String[] args) throws Exception {
	    ServerSocket serverSocket = new ServerSocket(4321);	    
	    serverSocket.setSoTimeout(5000);
	    List<Socket> sockets = new ArrayList<Socket>();
	    List<PrintWriter> outChannels = new ArrayList<PrintWriter>();

	    //the following is a pattern of busy-waiting.
	    while (true) {
	    	try {
	    		Socket newSocket = serverSocket.accept();
	    		sockets.add(newSocket);
	    	    System.out.println(sockets.size() + " clients connected.");
	    		outChannels.add(new PrintWriter(newSocket.getOutputStream(), true));                   
	    	}
	    	catch (java.net.SocketTimeoutException e) {
	    	    System.out.println("Timed out.");
	    		break;
	    	}	    	
	    }
	    System.out.println("Sending an important message to ");
        
	    for (int i = 0; i < sockets.size(); i++) {
	    	outChannels.get(i).write("This is an important message from your server.");
	    	outChannels.get(i).flush();
	    	sockets.get(i).close();
	    	outChannels.get(i).close();
	    }	

	    serverSocket.close();
	}
}
