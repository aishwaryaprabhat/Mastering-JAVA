

package ThreadedYo;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


// TODO: read data from external file and store it in an array
// Note: you should pass the file as a first command line argument at runtime.

// define number of threads
//        int NumOfThread = Integer.valueOf(args[1]);// this way, you can pass number of threads as
// a second command line argument at runtime.

// TODO: partition the array list into N subArrays, where N is the number of threads

// TODO: start recording time

// TODO: create N threads and assign subArrays to the threads so that each thread computes mean of
// its repective subarray. For example,

//        MeanMultiThread thread1 = new MeanMultiThread(subArray1);
//        MeanMultiThread threadn = new MeanMultiThread(subArrayn);
//Tip: you can't create big number of threads in the above way. So, create an array list of threads.

// TODO: start each thread to execute your computeMean() function defined under the run() method
//so that the N mean values can be computed. for example,
//        thread1.start(); //start thread1 on from run() function
//        threadn.start();//start thread2 on from run() function
//
//        thread1.join();//wait until thread1 terminates
//        threadn.join();//wait until threadn terminates

// TODO: show the N mean values

// TODO: store the temporal mean values in a new array so that you can use that
/// array to compute the global mean.

// TODO: compute the global mean value from N mean values.

// TODO: stop recording time and compute the elapsed time

public class MeanThread {
    static long starttime;
    static long endtime;
    static ArrayList<Integer> array_of_input_integers = new ArrayList<>();
    static int number_of_threads;
    static ArrayList<MeanMultiThread> array_of_threads = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        System.out.print("Enter file location: ");
        Scanner input = new Scanner(System.in);
        String filelocation = input.nextLine();
        System.out.println();
        System.out.print("Enter number of threads: ");
        Scanner numberofthreads = new Scanner(System.in);
        number_of_threads = input.nextInt();
        experiment_mean(filelocation,number_of_threads);
    }
    public static void experiment_mean(String filelocation, int numberofthreads) throws InterruptedException, FileNotFoundException {
        Scanner scanner  = new Scanner(new File(filelocation));
        while (scanner.hasNextInt()) {
            array_of_input_integers.add(scanner.nextInt());
        }

        int partitionsize  = array_of_input_integers.size()/number_of_threads;

        ArrayList temporal_means = mean_manager(partitionsize);

        System.out.println("Temporal means: "+temporal_means.toString());

        double globalmean = computeglobalmean(temporal_means);
        endtime = System.currentTimeMillis();
        System.out.println("Global mean = "+globalmean);
        System.out.println("Execution time = "+(endtime-starttime));

    }

    public static ArrayList mean_manager(int partitionsize) throws InterruptedException {

        ArrayList array_of_temporal_means = new ArrayList();
//        ArrayList<MeanMultiThread> array_of_threads = new ArrayList<>();
        for(int i=0;i<array_of_input_integers.size();i+=partitionsize){
            int startindex = i;
            int endindex = i+partitionsize-1;
            ArrayList<Integer> smallerarray = new ArrayList<>();
            for(int j = startindex;j<=endindex;j++) {

                smallerarray.add(array_of_input_integers.get(j));
            }
//            System.out.println(smallerarray.toString());

            array_of_threads.add(new MeanMultiThread(smallerarray));
        }
        starttime = System.currentTimeMillis();
        for(MeanMultiThread m: array_of_threads){
            m.start();
        }

        for(MeanMultiThread m:array_of_threads){
            m.join();
        }

        for(MeanMultiThread m:array_of_threads){
            array_of_temporal_means.add(m.getMean());

        }

        return array_of_temporal_means;
    }

    public static double computeglobalmean(ArrayList<Double> a){
        double sum = 0;
        for (int i=0;i<a.size();i++){
            sum = sum+a.get(i);
        }
        return sum/a.size();
    }


}
//Extend the Thread class
class MeanMultiThread extends Thread {
    private ArrayList<Integer> list;
    private double mean;

    public static double computemean(ArrayList<Integer> a){
        double sum = 0;
        for (int i=0;i<a.size();i++){
            sum = sum+a.get(i);
        }
        return sum/a.size();
    }
    MeanMultiThread(ArrayList<Integer> array) {
        list = array;
    }
    public double getMean() {
        return mean;
    }
    public void run() {
//        System.out.println("list size:"+list.size());
        mean = computemean(list);

    }
}