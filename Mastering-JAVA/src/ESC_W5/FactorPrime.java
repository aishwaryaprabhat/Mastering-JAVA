package ESC_W5;


//
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.Arrays;
//
///**
// * Created by Lenovo on 2/20/2017.
// */
//public class W5C1_1 {
//
//    final BigInteger n;
//    ArrayList factors = new ArrayList();
//
//    public W5C1_1(BigInteger n) {
//        this.n = n;
//    }
//
//    public ArrayList outputprimefactors() {
//        for (BigInteger i = 1; i <= Math.sqrt(this.n); i++) {
//            if (checkdivision(i) == true) {
//                factors.add(i);
//                factors.add(this.n / i);
//            }
//        }
//        return factors;
//    }
//
//    public boolean checkdivision(BigInteger a) {
//        if (this.n.mod(a) == 0) {
//
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//}
//class Test{
//    public static void main(String[] args) {
//        W5C1_1 w = new W5C1_1(21);
//        System.out.println(w.outputprimefactors());
//    }
//}