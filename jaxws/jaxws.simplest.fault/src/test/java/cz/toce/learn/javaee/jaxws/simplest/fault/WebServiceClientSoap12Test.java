package cz.toce.learn.javaee.jaxws.simplest.fault;

import com.sun.xml.ws.fault.ServerSOAPFaultException;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.HelloCheckedExceptionRequest;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.HelloRuntimeExceptionRequest;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.InternalErrorExceptionFault;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.SimplestWebServiceFault;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.SimplestWebServiceFaultPortType;
import cz.toce.learn.javaee.jaxws.simplest.fault.server.SimpleWebServiceFault12Impl;
import java.net.URL;
import javax.xml.ws.Endpoint;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Jednotkovy test, ktery nahradil client/server tridy s main metodami, aby se daly prevolavat mezi sebou.
 * 
 * @author tomas.cejka
 */
public class WebServiceClientSoap12Test {
    
    private static final String SERVICE_URL = "http://localhost:8081/jax-ws/simplest/fault";
    private static Endpoint endpoint;
    private SimplestWebServiceFaultPortType tested;

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @BeforeClass
    public static void before() throws Exception {
        endpoint = Endpoint.publish(SERVICE_URL, new SimpleWebServiceFault12Impl());
    }
    
    @Before
    public void setUp() throws Exception {
        URL wsdlLocation = new URL(SERVICE_URL);
        SimplestWebServiceFault service = new SimplestWebServiceFault(wsdlLocation);
        tested = service.getSimplestWebServiceFaultSoap12Http();
    }
    
    @Test(expected = ServerSOAPFaultException.class)
    public void testHelloRuntimeException() {
        HelloRuntimeExceptionRequest request = new HelloRuntimeExceptionRequest();
        request.setGreetings("Hello from RUNTIME Tomas");
        // -- testovana metoda
        tested.helloRuntimeException(request);
    }
    
    @Test
    public void testHelloRuntimeExceptionWithMessage () {
        HelloRuntimeExceptionRequest request = new HelloRuntimeExceptionRequest();
        request.setGreetings("Hello from RUNTIME Tomas");
        // -- nutny setup pred spustenim testovane metody
        thrown.expect(ServerSOAPFaultException.class);
        thrown.expectMessage(SimpleWebServiceFault12Impl.RUNTIME_EXCEPTION_MESSAGE);
        // -- testovana metoda
        tested.helloRuntimeException(request);
    }
    
    @Test(expected = InternalErrorExceptionFault.class)
    public void testHelloCheckedException() throws InternalErrorExceptionFault {
        HelloCheckedExceptionRequest request = new HelloCheckedExceptionRequest();
        request.setGreetings("Hello checked ...");
        tested.helloCheckedException(request);
    }
    
    @Test
    public void testHelloCheckedExceptionWithMessage () throws InternalErrorExceptionFault {
        HelloCheckedExceptionRequest request = new HelloCheckedExceptionRequest();
        request.setGreetings("Hello checked ...");        
        // -- nutny setup pred spustenim testovane metody
        thrown.expect(InternalErrorExceptionFault.class);
        thrown.expectMessage(SimpleWebServiceFault12Impl.CHECKED_EXCEPTION_MESSAGE);
        // -- testovana metoda
        tested.helloCheckedException(request);
    }
    
    @AfterClass
    public static void tearDown() throws Exception {
        if(endpoint != null) {
            endpoint.stop();
        }
    }
}
