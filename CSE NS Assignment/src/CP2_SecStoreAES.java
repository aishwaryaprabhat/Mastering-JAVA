import javax.crypto.*;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class CP2_SecStoreAES {

    public final static int SOCKET_PORT = 13267;  // you may change this
    public final static String FILE_TO_RECEIVE = "RSA_received_file" ;

    public final static String SERVER_CERT = "1001763.crt";

    public final static int FILE_SIZE = 6022386;

    public static void main(String[] args) throws IOException, CertificateException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, InvalidKeySpecException {

        ServerSocket servsock = null;
        Socket sock = null;
        try {
            servsock = new ServerSocket(SOCKET_PORT);
            while (true) {
                System.out.println("Waiting...");
                try {
                    sock = servsock.accept();
                    System.out.println("Accepted connection : " + sock);
                    authenticate_and_receive(sock);

                } finally {
                    if (sock != null) sock.close();
                }
            }
        } finally {

            if (servsock != null) servsock.close();
        }

    }


    public static void authenticate_and_receive(Socket sock) throws CertificateException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        PrivateKey privKey;
        byte[] encryptedNonce;
        byte[] byteArray;
        Cipher rsaCipher;
        Path path = Paths.get("privateServer.der");
        byteArray = Files.readAllBytes(path);
        EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(byteArray);


        KeyFactory kf = KeyFactory.getInstance("RSA");
        privKey = kf.generatePrivate(keySpec);
        rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        rsaCipher.init(Cipher.ENCRYPT_MODE, privKey);


        InputStream is = sock.getInputStream();
        OutputStream os = sock.getOutputStream();

        // receive nonce
        byte[] nonce = new byte[16];
        System.out.println("Nonce read");

        // encrypt nonce
        is.read(nonce);
        encryptedNonce = rsaCipher.doFinal(nonce);
        System.out.println(encryptedNonce.length);
        System.out.println("Nonce encrypted");

        // send encrypted nonce
        os.write(encryptedNonce);
        System.out.println("Encrypted nonce sent");

        int certReq = is.read();
        System.out.println(certReq);

        if (certReq != 1) {
            System.out.println("No pending cert request");
            return;
        }
        System.out.println("Certificate request received");

        // send server cert
        InputStream fisCA = new FileInputStream(SERVER_CERT);
        Certificate myCert = cf.generateCertificate(fisCA);
        os.write(myCert.getEncoded());
        System.out.println("Certificate sent");

        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        SecretKey secretKey = keygen.generateKey();

        byte[] encryptedkey = rsaCipher.doFinal(secretKey.getEncoded());
        os.write(encryptedkey);
        // decrypt file
        Cipher decaesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        decaesCipher.init(Cipher.DECRYPT_MODE, secretKey);

        // receive file
        fos = new
                FileOutputStream(FILE_TO_RECEIVE);
        String data;
        int input_size = is.available();
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        while ((data = in.readLine()) != null) {


            fos.write(decaesCipher.update(DatatypeConverter.parseBase64Binary(data)));
        }
        in.close();


    }
    public static byte[] removeTrailingZeros( byte[] str ){
        if (str == null){
            return null;}
        int length,index ;length = str.length;
        index = length -1;
        for (; index >=0;index--)
        {
            if (str[index] != 0){
                break;}
        }
        byte[] result = new byte[index + 1];
        for(int i = 0; i < index + 1; ++i) {
            result[i] = str[i];
        }
        return result;
    }
}

//    public static void receivefile(Socket sock) throws IOException {
//
//        // decrypt file
//
//        rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//        rsaCipher.init(Cipher.DECRYPT_MODE, privKey);
//
//        // receive file
//        fos = new FileOutputStream(FILE_TO_RECEIVE);
//        String data;
//        BufferedReader in = new BufferedReader(new InputStreamReader(is));
//        while ((data = in.readLine()) != null) {
//            fos.write(rsaCipher.doFinal(DatatypeConverter.parseBase64Binary(data)));
//        }
//        in.close();
//
//        int bytesRead;
//        int current = 0;
//        FileOutputStream fos = null;
//        BufferedOutputStream bos = null;
//
//
//
//        try{
//            // receive file
//            byte[] mybytearray = new byte[FILE_SIZE];
//            InputStream is = sock.getInputStream();
//            fos = new FileOutputStream(FILE_TO_RECEIVED);
//            bos = new BufferedOutputStream(fos);
//            bytesRead = is.read(mybytearray, 0, mybytearray.length);
//            current = bytesRead;
//
//            do {
//                bytesRead =
//                        is.read(mybytearray, current, (mybytearray.length - current));
//                if (bytesRead >= 0) current += bytesRead;
//            } while (bytesRead > -1);
//
//            bos.write(mybytearray, 0, current);
//            bos.flush();
//            System.out.println("File " + FILE_TO_RECEIVED
//                    + " downloaded (" + current + " bytes read)");
//        } finally {
//            if (fos != null) fos.close();
//            if (bos != null) bos.close();
//
//        }
//    }