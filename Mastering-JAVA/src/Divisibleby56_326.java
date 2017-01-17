import java.util.Scanner;
public class Divisibleby56_326 {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter number: ");
        Double user_input = input.nextDouble();
        if(user_input%5==0 && user_input%6==0){
            System.out.println("Divisible by both");
        }else if(user_input%5==0){
            System.out.println("Divisible by 5");
        }else if(user_input%6==0){
            System.out.println("Divisible by 6");
        }else{
            System.out.println("Divisible by neither");
        }

    }

}
