import java.util.HashMap;

/**
 * Created by Lenovo on 4/11/2017.
 */
public class NvsFPerformance {
    public static void main(String[] args) {

        double[][] hash = new double[][]{
                {10.0, 0.1},
                {100.0, 0.1},
                {100000.0, 0.1},
                {10.0, 0.25},
                {100.0, 0.25},
                {100000.0, 0.25}
        };
        System.out.println("N       F       Speedup");
        for(double[] arr:hash){
            double N = arr[0];
            double F = arr[1];
            double speedup = 1.0/(F+((1-F)/N));
            System.out.println(N+"      "+F+"       "+speedup);
        }

    }
}
