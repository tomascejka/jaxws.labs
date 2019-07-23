package cz.toce.learn.javaee.jaxws.simplest.handler.client;

import cz.toce.learn.javaee.jaxws.simplest.handler.api.InternalErrorExceptionFault;
import cz.toce.learn.javaee.jaxws.simplest.handler.api.SimpleWebServiceHandler;
import cz.toce.learn.javaee.jaxws.simplest.handler.api.SimpleWebServiceHandlerPortType;
import cz.toce.learn.javaee.jaxws.simplest.handler.api.model.HelloCheckedExceptionRequest;
import cz.toce.learn.javaee.jaxws.simplest.handler.api.model.HelloRuntimeExceptionRequest;
import cz.toce.learn.javaee.jaxws.simplest.handler.server.WebServicePublisher;
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
//*
    private static final boolean execRuntime = false;
    
    public static void main(String[] args) throws InternalErrorExceptionFault, MalformedURLException {
        // -- service, kam budu posilat soap request
        URL wsdlLocation = new URL(WebServicePublisher.SERVICE_URL);
        SimpleWebServiceHandler service = new SimpleWebServiceHandler(wsdlLocation);
        SimpleWebServiceHandlerPortType port = service.getSimpleWebServiceHandlerSoap12Http();
        
        // -- Pridani handleru
        List<Handler> handlerChain = new ArrayList<>();
        handlerChain.add(new SimpleWebServiceSoapHandler());
        ((BindingProvider) port).getBinding().setHandlerChain(handlerChain);
        
        // -- Volani serverové časti
        if(execRuntime) {
            // -- Request zprava, kt. budu posilat via soap klienta
            HelloRuntimeExceptionRequest request = new HelloRuntimeExceptionRequest();
            request.setGreetings("Hello from RUNTIME Tomas");
            System.out.println(port.helloRuntimeException(request).getResultText());
        } else {
            // -- Request zprava, kt. budu posilat via soap klienta
            HelloCheckedExceptionRequest crequest = new HelloCheckedExceptionRequest();
            crequest.setGreetings("Hello from CHECKED Tomas");
            System.out.println(port.helloCheckedException(crequest).getResultText());
        }
    }
//*/
}
