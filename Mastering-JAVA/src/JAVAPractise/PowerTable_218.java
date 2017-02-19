package JAVAPractise;

/**
 * Created by Lenovo on 1/13/2017.
 */
public class PowerTable_218 {
    public static void main(String[] args){
        int m = 6;
        System.out.println("a b pow(a,b)");
        for(int i=1;i<=m;i++){
            System.out.println(rowgenerator(i));
        }

    }
    public static String rowgenerator(int n){
        String s = Integer.toString(n)+" "+Integer.toString(n+1)+" "+Double.toString(Math.pow(n,n+1));
        return s;
    }
}
