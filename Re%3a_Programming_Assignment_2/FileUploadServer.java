import java.net.Socket;
import java.net.ServerSocket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.KeyFactory;
import java.security.PrivateKey;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;

public class FileUploadServer{
	private static final String fileName = "out.txt";
	public static void main(String[] args) throws Exception{
		try{
			//Initialize sockets
			ServerSocket server = new ServerSocket(42423);
			System.out.println("Server is open");
			Socket clientSocket = server.accept();
			
			InputStream in = clientSocket.getInputStream();
			BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
			//For writing to client
			DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
			PrintWriter print = new PrintWriter(clientSocket.getOutputStream());
			
			//Wait for client
			String line;
			line = buffer.readLine();
			if(line.equals("Hello SecStore, please prove your identity!")){
				print.println("Hello, this is SecStore!");
				print.flush();
			}
			
			//AP protocol
			//Loading private key from .der
			File f = new File("privateServer.der");
			DataInputStream dis = new DataInputStream(new FileInputStream(f)); 
			byte[] privateByte = new byte[(int)f.length()];
			dis.readFully(privateByte);
			//dis.close();
			
			//Loading public key from .der
			File f1 = new File("publicServer.der");
			dis = new DataInputStream(new FileInputStream(f1)); 
			privateByte = new byte[(int)f1.length()];
			dis.readFully(privateByte);
			//dis.close();
			
			//Encoding message with private key
			PKCS8EncodedKeySpec pkcs = new PKCS8EncodedKeySpec(privateByte);
			KeyFactory kf = KeyFactory.getInstance("RSA");
			PrivateKey pk = kf.generatePrivate(pkcs);
			
			//Initialize cipher
			Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			rsaCipher.init(Cipher.ENCRYPT_MODE,pk);
			
			//Extra step: transfer of random byte array between client and server
			//Used to ensure that previous communications are not repeated unknowingly
			String byteLen = buffer.readLine();
			byte[] hold = new byte[Integer.valueOf(byteLen)];
			testByte(hold,in);
			System.out.println("random byte array received from Client");
			
			//Encrypt byte array sent by Client
			byte[] encrypted = rsaCipher.doFinal(hold);
			OutputStream os = clientSocket.getOutputStream();
			os.write(encrypted,0,encrypted.length);
			os.flush();
			System.out.println("Returning random byte array back to Client");
			
			//Loading certificate
			File certificate = new File("1001763.crt");
			FileInputStream cert = new FileInputStream(certificate);
			byte[] certHold = new byte[(int)certificate.length()];
			cert.read(certHold);
			
			//Wait for client
			line = buffer.readLine();
			if(line.equals("Give me your certificate signed by CA!")){
				out.writeInt(certHold.length);
				out.flush();
				out.write(certHold,0,certHold.length);
				out.flush();
			}
			
			//Get input from client
			//check handshake with client
			line = buffer.readLine();
			if(line.contains("Bye!")){ 
				print.close();
				os.close();
				buffer.close();
				
				in.close();
				clientSocket.close();
				server.close();
			}
			
			//start file upload
			decryptClientFile(print,buffer,in,pk);
			print.println("Upload Successful!");
			print.flush();
			
			print.close();
			os.close();
			buffer.close();
			
			in.close();
			clientSocket.close();
			server.close();
		}
		catch(Exception e){
			
		}
	}
	
	public static void decryptClientFile(PrintWriter print,BufferedReader buffer,InputStream in,PrivateKey pk){
		try{
			//session key from client
			String sessionKey = buffer.readLine();
			byte[] encryptAES = new byte[Integer.valueOf(sessionKey)];
			testByte(encryptAES,in);
			
			//Get encrypted file from client
			String fileName = buffer.readLine();
			String fileLength = buffer.readLine();
			print.println("Receiving encrypted file");
			print.flush();
			byte[] encryptByte = new byte[Integer.valueOf(fileLength)];
			testByte(encryptByte,in);
			
			//Initialize cipher for decrypting AES key
			Cipher rsaCipherDe = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			rsaCipherDe.init(Cipher.DECRYPT_MODE,pk);
			byte[] aesKey = rsaCipherDe.doFinal(encryptAES);
			
			//regenerate AES key
			SecretKey aesSecret = new SecretKeySpec(aesKey,0,aesKey.length,"AES");
			//Cipher for decryption of file
			Cipher aesCipherDe = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			aesCipherDe.init(Cipher.DECRYPT_MODE,aesSecret);
			
			//decrypt AES file
			byte[] rawBytes = aesCipherDe.doFinal(encryptByte);
			FileOutputStream fileOut = new FileOutputStream(fileName);
			fileOut.write(rawBytes,0,rawBytes.length);
			System.out.println("File saved!");
		}
		catch(Exception e){
			
		}
	}
	
	public static boolean testByte(byte[] empty, InputStream in){
		try{
			//modelling InputStream.read method
			int off = 0;
			int numBytes = 0;
			while(off<empty.length && (numBytes=in.read(empty,off,empty.length-off)) >= 0){
				off+=numBytes;
			}
			if(off < empty.length){
				return true;
			}
			
		}
		catch(Exception e){
			
		}
		return false;
	}
}