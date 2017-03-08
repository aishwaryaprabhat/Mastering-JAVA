class TestYo{
    public static void main(String[] args) {
        SynchronizedObject a1 = new SynchronizedObject(new StringBuffer("A"));
        SynchronizedObject a2 = new SynchronizedObject(a1.sb);
        SynchronizedObject a3 = new SynchronizedObject(a2.sb);
        a1.start();a2.start();a3.start();
    }
}
public class SynchronizedObject extends Thread {
    public StringBuffer sb;
    SynchronizedObject(StringBuffer sb){

        this.sb = sb;
    }
    public void run(){
        synchronized (this.sb){
            for(int i=1;i<=100;i++){
                System.out.print(this.sb);

//
            }
            System.out.println();
            int currentalphabet = (this.sb.toString()).charAt(0);
//            System.out.println(currentalphabet);
            char next = (char) (currentalphabet+1);
            this.sb = new StringBuffer(Character.toString(next));

//            System.out.println("Next:"+this.sb.toString());
        }
    }

}
