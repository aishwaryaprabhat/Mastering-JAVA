import java.net.Socket;
import java.net.ServerSocket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedInputStream;
import java.io.File;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import javax.crypto.KeyGenerator;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ByteArrayInputStream;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.KeyFactory;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;

public class FileUploadClient{
	private static final String serverIp = "localhost";
			//"10.12.255.182";
	private static final int connectPort = 42423;
	private static final String fileName = "smallFile.txt";
	private static final String CAcertfile = "CA.crt";
	public static void main(String[] args) throws Exception{
		try{
			//Initialize sockets
			Socket socket = new Socket(serverIp,connectPort);
			
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			DataInputStream is = new DataInputStream(in);
			DataOutputStream os = new DataOutputStream(out);
			
			BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
			PrintWriter print = new PrintWriter(out);
			
			//Start conversation
			out.write(1);
			print.println("Hello SecStore, please prove your identity!");
			print.flush();
			
			//Wait for server to respond
			String reply = buffer.readLine();
			System.out.println(reply);
			
			//AP protocol
			//Generating random array
			RandomByteGenerator rand = new RandomByteGenerator();
			byte[] randByte = rand.getByteArray();
			if(reply.contains("this is")){
				print.println(Integer.toString(randByte.length));
				os.write(randByte,0,randByte.length);
				os.flush();
			}
			
			//Receive byte array from server
			String byLen = buffer.readLine();
			byte[] byteHold = new byte[Integer.parseInt(byLen)];
			testByte(byteHold,in);
			
			//ask Server for certificate
			print.println("Give me your certificate signed by CA!");
			print.flush();
			
			//Extract public key from CAcert
			InputStream fis = new FileInputStream(CAcertfile);
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			X509Certificate caCert =(X509Certificate)cf.generateCertificate(fis);
			PublicKey key = caCert.getPublicKey();
			System.out.println("Public Key extracted");
			
			//Received server's signed cert
			String severCertlen = buffer.readLine();
			print.println("Getting server's signed certificate");
			print.flush();
			byte[] certByte = new byte[Integer.parseInt(severCertlen)];
			testByte(certByte,in);
			
			//Checking server's cert is valid
			InputStream certInput = new ByteArrayInputStream(certByte);
			X509Certificate signCert =(X509Certificate)cf.generateCertificate(certInput);
			signCert.checkValidity();
			signCert.verify(key);
			System.out.println("Server's certificate is valid");
			
			//Extract public key from server's cert
			PublicKey pkey = signCert.getPublicKey();	
			
			//Initialize cipher
			Cipher cipherDec = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipherDec.init(Cipher.DECRYPT_MODE,pkey);
			
			//Decrypt random byte array from server
			byte[] decryptByte = cipherDec.doFinal(byteHold);
			if(Arrays.equals(randByte,byteHold)){
				print.println("Preparing to upload file");
				print.flush();
			}
			else{
				print.println("Bye!");
				print.close();
				os.close();
				buffer.close();
				in.close();
				out.close();
				socket.close();
			}
						
			//start file transfer to server
			System.out.println("File uploading to server");
			Long startTime = System.currentTimeMillis();
			encryptClientFile(print,buffer,out,pkey);
			//Check for "Upload Successful!" reply from server
			System.out.println(buffer.readLine());
			Long endTime = System.currentTimeMillis();
			System.out.println("Time spent: " + (endTime-startTime) + "ms");
			
			print.close();
			os.close();
			buffer.close();
			in.close();
			out.close();
			socket.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void encryptClientFile(PrintWriter print,BufferedReader buffer,OutputStream out,PublicKey pkey){
		try{
			//Initialize cipher for encryption
			Cipher cipherEn = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipherEn.init(Cipher.ENCRYPT_MODE,pkey);
			
			//generate AES key
			KeyGenerator keygen = KeyGenerator.getInstance("AES");
			keygen.init(128);
			SecretKey aesKey = keygen.generateKey();
			byte[] encryptBytes = aesKey.getEncoded();
			
			//create cipher for file encrypting
			Cipher cipherAES = Cipher.getInstance("AES/ECB/PKCS1Padding");
			cipherAES.init(Cipher.ENCRYPT_MODE,aesKey);
			
			//Encrypt file with AES key
			File file = new File(fileName);
			byte[] fileByt = new byte[(int)file.length()];
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			bis.read(fileByt,0,fileByt.length);
			byte[] encryptFile = cipherAES.doFinal(fileByt);
			
			//send to server shared session key
			print.println(encryptBytes.length);
			print.flush();
			out.write(encryptBytes,0,encryptBytes.length);
			out.flush();
			System.out.println("Sent to server shared session key");
			
			print.println(fileName);
			print.println(encryptFile.length);
			print.flush();
			System.out.println(buffer.readLine());
			out.write(encryptFile,0,encryptFile.length);
			out.flush();
			System.out.println("Done Uploading");
		}
		catch(Exception e){
			e.printStackTrace();
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