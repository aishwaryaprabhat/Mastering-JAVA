import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*
 * Apply SPMD (Single Program, Multiple Data) design pattern for concurrent programming to parallelize the program which 
 * approximates $\pi$ by integrating the following formula $4/(1+x^2 )$. Hint: In the SPMD design pattern, all threads 
 * run the same program, operating on different data.
 */
public class Exercise1 {
	public static void main(String[] args) throws Exception {

		int NTHREADS = 5;
		ArrayList<Future> futures = new ArrayList<>();
		ExecutorService exec = Executors.newFixedThreadPool(NTHREADS - 1);

		// todo: complete the program by writing your code below.
		double initial_value = 0; //integrate from
		double final_value = 1; //integrate upto
		double interval = (final_value-initial_value)/NTHREADS;
		for(int i=0;i<NTHREADS;i++){
			Future result = exec.submit(new Integrate(initial_value,initial_value+interval));
			initial_value+=interval;
			futures.add(result);
		}


		Double sum = 0.0;
		for(Future f:futures){
			sum+= (Double)f.get();
		}



		exec.shutdown();
		System.out.println(sum);
	}



}

class Integrate implements Callable{

	double a=0;
	double b=0;

	Integrate(double a, double b){
		this.a = a;
		this.b = b;
	}
	@Override
	public Double call() throws Exception {
		return integrate(this.a,this.b);
	}

	public static double f(double x) {
		return 4.0 / (1 + x * x);
	}

	// the following does numerical integration using Trapezoidal rule.
	public static double integrate(double a, double b) {
		int N = 10000; // preciseness parameter
		double h = (b - a) / (N - 1); // step size
		double sum = 1.0 / 2.0 * (f(a) + f(b)); // 1/2 terms

		for (int i = 1; i < N - 1; i++) {
			double x = a + h * i;
			sum += f(x);
		}

		return sum * h;
	}
}
