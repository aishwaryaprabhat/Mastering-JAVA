import java.util.Random;
import java.util.concurrent.Phaser;


//took inspiration from Ong Teck Wu for this piece of <code></code>



public class ExamExample
{
    private static void begin(final Phaser examiner, final int sleepTime)
    {
        examiner.register();
        new Thread(){
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(sleepTime);
                    System.out.println(Thread.currentThread().getName()+" is ready to start");
                    examiner.arriveAndAwaitAdvance();
                }

                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"Student is in the exam");
            }
        }.start();
    }

    private static void submitExam(final Phaser examiner, final int sleepTime)
    {
        new Thread(){
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(sleepTime);
                    System.out.println(Thread.currentThread().getName()+" is ready to hand in");
                    examiner.arriveAndDeregister();
                }

                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    public static void main(String[] args) throws InterruptedException
    {

        Phaser examiner = new Phaser();
        examiner.register();

        Random rn = new Random();

        int students = 100;
        for (int i = 0; i < students; i++) {
            begin(examiner, (rn.nextInt(5) + 1) * 1000);
        }

        examiner.arrive();
        System.out.println("Begin");
        System.out.println("Phasecount:"+examiner.getPhase());


        for (int i = 0; i < students; i++) {
            submitExam(examiner, (rn.nextInt(5) + 1) * 1000);
        }
        examiner.arriveAndAwaitAdvance();
        System.out.println("Ended");


        System.out.println("Phasecount:"+examiner.getPhase());

    }

}