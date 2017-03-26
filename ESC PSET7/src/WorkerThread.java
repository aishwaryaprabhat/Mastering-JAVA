import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class TestWrokerThread{
    public static void main(String[] args) {
        Map map = new ConcurrentHashMap();
        Map map2 = Collections.synchronizedMap(map);
        WorkerThread w1 = new WorkerThread(Collections.synchronizedMap(map2), "synchronizedMap");
        WorkerThread w2 = new WorkerThread(map, "HashMap");

        w1.start();
        w2.start();
    }
}

public class WorkerThread extends Thread {

    private Map<String, Integer> map = null;
    public final String name;

    public WorkerThread(Map<String, Integer> map, String name) {

        this.map = map;
        this.name = name;

    }

    public void run() {
        long statrtime = System.currentTimeMillis();
          for (int i=0; i<500000; i++) {
                 // Return 2 random integers
                 Integer newInteger1 = (int) Math.ceil(Math.random()*10000);
                 Integer newInteger2 = (int) Math.ceil(Math.random()*10000);                                           
                 // 1. Attempt to retrieve a random Integer element
                 map.get(String.valueOf(newInteger1));
                 // 2. Attempt to insert a random Integer element
                 map.put(String.valueOf(newInteger2), newInteger2);
          }
        long endtime = System.currentTimeMillis();
        System.out.println(name+" took "+(endtime-statrtime)+" ms");

    }
}