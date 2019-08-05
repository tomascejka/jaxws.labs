package cz.toce.learn.javaee.jaxws.simplest.faulthandler;

import com.sun.xml.ws.fault.ServerSOAPFaultException;
import cz.toce.learn.javaee.jaxws.simplest.faulthandler.api.*;
import cz.toce.learn.javaee.jaxws.simplest.faulthandler.api.model.*;
import java.net.URL;
import javax.xml.ws.Endpoint;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import cz.toce.learn.javaee.jaxws.simplest.faulthandler.server.SimpleWebServiceFaultHandler11Impl;
import static cz.toce.learn.javaee.jaxws.simplest.faulthandler.WebServicePublisher.SERVICE_URL_11;
import cz.toce.learn.javaee.jaxws.simplest.faulthandler.client.SimpleWebServiceSoapHandler;
import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

/**
 * Jednotkovy test, ktery nahradil client/server tridy s main metodami, aby se daly prevolavat mezi sebou.
 * 
 * @author tomas.cejka
 */
public class WebServiceClientSoap11Test {
    
    private static Endpoint endpoint;
    private SimpleWebServiceFaultHandlerPortType tested;

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @BeforeClass
    public static void before() throws Exception {
        endpoint = Endpoint.publish(SERVICE_URL_11, new SimpleWebServiceFaultHandler11Impl());
    }
    
    @Before
    public void setUp() throws Exception {
        URL wsdlLocation = new URL(SERVICE_URL_11);
        SimpleWebServiceFaultHandler service = new SimpleWebServiceFaultHandler(wsdlLocation);
        tested = service.getSimpleWebServiceFaultHandlerSoap11Http();
        // -- Pridani handleru
        List<Handler> handlerChain = new ArrayList<>();
        handlerChain.add(new SimpleWebServiceSoapHandler());
        ((BindingProvider) tested).getBinding().setHandlerChain(handlerChain);
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
        thrown.expectMessage(SimpleWebServiceFaultHandler11Impl.RUNTIME_EXCEPTION_MESSAGE);
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
        thrown.expectMessage(SimpleWebServiceFaultHandler11Impl.CHECKED_EXCEPTION_MESSAGE);
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
