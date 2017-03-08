import java.util.List;
import java.util.ArrayList;




public class FactorThread {

    public static void main(String[] args) {

        int semiprime = 69;
        PrimeFactors1 smalltobig = new PrimeFactors1(semiprime);
        PrimeFactors2 bigtosmall = new PrimeFactors2(semiprime);
        smalltobig.start();bigtosmall.start();
        if(!smalltobig.isAlive()){
            bigtosmall.kill();
            for(Integer integer:smalltobig.factors){
                System.out.println(integer);
            }
        }else if(!bigtosmall.isAlive()){
            smalltobig.kill();
            for(Integer integer:bigtosmall.factors){
                System.out.println(integer);
            }
        }
    }
}



class PrimeFactors1 extends Thread{
    private volatile boolean isRunning = true;
    public void kill() {
        isRunning = false;
    }
    public static int numbers;
    public static List<Integer> factors = new ArrayList<Integer>();
    public void run(){
        while(isRunning) {
            primeFactors();
        }
    }

    PrimeFactors1(int n){
        this.numbers = n;
    }


    public static void primeFactors() {

        int n = numbers;

        for (int i = 2; i <= n/i; i++) {
            while (n % i == 0) {
               factors.add(i);
                n /= i;
            }
        }
        if (n > 1) {
            factors.add(n);
        }
//        return factors;
    }

}
class PrimeFactors2 extends Thread{
    private volatile boolean isRunning = true;
    public void kill() {
        isRunning = false;
    }
    public static int numbers;
    public static List<Integer> factors = new ArrayList<Integer>();
    public void run(){
        while(isRunning) {
            primeFactors();
        }
    }

    PrimeFactors2(int n){
        this.numbers = n;
    }


    public static void primeFactors() {

        int n = numbers;

        for (int i = n; i >= n/i; i--) {
            while (n % i == 0) {
                factors.add(i);
                n /= i;
            }
        }
        if (n > 1) {
            factors.add(n);
        }
//        return factors;
    }

}

