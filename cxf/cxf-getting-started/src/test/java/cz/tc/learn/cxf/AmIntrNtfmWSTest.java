package cz.tc.learn.cxf;

import javax.xml.ws.Endpoint;
import javax.xml.ws.soap.SOAPBinding;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.BeforeClass;

public class AmIntrNtfmWSTest {
    
    private static Endpoint server;   

    @BeforeClass
    public static void setUpClass() throws Exception {
        if(server == null) {
            server = Endpoint.create(SOAPBinding.SOAP12HTTP_BINDING, new AmIntrNtfmWsPortImpl());
            server.publish("http://localhost:8888/ws/test");
        }
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception {
        if(server!=null && server.isPublished()){
            server.stop();
        }
    }
    
    @Test
    public void testCall() {
        
    }
    
}
