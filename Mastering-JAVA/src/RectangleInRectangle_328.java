import java.util.Scanner;


public class RectangleInRectangle_328 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter x: ");
        double x = input.nextDouble();
        System.out.println();

        System.out.print("Enter y: ");
        double y = input.nextDouble();
        System.out.println();

        System.out.print("Enter h: ");
        double h = input.nextDouble();
        System.out.println();

        System.out.print("Enter w: ");
        double w = input.nextDouble();
        System.out.println();

        System.out.print("Enter x1: ");
        double x1 = input.nextDouble();
        System.out.println();

        System.out.print("Enter y1: ");
        double y1 = input.nextDouble();
        System.out.println();

        System.out.print("Enter h1: ");
        double h1 = input.nextDouble();
        System.out.println();

        System.out.print("Enter w1: ");
        double w1 = input.nextDouble();
        System.out.println();


        double[] rectangle1 = edge_producer(x,y,w,h);
        double[] rectangle2 = edge_producer(x1,y1,w1,h1);
        System.out.println(checker(rectangle1, rectangle2));


    }
    public static double[] edge_producer(double x, double y, double width, double height)
    {
        double[] a = new double[4];
        double top_edge = y+(height/2);
        a[0] = top_edge;
        double bottom_edge = y-(height/2);
        a[1] = bottom_edge;
        double right_edge = x + (width/2);
        a[2] = right_edge;
        double left_edge = x - (width/2);
        a[3] = left_edge;
        return a;

    }

    public static boolean checker(double[]r1, double[] r2){
        if(r1[0]<=r2[0] && r1[0]>=r2[1]){
            return true;
        }
        if(r1[1]<=r2[0] && r1[1]>=r2[1]){
            return true;
        }
        if(r1[2]<=r2[2] && r1[2]>=r2[3]){
            return true;
        }
        if(r1[3]<=r2[2] && r1[3]>=r2[3]){
            return true;
        }
        else{
            return false;
        }

    }
}
