import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchExercise implements Runnable{
    private final String[] array_of_strings;

    CountDownLatch instance_latch;

    CountDownLatchExercise(String[] array_of_strings, CountDownLatch latch){

        this.array_of_strings = array_of_strings;
        this.instance_latch = latch;
    }



    @Override
    public void run() {
        for(String grade:this.array_of_strings){
            if(grade.equals("F")){
                instance_latch.countDown();
            }
        }
    }
}

class CountF{

    public static void main(String[] args) throws InterruptedException {
        String[] largearray = new String[]{"F","F","F","F","A","F", "F","F"};
        CountDownLatch latch  = new CountDownLatch(7);
        CountDownLatchExercise c =  new CountDownLatchExercise(
                Arrays.copyOfRange(largearray,0,(largearray.length/2)),
                latch);
        CountDownLatchExercise c2 =  new CountDownLatchExercise(
                Arrays.copyOfRange(largearray,largearray.length/2,largearray.length),
                latch);
        Thread c_1 = new Thread(c);
        Thread c_2 = new Thread(c2);

        c_1.start();c_2.start();

        //assuming my input array definitely has 7 Fs else need to figure out a way i
        //all the sub arrays have been parsed through and no Fs found
        latch.await();
        System.out.println("7 Fs found!");
    }
}
