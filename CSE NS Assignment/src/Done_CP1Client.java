
import java.io.*;
import java.net.Socket;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;

public class Done_CP1Client {

    public final static int SOCKET_PORT = 13267;
    public final static String SERVER = "10.12.91.74";
    public final static String FILE_TO_SEND = "largeFile.txt";  // you may change this
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
            InputStream fisServer = new FileInputStream("CA.crt");
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


            // send file

            File myFile = new File (FILE_TO_SEND);
            byte[] bytesArray  = new byte [(int)myFile.length()];
            fisServer = new FileInputStream(myFile);
            bis = new BufferedInputStream(fisServer);

            System.out.println("Sending " + FILE_TO_SEND + "(" + bytesArray.length + " bytes)");
            rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            rsaCipher.init(Cipher.ENCRYPT_MODE, ServerKey);

            PrintWriter out = new PrintWriter(os);
            String data;
            byte[] buffer = new byte[117];
            byte[] enc;
            int count;
            long startTime = System.currentTimeMillis();
            while((count = bis.read(buffer))>0){
                if(count<buffer.length){
                    enc = rsaCipher.doFinal(Arrays.copyOf(buffer, count));
                }
                else{
                    enc = rsaCipher.doFinal(buffer);
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

