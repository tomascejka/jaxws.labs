package cz.tc.learn.ws.mtom.stream;

import cz.tc.learn.ws.mtom.stream.api.FileStorageStreamPort;
import cz.tc.learn.ws.mtom.stream.types.FileStreamType;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;
import javax.xml.ws.soap.MTOMFeature;
import org.junit.*;

/**
 * @author tomas.cejka
 */
public class FileStorageStreamServiceImplV2Test {

    private static Logger LOG = Logger.getLogger(FileStorageStreamServiceImplV2Test.class.getName());
    private static final String URL_SERVICE = "http://localhost:8081/mtom.ws/fileStorageStreamService";
    
    private static Endpoint endpoint;
    private Service service;
    private FileStorageStreamPort port;
    private final Class<FileStorageStreamPort> portClazz = FileStorageStreamPort.class;

    @BeforeClass
    public static void beforeClass() throws Exception {
        // vypise xml v sysout
        System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
        System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
        // -- aby vypis mohl byt hrozne dlouhej
        System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dumpTreshold", "999999999");
        System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dumpTreshold", "999999999");
        endpoint = Endpoint.publish(URL_SERVICE, new FileStorageStreamServiceImplV2());
    }

    @AfterClass
    public static void afterClass() throws Exception {
        if (endpoint != null) {
            endpoint.stop();
        }
    }
    
    @Before
    public void setUp() throws Exception {
        // ns=viz. wsdl /definitions[@targetNamespace], localpart=viz. wsdl /definitions/service[@name]
        QName serviceName = new QName("http://ws.learn.tc.cz/mtom/stream/api", "fileStorageStreamService");
        service = Service.create(new URL(URL_SERVICE + "?wsdl"), serviceName);
        port = service.getPort(portClazz);
        System.out.println("=================================================\n");
    }  

    @Test
    public void test_SmallDownloadBinary() throws Exception {
        String filename=FileStorageStreamServiceImpl.SMALL_SAMPLE_FILE_XML;
        FileStreamType file = port.downloadStream(filename);
        Assert.assertEquals(filename, file.getFileName());
    }
    
    @Test
    public void test_DownloadBinary() throws Exception {
        String filename=FileStorageStreamServiceImpl.SAMPLE_FILE_XML;
        FileStreamType file = port.downloadStream(filename);
        Assert.assertEquals(filename, file.getFileName());
    }

    @Test
    public void testTryMtomFeature_SmallDownloadBinary() throws Exception {
        String filename=FileStorageStreamServiceImpl.SMALL_SAMPLE_FILE_XML;
        MTOMFeature feature = new MTOMFeature(true);
        port = service.getPort(portClazz, feature);
        FileStreamType file = port.downloadStream(filename);
        Assert.assertEquals(filename, file.getFileName());
    }
    
    @Test
    public void testTryMtomFeature_DownloadBinary() throws Exception {
        String filename=FileStorageStreamServiceImpl.SAMPLE_FILE_XML;
        MTOMFeature feature = new MTOMFeature(true, 6000);
        port = service.getPort(portClazz, feature);
        FileStreamType file = port.downloadStream(filename);
        Assert.assertEquals(filename, file.getFileName());
    }  
}
