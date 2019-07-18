package cz.toce.learn.javaee.jaxws.simplest.faulthandler.client;

import cz.toce.learn.javaee.jaxws.simplest.faulthandler.server.WebServicePublisher;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

/**
 *
 * @author tomas.cejka
 */
public class WebServiceClient {
/*
    private static final boolean execRuntime = false;
    
    public static void main(String[] args) throws InternalErrorExceptionFault, MalformedURLException {
        // -- service, kam budu posilat soap request
        URL wsdlLocation = new URL(WebServicePublisher.SERVICE_URL);
        SimpleWebServiceFaultHandler service = new SimpleWebServiceFaultHandler(wsdlLocation);
        SimpleWebServiceFaultHandlerPortType port = service.getSimpleWebServiceFaultHandlerSoap12Http();
        
        // -- Pridani handleru
        List<Handler> handlerChain = new ArrayList<>();
        handlerChain.add(new SimpleWebServiceSoapHandler());
        ((BindingProvider) port).getBinding().setHandlerChain(handlerChain);
        
        // -- Volani serverové časti
        if(execRuntime) {
            // -- Request zprava, kt. budu posilat via soap klienta
            HelloRequest request = new HelloRequest();
            request.setGreetings("Hello from RUNTIME Tomas");
            System.out.println(port.helloRuntimeException(request).getResultText());
        } else {
            // -- Request zprava, kt. budu posilat via soap klienta
            HelloCheckedRequest crequest = new HelloCheckedRequest();
            crequest.setGreetings("Hello from CHECKED Tomas");
            System.out.println(port.helloCheckedException(crequest).getResultText());
        }
    }
//*/
}
