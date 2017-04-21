import java.util.Random;
import java.io.InputStream;

public class RandomByteGenerator{
	Random rand;
	byte[] buff;
	public RandomByteGenerator(){
		this.rand = new Random();
		
		buff = new byte[128];
		
		rand.nextBytes(buff);
	}
	
	public byte[] getByteArray(){
		return buff;
	}
}