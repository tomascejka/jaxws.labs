package cz.tc.learn.ws.mtom.binary;

import cz.tc.learn.ws.mtom.binary.api.FileStorageBinaryPort;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;
import org.junit.AfterClass;
import org.junit.Before;

/**
 * @author tomas.cejka
 */
public abstract class BaseBinaryTest {
    
    protected static final String URL_SERVICE = "http://localhost:8081/mtom.ws/fileStorageBinaryService";
    
    protected static Endpoint endpoint;
    protected Service service;
    protected FileStorageBinaryPort port;
    protected final Class<FileStorageBinaryPort> portClazz = FileStorageBinaryPort.class;

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
        System.out.println("=================================================\n");
    } 
}
