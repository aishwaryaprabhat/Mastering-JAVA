import java.util.Arrays;

public class MultiThreadSearch extends Thread {

    public boolean found = false;
    public final int[] arraytosearch;
    public final int elementtofind;


    MultiThreadSearch(int[] arraytosearch, int elementtofind){

        this.arraytosearch = arraytosearch;
        this.elementtofind = elementtofind;

    }


    @Override
    public void run() {
        for(int i=0;i<arraytosearch.length;i++){
            if(arraytosearch[i]==elementtofind){
                this.found = true;
            }

        }
    }
}

class MultiThreadSearchTest{
    public static void main(String[] args) throws InterruptedException {
        int[] testarray = new int[]{1,2,3,4,5,6,7};
        //set the element to be searched
        int elementtosearch = 21;
        MultiThreadSearch thread1 = new MultiThreadSearch(Arrays.copyOfRange(testarray,0,testarray.length/2),elementtosearch);
        MultiThreadSearch thread2 = new MultiThreadSearch(Arrays.copyOfRange(testarray,testarray.length/2,testarray.length),elementtosearch);
        thread1.start();thread2.start();
        thread1.join();thread2.join();
        if(thread1.found==true|thread2.found==true){
            System.out.println("Element found!");
        }else {
            System.out.println("Element not found!");
        }

    }

}