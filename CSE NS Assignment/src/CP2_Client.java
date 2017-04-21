import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.Socket;
import java.security.*;
import java.security.cert.*;
import java.util.Arrays;

public class CP2_Client {

    public final static int SOCKET_PORT = 13267;
    public final static String SERVER = "localhost";
    //public final static String SERVER = "10.12.91.74";  // change to desired IP
    public final static String FILE_TO_SEND = "smallFile.txt";  // change this


    public static void main(String[] args) throws IOException, CertificateException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, SignatureException, NoSuchProviderException, InvalidKeyException, IllegalBlockSizeException {

        Socket sock = null;
        try {
            sock = new Socket(SERVER, SOCKET_PORT);
            System.out.println("Connecting...");

            //authenticate and send file
            authenticate_server(sock);

        }finally {
            if (sock != null) sock.close();
        }

    }

    public static boolean authenticate_server(Socket sock) throws IOException, CertificateException, NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {

        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        OutputStream  os = sock.getOutputStream();
        InputStream is = sock.getInputStream();

        byte[] nonce = new byte[16];
        byte[] response = new byte[128];
        int request_for_cert = 1;
        SecureRandom random = new SecureRandom();
        random.nextBytes(nonce);


        //send nonce
        os.write(nonce);
        System.out.println("Nonce sent");
        //wait to receive encrypted nonce
        is.read(response);
        System.out.println("Nonce received");
        // ask for cert
        os.write(request_for_cert);
        System.out.println("Requesting Cert");

        //get CA cert
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        FileInputStream fisServer = new FileInputStream("CA.crt");
        X509Certificate CAcert = (X509Certificate)cf.generateCertificate(fisServer);
        X509Certificate serverCert = (X509Certificate) cf.generateCertificate(is); //input stream
        System.out.println("Cert received");
        PublicKey CAkey = CAcert.getPublicKey();

        serverCert.checkValidity();
        //verify ServerCert using CAkey
        serverCert.verify(CAkey);

        //Extract public key of server cert
        PublicKey ServerKey = serverCert.getPublicKey();

        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        rsaCipher.init(Cipher.DECRYPT_MODE, ServerKey);
        byte[] decryptedNonce = rsaCipher.doFinal(response);


        //check if nonce matches the decryptedNonce
        if (Arrays.equals(nonce, decryptedNonce)){
            System.out.println("Nonce matched");
            sendfile(sock,decryptedNonce);
            return true;
        }
        else{
            System.out.println("Nonce did not match");
            fos.close();
            bos.close();
            is.close();
            os.close();
            return false;
        }

    }


    public static void sendfile(Socket sock,byte[] ServerKey) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        InputStream fisServer = new FileInputStream(FILE_TO_SEND);
        bis = new BufferedInputStream(fisServer);


        try{
            // send file
            File myFile = new File (FILE_TO_SEND);
            byte [] mybytearray  = new byte [(int)myFile.length()];

            //encrypt before sending


            Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKey sk = new SecretKeySpec(ServerKey,0,ServerKey.length,"AES");
            aesCipher.init(Cipher.ENCRYPT_MODE, sk);
            os = sock.getOutputStream();

            bis = new BufferedInputStream(fisServer);
            PrintWriter out = new PrintWriter(os);
            String data;
            byte[] buffer = new byte[117];
            byte[] enc;
            int count;
            long startTime = System.currentTimeMillis();
            while((count = bis.read(buffer))>0){
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

            System.out.println("Done.");
        } finally {
            if (bis != null) bis.close();
            if (os != null) os.close();

        }
    }
}