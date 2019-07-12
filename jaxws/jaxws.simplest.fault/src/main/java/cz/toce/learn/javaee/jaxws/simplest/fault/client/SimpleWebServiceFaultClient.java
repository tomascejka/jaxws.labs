package cz.toce.learn.javaee.jaxws.simplest.fault.client;

import cz.toce.learn.javaee.jaxws.simplest.fault.api.HelloRequest;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.InternalErrorExceptionFault;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.SimpleWebServiceFault;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.SimpleWebServiceFaultPortType;
import cz.toce.learn.javaee.jaxws.simplest.fault.server.SimplestWebServiceFaultPublisher;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

/**
 *
 * @author tomas.cejka
 */
public class SimpleWebServiceFaultClient {
    
    private static final String NAMESPACE_URI = "http://api.fault.simplest.jaxws.javaee.learn.toce.cz/";
    
    public static void main(String[] args) throws InternalErrorExceptionFault, MalformedURLException {
        // -- service
        URL wsdlLocation = new URL(SimplestWebServiceFaultPublisher.SERVICE_URL);
        String localPart = "SimplestWebServiceFaultImplService";
        QName serviceName = new QName(NAMESPACE_URI, localPart);
        SimpleWebServiceFault service = new SimpleWebServiceFault(wsdlLocation, serviceName);
        service.setHandlerResolver(new HandlerResolverImpl());
        // -- port
        QName portName = new QName(NAMESPACE_URI, "SimplestWebServiceFaultImplPort");
        SimpleWebServiceFaultPortType port = service.getPort(portName, SimpleWebServiceFaultPortType.class);
        // -- URL where api is published
        BindingProvider bindingProvider = (BindingProvider) port;
        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsdlLocation.toString());
        // -- Request
        HelloRequest request = new HelloRequest();
        request.setGreetings("Hello from Tomas");
        
        System.out.println(port.helloMessage(request).getResultText());
    }
}
