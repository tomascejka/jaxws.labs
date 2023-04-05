package cz.tc.learn.ws.mtom.binary;

import cz.tc.learn.ws.mtom.binary.api.FileStorageBinaryPort;
import cz.tc.learn.ws.mtom.binary.types.FileBinaryType;
import java.util.Base64;
import javax.xml.ws.Endpoint;
import javax.xml.ws.soap.MTOMFeature;
import org.junit.*;

/**
 * Otestuje, ze server nevraci MTOM vubec! Server neni nakonfigurovan, aby MTOM vracel.
 * A to dokonce i v pripade, ze klient si MTOM zada via klientskou konfiguraci!
 * 
 * @author tomas.cejka
 */
public class FileStorageBinaryServiceImplTest extends BaseBinaryTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        // vypise xml v sysout
        System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
        System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
        // -- aby vypis mohl byt hrozne dlouhej
        System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dumpTreshold", "999999999");
        System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dumpTreshold", "999999999");
        endpoint = Endpoint.publish(URL_SERVICE, new FileStorageBinaryServiceImpl());
    }

    @Test
    public void test_SmallDownloadBinary() throws Exception {
        String filename=FileStorageBinaryServiceImpl.SMALL_SAMPLE_FILE_XML;
        FileBinaryType file = port.downloadBinary(filename);
        
        Assert.assertEquals(filename, file.getFileName());
        Assert.assertEquals("PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4KPHJvb3Q+CiAgICA8Ym9va3M+CiAgICAgICAgPGJvb2s+CiAgICAgICAgICAgIDx0aXRsZT5OYXpldjwvdGl0bGU+CiAgICAgICAgICAgIDxhdXRob3I+QXV0aG9yPC9ib29rPgogICAgPC9ib29rcz4KPC9yb290PgoK",
                new String(Base64.getEncoder().encode(file.getContentData())));
    }    
    
    @Test
    public void test_DownloadBinary() throws Exception {
        String filename=FileStorageBinaryServiceImpl.SAMPLE_FILE_XML;
        FileBinaryType file = port.downloadBinary(filename);
        Assert.assertEquals(filename, file.getFileName());
    }

    // request je MTOM-based (via MTOMFeature), ale response NIKOLI
    @Test
    public void testTryMtomFeature_SmallDownloadBinary() throws Exception {
        String filename=FileStorageBinaryServiceImpl.SMALL_SAMPLE_FILE_XML;
        MTOMFeature feature = new MTOMFeature(true);
        port = service.getPort(portClazz, feature);
        FileBinaryType file = port.downloadBinary(filename);
        Assert.assertEquals(filename, file.getFileName());
    }
    
    // request je MTOM-based (via MTOMFeature), ale response NIKOLI
    @Test
    public void testTryMtomFeature_DownloadBinary() throws Exception {
        String filename=FileStorageBinaryServiceImpl.SAMPLE_FILE_XML;
        MTOMFeature feature = new MTOMFeature(true, 6000);
        port = service.getPort(portClazz, feature);
        FileBinaryType file = port.downloadBinary(filename);
        Assert.assertEquals(filename, file.getFileName());
    }  
}
