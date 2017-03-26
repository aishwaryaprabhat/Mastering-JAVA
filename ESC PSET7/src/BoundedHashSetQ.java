
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class  BoundedHashSetQ<T> {
	private final Set<T> set;
	Semaphore s;
	public BoundedHashSetQ (int bound) {
		this.set = Collections.synchronizedSet(new HashSet<T>());
		Semaphore s = new Semaphore(set.size());
	}
	
	public boolean add(T o) throws InterruptedException {
		s.acquire();
		set.add(o);
		s.release();
		return set.add(o);

	}
	
	public boolean remove (Object o) throws InterruptedException {
		s.acquire();
		set.remove(o);
		s.release();
		return set.remove(o);
	}
}

