
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;

public class Done_CP2SecStore {

    public final static int SOCKET_PORT = 13267;

    public final static String
            FILE_TO_RECEIVE = "received_file";
    public final static String
            SERVER_CERT = "1001763.crt";

    public final static int FILE_SIZE = 6022386; // file size temporary hard coded
    // should bigger than the file to be downloaded

    public static void main (String [] args ) throws Exception {
        int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        FileInputStream fis;
        BufferedInputStream bis = null;
        ServerSocket servsock = null;
        Socket sock = null;
        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        PrivateKey privKey;
        byte[] encryptedNonce;
        byte[] byteArray;
        Cipher rsaCipher;
        Path path = Paths.get("/Users/ongteckwu/50.005/certificate_request/privateServer.der");
        byteArray = Files.readAllBytes(path);
        EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(byteArray);


        KeyFactory kf = KeyFactory.getInstance("RSA");
        privKey = kf.generatePrivate(keySpec);
        rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        rsaCipher.init(Cipher.ENCRYPT_MODE, privKey);


        try {
            servsock = new ServerSocket(SOCKET_PORT);
            System.out.println("Waiting for connection...");
            while (true) {
                try {
                    sock = servsock.accept();
                    System.out.println("Accepted connection : " + sock);
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

                    if (certReq != 1) {
                        System.out.println("No cert request");
                        break;
                    }
                    System.out.println("Certificate request received");

                    // send server cert
                    InputStream fisCA = new FileInputStream (SERVER_CERT);
                    Certificate myCert = cf.generateCertificate(fisCA);
                    os.write(myCert.getEncoded());
                    System.out.println("Certificate sent");

                    int sessionKeyReq = is.read();

                    if (sessionKeyReq != 1) {
                        System.out.println("No sess key request");
                        break;
                    }
                    System.out.println("Session key request received");

                    KeyGenerator keyGen = KeyGenerator.getInstance("AES");
                    SecretKey sessionKey = keyGen.generateKey();

                    byte[] encryptedSessionKey = rsaCipher.doFinal(sessionKey.getEncoded());
                    os.write(encryptedSessionKey);
                    // decrypt file
                    Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                    aesCipher.init(Cipher.DECRYPT_MODE, sessionKey);

                    // receive file
                    fos = new FileOutputStream(FILE_TO_RECEIVE);
                    String data;
                    BufferedReader in = new BufferedReader(new InputStreamReader(is));
                    while ((data = in.readLine()) != null) {
                        fos.write(aesCipher.doFinal(DatatypeConverter.parseBase64Binary(data)));
                    }
                    in.close();
                } finally {
                    if (fos != null) fos.close();
                    if (bos != null) bos.close();
                    if (sock != null) sock.close();
                }
            }
        }
        finally {
            if (servsock != null) servsock.close();
        }
    }
}
