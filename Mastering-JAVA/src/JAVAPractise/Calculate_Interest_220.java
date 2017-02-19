package JAVAPractise;

import java.util.Scanner;
public class Calculate_Interest_220 {
    public static void main(String[] args){
        Scanner input =  new Scanner(System.in);
        System.out.print("Enter balance: ");
        double balance = input.nextDouble(); System.out.println();
        System.out.println("Enter annual interest rate: ");
        double rate = input.nextDouble();
        double interest = balance*(rate/1200.0);
        System.out.println("The interest is: "+interest);

    }
}
