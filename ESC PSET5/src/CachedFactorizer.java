import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class FactorizerUser {
	public static void main (String[] args) {
		CachedFactorizer factorizer = new CachedFactorizer ();
		MyRunnable tr1 = new MyRunnable(factorizer);
		MyRunnable tr2 = new MyRunnable(factorizer);
		tr1.start();
		tr2.start();
		
		try {
			tr1.join();
			tr2.join();
		}

		catch (Exception e) {
			
		}
	}
}

class MyRunnable extends Thread {
	private CachedFactorizer factorizer;
	
	public MyRunnable (CachedFactorizer factorizer) {
		this.factorizer = factorizer; 
	}
	
	public void run () {
		Random random = new Random ();

		factorizer.factor(random.nextInt(100));
	}
}

public class CachedFactorizer {
	private int lastNumber;
	private List<Integer> lastFactors;
	private long hits;
	private long cacheHits;
	
	public long getHits () {
		return hits;
	}
	
	public double getCacheHitRatio () {
		return (double) cacheHits/ (double) hits;
	}
	
	public List<Integer> service (int input) {
		List<Integer> factors = null;
		++hits;
		
		if (input == lastNumber) {
			++cacheHits;
			factors = new ArrayList<Integer>(lastFactors);
		}
		
		if (factors == null) {
			factors = factor(input);
			lastNumber = input;
			lastFactors = new ArrayList<Integer>(factors);
		}
		
		return factors;
	}
	
	public List<Integer> factor(int n) {
		System.out.println(n);
		List<Integer> factors = new ArrayList<Integer>();
		synchronized (factors){
		for (int i = 2; i <= n; i++) {
                while (n % i == 0) {
                    factors.add(i);
                    n /= i;
                }
		    }
		}
		System.out.println(factors.toString());
		return factors;
	}
}