package JAVAPractise;

import java.util.Arrays;

/**
 * Created by Lenovo on 1/26/2017.
 */
public class SecondLargest {

    public static void main(String[] args) {
        System.out.println(outofthree(4,3,1));
        System.out.println(outoffour(4,3,6,1));
    }

    public static double outofthree(double a, double b, double c){

        Double[] arr = new Double[]{a,b,c};
        Arrays.sort(arr);
        return arr[1];
    }
    public static double outoffour(double a, double b, double c, double d){
        Double[] arr = new Double[]{a,b,c,d};
        Arrays.sort(arr);
        return arr[2];

    }
}
