package cz.toce.learn.javaee.jaxws.simplest.handler.client;

import cz.toce.learn.javaee.jaxws.simplest.handler.api.InternalErrorExceptionFault;
import cz.toce.learn.javaee.jaxws.simplest.handler.api.SimpleWebServiceHandler;
import cz.toce.learn.javaee.jaxws.simplest.handler.api.SimpleWebServiceHandlerPortType;
import cz.toce.learn.javaee.jaxws.simplest.handler.api.model.HelloRequest;
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
    
    public static void main(String[] args) throws InternalErrorExceptionFault, MalformedURLException {
        // -- service, kam budu posilat soap request
        URL wsdlLocation = new URL(WebServicePublisher.SERVICE_URL);
        SimpleWebServiceHandler service = new SimpleWebServiceHandler(wsdlLocation);
        SimpleWebServiceHandlerPortType port = service.getSimpleWebServiceHandlerSoap12Http();
        
        // -- Pridani handleru
        List<Handler> handlerChain = new ArrayList<>();
        handlerChain.add(new SimpleWebServiceLogSoapHandler());
        ((BindingProvider) port).getBinding().setHandlerChain(handlerChain);
        
        HelloRequest request = new HelloRequest();
        request.setGreetings("Hello from client, man");
        
        // -- Volani serverové časti
        System.out.println(port.hello(request).getResultText());
    }
//*/
}
