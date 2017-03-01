package ESC_W5;

public class ThreadArrayExample {    
    public static void main(String[] args) throws Exception {
    	int[] array = {1,2,3,4,5,6,7,8,9,10,11,12};
    	int numberOfThreads = 1;
    	Summer[] summers = new Summer[numberOfThreads];
    	Thread[] threads = new Thread[numberOfThreads];
    	
    	for (int i = 0; i < numberOfThreads; i++) {
    		summers[i] = new Summer (array, i*array.length/numberOfThreads, ((i+1)*array.length/numberOfThreads));
    		threads[i] = new Thread(summers[i]);
    		threads[i].start();
    	}
		
		try {
			int sum = 0;
	    	for (int i = 0; i < numberOfThreads; i++) {
	    		threads[i].join();
	    		sum += summers[i].getSum();
	    	}
			
			System.out.println("The sum is: " + sum);
		}
		catch (InterruptedException e) {
			System.out.println("A thread didn't finish!");
		}
    }
}
