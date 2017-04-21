import java.io.*;
import java.net.Socket;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class Done_CP2Client {

    public final static int SOCKET_PORT = 13267;  // you may change this
    public final static String SERVER = "10.12.54.168";  // enter IP address of localhost here
    public final static String FILE_TO_SEND = "C:/Users/Anushka/Desktop/2.5mil.txt";  // you may change this
    public final static int FILE_SIZE = 6022386; // file size temporary hard coded


    public static void main (String [] args ) throws Exception {
        //FileInputStream fis;
        BufferedInputStream bis = null;
        OutputStream os = null;
        InputStream is = null;
        Socket sock = null;
        //ByteArrayOutputStream buffer;
        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        byte[] nonce = new byte[16];
        byte[] response = new byte[128];
        int askCert = 1;
        SecureRandom random = new SecureRandom();
        random.nextBytes(nonce);
        try {
            sock = new Socket(SERVER, SOCKET_PORT);
            System.out.println("Connecting...");
            //send nonce
            os = sock.getOutputStream();
            is = sock.getInputStream();
            os.write(nonce);
            System.out.println("sent nonce");
            //wait to receive encrypted nonce
            is.read(response);
            System.out.println("received nonce");
            // ask for cert
            os.write(askCert);
            System.out.println("sent request for cert");

            //get CA cert
            InputStream fisServer = new FileInputStream("C:/Users/Anushka/Downloads/50.005 Computer Systems Engineering/CA.crt");
            X509Certificate CAcert = (X509Certificate)cf.generateCertificate(fisServer);
            X509Certificate serverCert = (X509Certificate) cf.generateCertificate(is); //input stream
            System.out.println("received cert");
            PublicKey CAkey = CAcert.getPublicKey();

            serverCert.checkValidity();
            //verify ServerCert using CAkey
            serverCert.verify(CAkey);

            //Extract public key of server cert
            PublicKey ServerKey = serverCert.getPublicKey();

            Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            rsaCipher.init(Cipher.DECRYPT_MODE, ServerKey);
            byte[] decryptedNonce = rsaCipher.doFinal(response);

            if (Arrays.equals(nonce, decryptedNonce)){
                System.out.println("matched nonce");
            }
            else{
                System.out.println("nonce did not match");
                return;
            }

            //after nonce matches, client must request for a session key
            int askSessionKey = 1;
            os.write(askSessionKey);
            System.out.println("sent request for session key");

            //server will send session key encrypted with private key
            byte[] responseSessionKey = new byte[128];
            is.read(responseSessionKey);
            System.out.println("received encryped session key");

            //client must decrypt session key using public key extracted from the cert
            Cipher rsaCipherSK = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            rsaCipherSK.init(Cipher.DECRYPT_MODE, ServerKey);
            byte[] decryptedSessionKey = rsaCipher.doFinal(responseSessionKey);


            // send file

            File myFile = new File (FILE_TO_SEND);
            byte[] bytesArray  = new byte [(int)myFile.length()];
            fisServer = new FileInputStream(myFile);
            bis = new BufferedInputStream(fisServer);

            System.out.println("Sending " + FILE_TO_SEND + "(" + bytesArray.length + " bytes)");
            Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKey originalKey = new SecretKeySpec(decryptedSessionKey, 0, decryptedSessionKey.length, "AES");
            aesCipher.init(Cipher.ENCRYPT_MODE, originalKey);

            PrintWriter out = new PrintWriter(os);
            String data;
            byte[] buffer = new byte[117];
            byte[] enc;
            int count;
            long startTime = System.currentTimeMillis();
            while((count = bis.read(buffer))>0){
                //System.out.println("sent .txt");
                if(count<buffer.length){
                    enc = aesCipher.doFinal(Arrays.copyOf(buffer, count));
                }
                else{
                    enc = aesCipher.doFinal(buffer);
                }
                data = DatatypeConverter.printBase64Binary(enc);
                out.println(data);
                out.flush();
            }
            double totalTime = (System.currentTimeMillis() - startTime)/1000.0;
            System.out.printf("Total time: %.4fs\n", totalTime);

            System.out.println("Done.");
        }
        finally {
            if (bis != null) bis.close();
            if (os != null) os.close();
            if (sock != null) sock.close();
        }
    }
}

