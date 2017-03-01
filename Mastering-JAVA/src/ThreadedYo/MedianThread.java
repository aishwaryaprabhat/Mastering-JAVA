package ThreadedYo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;


public class MedianThread {
    static long starttime;
    static long endtime;
    static ArrayList<Integer> array_of_input_integers = new ArrayList<>();
    static int number_of_threads;
    static ArrayList<MedianMultiThread> array_of_threads = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        System.out.print("Enter file location: ");
        Scanner input = new Scanner(System.in);
        String filelocation = input.nextLine();
        System.out.println();
        System.out.print("Enter number of threads: ");
        Scanner numberofthreads = new Scanner(System.in);
        number_of_threads = input.nextInt();
        experiment_Median(filelocation,number_of_threads);
    }
    public static void experiment_Median(String filelocation, int numberofthreads) throws InterruptedException, FileNotFoundException {
        Scanner scanner  = new Scanner(new File(filelocation));
        while (scanner.hasNextInt()) {
            array_of_input_integers.add(scanner.nextInt());
        }

        int partitionsize = array_of_input_integers.size() / numberofthreads;

        ArrayList<Integer>[] num_sublists = new ArrayList[numberofthreads];

        for  (int i = 0; i<numberofthreads; i++) {
            num_sublists[i] = new ArrayList<Integer>();
            for  (int count = 0; count<partitionsize; count++) {
                num_sublists[i].add( array_of_input_integers.get(count+i*partitionsize) );
            }
        }


        long startTime = System.currentTimeMillis();




        MedianMultiThread[] mini_thread_lists = new MedianMultiThread[numberofthreads];

        for (int i=0; i<numberofthreads; i++) {
            MedianMultiThread thread = new MedianMultiThread(num_sublists[i]);
            mini_thread_lists[i] = thread;
        }


        for (int i=0; i<numberofthreads; i++) {
            mini_thread_lists[i].start();
        }

        for (int i=0; i<numberofthreads; i++) {
            mini_thread_lists[i].join();
        }


        ArrayList sortedFullArray = new ArrayList();
        for (int num : mini_thread_lists[0].getInternal()) sortedFullArray.add(num);

        for (int i=1; i<numberofthreads; i++) {
            merge(sortedFullArray,mini_thread_lists[i].getInternal());
        }


        double median = computeMedian(sortedFullArray);


        long stopTime = System.currentTimeMillis();
        long totalTime = stopTime - startTime;


        System.out.printf("Final array: ");
        System.out.println(Arrays.toString(sortedFullArray.toArray()));


        System.out.println("Median:" + median);

        System.out.println("Total time taken: " + (totalTime));
    }

    public static double computeMedian(ArrayList<Integer> inputArray) {
        double Median;
        if (inputArray.size() % 2 == 0)
            Median = ( (double) inputArray.get(inputArray.size()/2) + (double)inputArray.get(inputArray.size()/2 - 1) )/2;
        else
            Median = (double) inputArray.get(inputArray.size()/2);
        return Median;
    }

    public static void merge(ArrayList<Integer> first_list, ArrayList<Integer> second_list) {
        for (int index1 = 0, index2 = 0; index2 < second_list.size(); index1++) {
            if (index1 == first_list.size() || first_list.get(index1) > second_list.get(index2)) {
                first_list.add(index1, second_list.get(index2++));
            }
        }
    }


}

// extend Thread
class MedianMultiThread extends Thread {
    private ArrayList<Integer> list;

    public ArrayList<Integer> getInternal() {
        return list;
    }

    MedianMultiThread(ArrayList<Integer> array) {
        list = array;
    }

    public void run() {

        this.list = quickSort(this.list);
    }


    public ArrayList<Integer> quickSort(ArrayList<Integer> array) {
        if(array.size() <= 1){
            return array;
        }
        int middle = (int) Math.ceil((double)array.size() / 2);
        int pivot = array.get(middle);
        ArrayList<Integer> less = new ArrayList<Integer>();
        ArrayList<Integer> larger = new ArrayList<Integer>();

        for (int i = 0; i < array.size(); i++) {
            if(array.get(i) <= pivot){
                if(i == middle){
                    continue;
                }
                less.add(array.get(i));
            }
            else{
                larger.add(array.get(i));
            }
        }
        return puteverythingtogether(quickSort(less), pivot, quickSort(larger));
    }

    private ArrayList<Integer> puteverythingtogether(ArrayList<Integer> less, int pivot, ArrayList<Integer> larger){
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < less.size(); i++) {
            list.add(less.get(i));
        }
        list.add(pivot);
        for (int i = 0; i < larger.size();i++) {
            list.add(larger.get(i));
        }
        return list;
    }
}