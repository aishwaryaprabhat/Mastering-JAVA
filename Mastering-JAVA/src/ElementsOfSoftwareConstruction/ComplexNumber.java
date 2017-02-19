package ElementsOfSoftwareConstruction;

public class ComplexNumber
{

    private final double real ;
    private final double imaginary ;




    ComplexNumber(double real, double imaginary){
        this.real = real;
        this.imaginary = imaginary;
    }

    public static ComplexNumber addc(ComplexNumber a, ComplexNumber b){
        double realsum = a.real+b.real;
        double imagsum = a.imaginary+b.imaginary;
        return new ComplexNumber(realsum,imagsum);
    }

    public static ComplexNumber subtractc(ComplexNumber a, ComplexNumber b){
        double realsub = a.real-b.real;
        double imagsub = a.imaginary-b.imaginary;
        return new ComplexNumber(realsub,imagsub);
    }

    public static ComplexNumber multc(ComplexNumber a, ComplexNumber b){
        double realmult = (a.real*b.real)+(a.imaginary*b.imaginary);
        double imagmult = (a.imaginary*b.real)+(a.imaginary*b.real);
        return new ComplexNumber(realmult,imagmult);
    }

    public static ComplexNumber divc(ComplexNumber divisor, ComplexNumber dividend){
        ComplexNumber div_conj = ComplexNumber.conjugate(divisor);
        ComplexNumber answer = ComplexNumber.multc(dividend,div_conj);
        return answer;
    }

    public static ComplexNumber conjugate(ComplexNumber a){
        double denom = Math.pow(a.real,2)-Math.pow(a.imaginary,2);
        double realpart = a.real/denom;
        double imagpart = -a.imaginary/denom;
        return new ComplexNumber(realpart,imagpart);
    }

    public static double[] toDoubleArray(ComplexNumber a){
        double[] answer = {a.real,a.imaginary};
        return answer;
    }

    public static String toString(ComplexNumber a){
        String realpart = Double.toString(a.real);
        String imaginarypart = Double.toString(a.imaginary);
        String answer = realpart+"+"+imaginarypart+"i";
        return answer;
    }
}

class TestComplex{
    TestComplex(){}
    public static void main(String[] args){
        ComplexNumber a = new ComplexNumber(5.0, 6.0);
        ComplexNumber b = new ComplexNumber(-3.0, 4.0);
        //run tests
        System.out.println(ComplexNumber.toString(ComplexNumber.addc(a,b)));
        System.out.println(ComplexNumber.toString(ComplexNumber.subtractc(a,b)));
        System.out.println(ComplexNumber.toString(ComplexNumber.multc(a,b)));
        System.out.println(ComplexNumber.toString(ComplexNumber.divc(a,b)));
    }
}