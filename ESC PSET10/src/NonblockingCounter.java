import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class NonblockingCounter {
    private AtomicStampedReference<AtomicInteger> value = new AtomicStampedReference<AtomicInteger>(new AtomicInteger(),0);
//    private AtomicInteger value = new AtomicInteger();
    private int stamp = 0;
    public int getValue() {
        return value.getReference().get();
    }

    public int increment() {

        AtomicInteger oldref;
        int oldstamp;
        AtomicInteger newref;

        do {
            //Prof's solution
            oldref = value.getReference();
            oldstamp = value.getStamp();
            newref = new AtomicInteger((oldref.get()));
            newref.incrementAndGet();
        } while (!value.compareAndSet(oldref, newref, oldstamp, oldstamp + 1));
        return oldref.get();
    }
}

//        int oldValue;
//        int oldstamp;
//        do{
//          oldValue = value.getReference();
//          oldstamp = value.getStamp();
//            newref = new AtomicInteger(oldref.get());
//        }while(!(new AtomicStampedReference<Integer>(oldValue,1).compareAndSet(oldValue,oldValue+1,stamp,stamp+1)));
//        {
//            return oldValue + 1;
//        }


