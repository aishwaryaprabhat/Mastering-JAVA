public class LockStaticVariables {
	public static int saving = 5000;
	public static int cash = 0;
	
	public static void main(String args[]){   	
		int numberofThreads = 10000;
		WD[] threads = new WD[numberofThreads];
	
		for (int i = 0; i < numberofThreads; i++) {

			threads[i] = new WD();
			threads[i].start();

		}
		
		try {
			for (int i = 0; i < numberofThreads; i++) {
				threads[i].join();
			}
		} catch (InterruptedException e) {
			System.out.println("some thread is not finished");
		}
		
		System.out.println("The cash is: " + LockStaticVariables.cash);
		System.out.println("The saving is: " + LockStaticVariables.saving);
	}
}

class WD extends Thread {	
	public void run () {
		if (LockStaticVariables.saving >= 1000 && LockStaticVariables.cash+LockStaticVariables.saving==5000) {
			System.out.println("I am doing something.");			
			LockStaticVariables.saving = LockStaticVariables.saving - 1000;
			LockStaticVariables.cash = LockStaticVariables.cash + 1000;
		}		
	}	
}

