import static org.junit.Assert.*;

import java.util.Random;

import org.junit.*;

/**
 * Took help to complete this code from Lund University notes: http://fileadmin.cs.lth.se/cs/education/EDA015F/2013/Ch12-presentation.pdf
 */

public class BoundedBufferTest {
	private static final long LOCKUP_DETECT_TIMEOUT = 1000;

	@Test
	public void testIsEmptyWhenConstructued () {
		BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
		assertTrue(bb.isEmpty());
		assertFalse(bb.isFull());
	}

	@Test
	public void testIsFullAfterPuts () throws InterruptedException {
		final BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);

		Runnable task = new Runnable () {
			public void run() {
				try {
					bb.put((new Random()).nextInt());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		Thread[] threads = new Thread[10];

		for (int i = 0; i < 10; i++) {
			threads[i] = new Thread (task);
			threads[i].start();
		}

		for (int i = 0; i < 10; i++) {
			threads[i].join();
		}

		assertTrue(bb.isFull());
		assertFalse(bb.isEmpty());
	}

	@Test
	public void testTakeBlocksWhenEmpty () {
		final BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
		Thread taker = new Thread() {
			public void run() {
				try {
					int unused = bb.take();
					assertTrue(false);
				} catch (InterruptedException success) {} //if interrupted, the exception is caught here
			}
		};

		try {
			taker.start();
			Thread.sleep(LOCKUP_DETECT_TIMEOUT);
			taker.interrupt();
			taker.join(LOCKUP_DETECT_TIMEOUT);
			assertFalse(taker.isAlive()); //the taker should not be alive for some time
		} catch (Exception unexpected) {
			assertTrue(false);
		}
	}


	//This test method tests checks for resource leaks such that
	//objects that hold other objects should not hold them unnecessarily
	//For bounded classes it ensures that the object is not uncontrollably growing
	@Test
	void testLeakofResources() throws InterruptedException {
		BoundedBuffer<Big> bb = new BoundedBuffer<Big>(CAPACITY);
		int heapSize1 = /* snapshot heap, “forces” GC */;
		for (int i = 0; i < CAPACITY; i++)
			bb.put(new Big());
		for (int i = 0; i < CAPACITY; i++)
			bb.take();
		int heapSize2 = /* snapshot heap, “forces” GC */;
		assertTrue(Math.abs(heapSize1-heapSize2) < THRESHOLD);
	}

	//This test method checks for any data races and ensures no chicken-egg situation
	//for example it is useful for consumer-producer problems
	//ensure that everything that goes into a waiting queue comes out
	//use non-compiler guessable checksums for enqueued items

	@Test
	void testForSafety() {
		try {
			for (int i = 0; i < nPairs; i++) {
				pool.execute(new Producer());
				pool.execute(new Consumer());
			}
			barrier.await(); // wait for all threads to be ready
			barrier.await(); // wait for all threads to finish
			assertEquals(putSum.get(), takeSum.get());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
