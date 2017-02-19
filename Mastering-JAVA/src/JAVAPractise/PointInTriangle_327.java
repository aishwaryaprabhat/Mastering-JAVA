package JAVAPractise;

import java.util.Scanner;

public class PointInTriangle_327 {
    public static void main(String[] args) {

        Scanner input  = new Scanner(System.in);
        System.out.print("Enter x: ");
        Double x = input.nextDouble();
        System.out.println();
        System.out.print("Enter y: ");
        Double y = input.nextDouble();

        //angle from 0,0
        Double angle = Math.atan2(y,x);
        if(angle<=Math.PI/2 && angle >=0){
            //calculate intersection point
            // -0.5*x1 + 100 = y1
            //y2 = c*x2

            Double c = y/x;
            Double x1 = 100/(0.5+c);
            Double y1 = -0.5*x1 + 100;
            if(x<=x1&&y<=y1){
                System.out.println("In triangle");

            }
            else{
                System.out.println("Outside triangle but in first quadrant");
            }
        }else{
            System.out.println("Not in trianble");
        }



    }
}
