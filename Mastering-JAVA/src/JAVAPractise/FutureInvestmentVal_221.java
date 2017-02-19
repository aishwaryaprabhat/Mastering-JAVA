package JAVAPractise;

import java.util.Scanner;

public class FutureInvestmentVal_221 {
    public static void main(String[] args){
        Scanner input  = new Scanner(System.in);
        System.out.print("Enter investment amount: ");
        double investmentAmount = input.nextDouble(); System.out.println();
        System.out.print("Enter annual interest rate: ");
        double annualinterestrate = input.nextDouble(); System.out.println();
        System.out.print("Enter number of years: ");
        double years = input.nextDouble(); System.out.println();

        double future_investment_val = investmentAmount*Math.pow((1+(annualinterestrate/12)),years);
        System.out.println("Accumulated value is: "+future_investment_val);
    }
}