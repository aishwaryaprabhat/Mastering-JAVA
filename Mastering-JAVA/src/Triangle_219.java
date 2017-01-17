/**
 * Created by Lenovo on 1/13/2017.
 */
import java.util.Scanner;
public class Triangle_219 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter x1,y1,x2,y2,x3,y3: ");
        double x1 = input.nextDouble();
        double y1 = input.nextDouble();
        double x2 = input.nextDouble();
        double y2 = input.nextDouble();
        double x3 = input.nextDouble();
        double y3 = input.nextDouble();
        System.out.println("Area of triangle = "+getarea(sidecalculator(x1,y1,x2,y2),sidecalculator(x2,y2,x3,y3),sidecalculator(x3,y3,x1,y1)));


    }
    public static double sidecalculator(double x1,double y1,double x2,double y2){
        double side = Math.pow((Math.pow((x1-x2),2)+Math.pow((y1-y2),2)),0.5);
        return side;
    }
    public static double getarea(double side1, double side2, double side3){
        double s = (side1+side2+side3)/2;
        double area = Math.sqrt(s*(s-side1)*(s-side2)*(s-side3));
        return area;
    }
}
