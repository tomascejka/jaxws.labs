package cz.tc.learn.ws.mtom.optimize;

import com.sun.xml.ws.developer.JAXWSProperties;
import com.sun.xml.ws.developer.StreamingAttachmentFeature;
import cz.tc.learn.ws.mtom.stream.*;
import cz.tc.learn.ws.mtom.stream.api.FileStorageStreamPort;
import cz.tc.learn.ws.mtom.stream.types.FileStreamType;
import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;
import javax.xml.ws.soap.MTOMFeature;
import org.junit.*;

/**
 * @author tomas.cejka
 */
public class FileStorageStreamOptimizeServiceImplTest {

    private static Logger LOG = Logger.getLogger(FileStorageStreamOptimizeServiceImplTest.class.getName());
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
        endpoint = Endpoint.publish(URL_SERVICE, new FileStorageStreamOptimizeServiceImpl());
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
    public void test_SmallDownloadStream() throws Exception {
        String filename=FileStorageStreamServiceImpl.SMALL_SAMPLE_FILE_XML;
        FileStreamType file = port.downloadStream(filename);
        Assert.assertEquals(filename, file.getFileName());
    }
    
    @Test
    public void test_DownloadStream() throws Exception {
        String filename=FileStorageStreamServiceImpl.SAMPLE_FILE_XML;
        FileStreamType file = port.downloadStream(filename);
        Assert.assertEquals(filename, file.getFileName());
    }

    @Test
    public void testTryMtomFeature_SmallDownloadStream() throws Exception {
        String filename=FileStorageStreamServiceImpl.SMALL_SAMPLE_FILE_XML;
        MTOMFeature mtomf = new MTOMFeature(true);
        StreamingAttachmentFeature stf = new StreamingAttachmentFeature("/tmp", true, 4000000L);
        port = service.getPort(portClazz, mtomf, stf);
        FileStreamType file = port.downloadStream(filename);
        Assert.assertEquals(filename, file.getFileName());
    }
    
    @Test
    public void testTryMtomFeature_DownloadStream() throws Exception {
        String filename=FileStorageStreamServiceImpl.SAMPLE_FILE_XML;
        MTOMFeature mtomf = new MTOMFeature(true);
        StreamingAttachmentFeature stf = new StreamingAttachmentFeature("/tmp", true, 4000000L);
        port = service.getPort(portClazz, mtomf, stf);
        FileStreamType file = port.downloadStream(filename);
        Assert.assertEquals(filename, file.getFileName());
    }
    
    @Test
    public void test_uploadStream() throws Exception {
        String filename=FileStorageStreamServiceImpl.SAMPLE_FILE_XML;
        MTOMFeature mtomf = new MTOMFeature(true);
        StreamingAttachmentFeature stf = new StreamingAttachmentFeature("/tmp", true, 4l);
        port = service.getPort(portClazz, mtomf, stf);
        
        Map<String, Object> ctxt=((BindingProvider)port).getRequestContext();
        ctxt.put(JAXWSProperties.HTTP_CLIENT_STREAMING_CHUNK_SIZE, 8192);
        URL resource = this.getClass().getClassLoader().getResource(filename);
        DataHandler dh = new DataHandler(new FileDataSource(new File(resource.toURI())));
        FileStreamType file = new FileStreamType();
        file.setFileName(System.currentTimeMillis()+"__"+filename);
        file.setContentData(dh);
        boolean state = port.uploadStream(file);
        Assert.assertTrue(state);
    }
    
    /*
      MtomStreamingService service = new MtomStreamingService();
      MTOMFeature feature = new MTOMFeature();
      MtomStreamingPortType port = service.getMtomStreamingPortTypePort(
         feature);
      Map<String, Object> ctxt=((BindingProvider)port).getRequestContext();
      ctxt.put(JAXWSProperties.HTTP_CLIENT_STREAMING_CHUNK_SIZE, 8192);
      DataHandler dh = new DataHandler(new
                  FileDataSource("/tmp/example.jar"));
      port.fileUpload("/tmp/tmp.jar",dh);

      DataHandler dhn = port.fileDownload("/tmp/tmp.jar");
      StreamingDataHandler sdh = {StreamingDataHandler)dh;
      try{
         File file = new File("/tmp/tmp.jar");
         sdh.moveTo(file);    
         sdh.close();
       }
       catch(Exception e){
          e.printStackTrace();
           
    */
}
