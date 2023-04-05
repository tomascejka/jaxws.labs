package cz.tc.learn.ws.mtom.binary;

//import com.sun.xml.internal.ws.developer.StreamingAttachmentFeature;
import cz.tc.learn.ws.mtom.binary.api.FileStorageBinaryPort;
import cz.tc.learn.ws.mtom.binary.types.FileBinaryType;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;
import org.junit.*;

/**
 *
 * @author tomas.cejka
 */
public class FileStorageBinaryServiceImplV4Test {

    private static Logger LOG = Logger.getLogger(FileStorageBinaryServiceImplV4Test.class.getName());
    private static final String URL_SERVICE = "http://localhost:8081/mtom.ws/fileStorageBinaryService";
    
    private static Endpoint endpoint;
    private Service service;
    private FileStorageBinaryPort port;
    private final Class<FileStorageBinaryPort> portClazz = FileStorageBinaryPort.class;

    @BeforeClass
    public static void beforeClass() throws Exception {
        // vypise xml v sysout
        System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
        System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
        // -- aby vypis mohl byt hrozne dlouhej
        System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dumpTreshold", "999999999");
        System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dumpTreshold", "999999999");
        endpoint = Endpoint.publish(URL_SERVICE, new FileStorageBinaryServiceImplV4());
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
        QName serviceName = new QName("http://ws.learn.tc.cz/mtom/binary/api", "fileStorageBinaryService");
        service = Service.create(new URL(URL_SERVICE + "?wsdl"), serviceName);
        port = service.getPort(portClazz);
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
    public void testTryStreamFeature_SmallDownloadBinary() throws Exception {
        String filename=FileStorageBinaryServiceImpl.SMALL_SAMPLE_FILE_XML;
        //StreamingAttachmentFeature fStream = new StreamingAttachmentFeature("/tmp", true, 4000L);
        //port = service.getPort(portClazz, fStream);
        FileBinaryType file = port.downloadBinary(filename);
        Assert.assertEquals(filename, file.getFileName());
    }
    
    @Test
    public void testTryStreamFeature_DownloadBinary() throws Exception {
        String filename=FileStorageBinaryServiceImpl.SAMPLE_FILE_XML;
        //StreamingAttachmentFeature fStream = new StreamingAttachmentFeature("/tmp", true, 4000L);
        //port = service.getPort(portClazz, fStream);
        FileBinaryType file = port.downloadBinary(filename);
        Assert.assertEquals(filename, file.getFileName());
    }    
    
}