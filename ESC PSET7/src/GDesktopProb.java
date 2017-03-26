
import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GDesktopProb {
	private final static int N_CONSUMERS = 4;
	
	//it starts here
	public static void startIndexing (File[] roots) {
		//we specify a capacity for the blockingqueue == 10
		BlockingQueue<File> queue = new LinkedBlockingQueue<>(10);
		FileFilter filter = new FileFilter() {
			public boolean accept(File file) {return true;}
		};
		
		for (File root : roots) {
			(new FileCrawlerProb(queue, filter, root)).start();
		}
		
		for (int i = 0; i < N_CONSUMERS; i++) {
			(new IndexerProb(queue)).start();
		}
	}
}

class FileCrawlerProb extends Thread {
	private final Queue<File> fileQueue; 
	private final FileFilter fileFilter; 	
	private final File root;
	
	FileCrawlerProb (Queue<File> queue, FileFilter filter, File root) {
		this.fileQueue = queue;
		this.fileFilter = filter;
		this.root = root;
	}
	
	public void run() {
		try {
			crawl(root);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	
	private void crawl(File root) throws InterruptedException {
		File[] entries = root.listFiles(fileFilter);
		
		if (entries != null) {
			for (File entry : entries) {
				if (entry.isDirectory()) {
					crawl(entry);
				}
				else {
					fileQueue.offer(entry);	
				}
			}
		}
	}
}

class IndexerProb extends Thread {
	private final BlockingQueue<File> queue;
	
	public IndexerProb (BlockingQueue<File> queue) {
		this.queue = queue;
	}
	
	public void run() {
		while (true) {
			indexFile(queue.poll());
		}
	}

	private void indexFile(File file) {
		// code for analyzing the context of the file is skipped for simplicity	
	}
}