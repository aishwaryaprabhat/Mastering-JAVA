import java.util.Scanner;

public class SleepCounter extends Thread{
    public boolean kill = true;
    @Override
    public void run() {
        while(this.kill){
            System.out.println(System.currentTimeMillis());
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void kill(){
        this.kill = false;
    }
}

class SleepCounterTest{

    public static void main(String[] args) {
        SleepCounter sl = new SleepCounter();
        sl.start();
        Scanner input = new Scanner(System.in);
        //press 0 and then enter
        if(input.nextInt()==0){
            sl.kill();
        }

    }
}
