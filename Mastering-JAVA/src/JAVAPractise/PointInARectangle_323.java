package JAVAPractise;

import java.util.Scanner;
public class PointInARectangle_323 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Input x coordinate: ");
        Double x = input.nextDouble();
        System.out.println("Input y coordinate: ");
        Double y = input.nextDouble();

        //mathematical computation
        if (x<=10 && x>=-10 && y<=5 && y>=-5){
            System.out.println("Point ("+x+","+y+") is in the rectangle");
        }else {
            System.out.println("nah");
        }
    }
}