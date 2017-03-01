package ThreadedYo;

public class BeatlesThread{
    public static void main(String[] args) {
        YouSayYes y = new YouSayYes();
        ISayNo n = new ISayNo();
        y.start(); ( new Thread(n)).start();
    }

}


class YouSayYes extends Thread{

    @Override
    public void run() {
        for (int i=0; i<5;i++) {
            System.out.println("You say yes");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


}

class ISayNo implements Runnable{

    @Override
    public void run() {
        for (int i=0; i<5;i++) {
            System.out.println("I say no");
        }
    }
}
