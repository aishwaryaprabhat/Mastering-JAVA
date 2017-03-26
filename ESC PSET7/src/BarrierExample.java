import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BarrierExample {

	private static class Task implements Runnable {
        //private CyclicBarrier barrier;
        private MyCyclicBarrier barrier;

        public Task(MyCyclicBarrier barrier) {
            this.barrier = barrier;
        }

        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " is waiting on barrier");
                barrier.await();
                System.out.println(Thread.currentThread().getName() + " has crossed the barrier");
            } catch (Exception ex) {
                Logger.getLogger(BarrierExample.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String args[]) {
    	//A CyclicBarrier supports an optional Runnable command that is run once per barrier point,
    	//after the last thread in the party arrives, but before any threads are released. This
    	//barrier action is useful for updating shared-state before any of the parties continue.
        final MyCyclicBarrier cb = new MyCyclicBarrier(3, new Runnable(){
            public void run(){
                //This task will be executed once all thread reaches barrier
                System.out.println("All parties are arrived at barrier, lets play");
            }
        });

        //starting each of thread
        Thread t1 = new Thread(new Task(cb), "Thread 1");
        Thread t2 = new Thread(new Task(cb), "Thread 2");
        Thread t3 = new Thread(new Task(cb), "Thread 3");

        t1.start();
        t2.start();
        t3.start();
    }
}


//this class must be thread-safe. why?
class MyCyclicBarrier {

    private int barrierlimit = 0;
    private int count = 0;
    private Runnable torun;

    public MyCyclicBarrier (int count, Runnable torun) {
        this.count = count;
        this.barrierlimit = count;
        this.torun = torun;
    }

    public MyCyclicBarrier (int count) {
        this.count = count;
    }

    //complete the implementation below.
    //hint: use wait(), notifyAll()
    public synchronized void await () throws InterruptedException {

        this.barrierlimit--;
        if(barrierlimit>0){ //till all the threads have reached await(), each thread has to continue waiting
            this.wait();
        }else{
            barrierlimit = count;
            notifyAll();
        }


    }
}