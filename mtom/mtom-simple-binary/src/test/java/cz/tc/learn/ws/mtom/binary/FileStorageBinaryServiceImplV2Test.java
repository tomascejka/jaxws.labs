package cz.tc.learn.ws.mtom.binary;

import cz.tc.learn.ws.mtom.binary.types.FileBinaryType;
import javax.xml.ws.Endpoint;
import javax.xml.ws.soap.MTOMFeature;
import org.junit.*;

/**
 * @author tomas.cejka
 */
public class FileStorageBinaryServiceImplV2Test extends BaseBinaryTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        // vypise xml v sysout
        System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
        System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
        // -- aby vypis mohl byt hrozne dlouhej
        System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dumpTreshold", "999999999");
        System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dumpTreshold", "999999999");
        endpoint = Endpoint.publish(URL_SERVICE, new FileStorageBinaryServiceImplV2());
    } 

    @Test
    public void test_SmallDownloadBinary() throws Exception {
        String filename=FileStorageBinaryServiceImpl.SMALL_SAMPLE_FILE_XML;
        FileBinaryType file = port.downloadBinary(filename);
        Assert.assertEquals(filename, file.getFileName());
    }
    
    @Test
    public void test_DownloadBinary() throws Exception {
        String filename=FileStorageBinaryServiceImpl.SAMPLE_FILE_XML;
        FileBinaryType file = port.downloadBinary(filename);
        Assert.assertEquals(filename, file.getFileName());
    }
    
    @Test
    public void testTryMtomFeature_SmallDownloadBinary() throws Exception {
        String filename=FileStorageBinaryServiceImpl.SMALL_SAMPLE_FILE_XML;
        MTOMFeature feature = new MTOMFeature(true, 60);
        port = service.getPort(portClazz, feature);
        FileBinaryType file = port.downloadBinary(filename);
        Assert.assertEquals(filename, file.getFileName());
    }
    
    @Test
    public void testTryMtomFeature_DownloadBinary() throws Exception {
        String filename=FileStorageBinaryServiceImpl.SAMPLE_FILE_XML;
        MTOMFeature feature = new MTOMFeature(true, 60);
        port = service.getPort(portClazz, feature);
        FileBinaryType file = port.downloadBinary(filename);
        Assert.assertEquals(filename, file.getFileName());
    }
    
}
