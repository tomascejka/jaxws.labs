package cz.toce.learn.javaee.jaxws.simplest.handler;

import com.sun.xml.ws.fault.ServerSOAPFaultException;
import cz.toce.learn.javaee.jaxws.simplest.api.*;
import cz.toce.learn.javaee.jaxws.simplest.api.model.*;
import static cz.toce.learn.javaee.jaxws.simplest.sei.WebServicePublisher.SERVICE_URL_12;
import cz.toce.learn.javaee.jaxws.simplest.sei.server.SimpleWebServiceSei12Impl;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Endpoint;
import javax.xml.ws.handler.Handler;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
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
    
    private static Endpoint endpoint;
    private SimplestWebServiceApiPortType tested;

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @BeforeClass
    public static void before() throws Exception {
        endpoint = Endpoint.publish(SERVICE_URL_12, new SimpleWebServiceSei12Impl());
    }
    
    @Before
    public void setUp() throws Exception {
        URL wsdlLocation = new URL(SERVICE_URL_12);
        SimplestWebServiceApi service = new SimplestWebServiceApi(wsdlLocation);
        tested = service.getSimplestWebServiceApiSoap12Http();
        // -- Pridani handleru
        List<Handler> handlerChain = new ArrayList<>();
        handlerChain.add(new SimpleWebServiceLogSoapHandler());
        ((BindingProvider) tested).getBinding().setHandlerChain(handlerChain);
    }
    
    @Test
    public void testHello(){
        HelloRequestType request = new HelloRequestType();
        request.setGreetings("Hello from RUNTIME Tomas");
        // -- testovana metoda
        HelloResponseType response = tested.hello(request);
        assertTrue("OK".equals(response.getResultCode()));
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
        thrown.expectMessage(SimpleWebServiceSei12Impl.RUNTIME_EXCEPTION_MESSAGE);
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
        thrown.expectMessage(SimpleWebServiceSei12Impl.CHECKED_EXCEPTION_MESSAGE);
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
