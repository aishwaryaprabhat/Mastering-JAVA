package JAVAPractise; /**
 * Created by Lenovo on 1/13/2017.
 */
import java.util.Scanner;
public class CostOfDriving_223 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the driving distance: ");
        double distance = input.nextDouble(); System.out.println();
        System.out.print("Enter miles per gallon: ");
        double mileage = input.nextDouble(); System.out.println();
        System.out.print("Enter price per gallon: ");
        double price = input.nextDouble(); System.out.println();
        System.out.print("The cost of driving is "+(Math.round((distance/mileage)*price*100))/100.0);
    }
}
