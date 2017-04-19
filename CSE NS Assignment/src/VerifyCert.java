import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * Created by Lenovo on 4/18/2017.
 */
public class VerifyCert {

    public static void main(String[] args) {

    }

    void verify() throws FileNotFoundException, CertificateException {

        InputStream fis = new FileInputStream("CA.crt");
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate CAcert = (X509Certificate)cf.generateCertificate(fis);

    }
}
