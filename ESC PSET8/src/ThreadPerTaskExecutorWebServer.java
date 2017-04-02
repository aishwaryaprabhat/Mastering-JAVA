import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
 
public class ThreadPerTaskExecutorWebServer {
	private static final int NTHREADS = 100;
	private static final Executor exec = new ThreadPerTaskExecutor();
	
    public static void main(String[] args) throws Exception {
		ServerSocket socket = new ServerSocket(4321, 1000);

		while (true) {
			final Socket connection = socket.accept();
			Runnable task = new Runnable () {
				public void run() {
					handleRequest(connection);					
				}
			};
			
			exec.execute(task);
		}
    }

	protected static void handleRequest(Socket connection) {
		// TODO Auto-generated method stub
		
	}
}

//Thread per task
class ThreadPerTaskExecutor implements Executor {
	public void execute (Runnable r) {
		new Thread(r).start();
	}
}
