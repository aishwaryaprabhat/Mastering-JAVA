public class SleepExample {
	public static final int rounds = 5000;
	public static final int sleepingTime = 50;
	
    public static void main(String[] args) throws Exception {
    	LeftThread left = new LeftThread();
    	RightThread right = new RightThread();
    	left.start();
    	right.start();
    	
    	try {
    		left.join();
    		right.join();
    	}
    	catch (InterruptedException e) {
			e.printStackTrace();    		
    	}
    }
}

class LeftThread extends Thread {
	public void run() {
		for (int i = 0; i < SleepExample.rounds; i++) {
			System.out.print(1);
			System.out.print(2);
			System.out.print(3);
			
			try { 
				Thread.sleep(SleepExample.sleepingTime*2);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println(5);
		}
	}
}

class RightThread extends Thread {
	public void run() {
		try {
			Thread.sleep(SleepExample.sleepingTime);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (int i = 0; i <  SleepExample.rounds; i++) {
			System.out.print(4);
			
			try { 
				Thread.sleep(SleepExample.sleepingTime*2);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
