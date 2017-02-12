import java.util.Arrays;

/**
 * Created by Lenovo on 2/7/2017.
 */
public class SortingAlgos {

    public static void main(String[] args) {
        int[] arr = {1,5,324,6,2,6};
        System.out.println(Arrays.toString(insertionsort(arr)));
    }

    public static int[] insertionsort(int[] input){
        for (int i=0;i<input.length;i++){
            for(int j=i;j>0;j--){
                if(input[j]<input[j-1]){
                    int tempvar = input[j];
                    input[j] = input[j-1];
                    input[j-1] = tempvar;
                }
                else{continue;}
            }
        }
        return input;
    }
}
