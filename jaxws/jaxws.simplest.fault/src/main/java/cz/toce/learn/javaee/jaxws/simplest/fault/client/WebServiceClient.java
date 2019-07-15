package cz.toce.learn.javaee.jaxws.simplest.fault.client;

import cz.toce.learn.javaee.jaxws.simplest.fault.api.HelloRequest;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.InternalErrorExceptionFault;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.SimpleWebServiceFault;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.SimpleWebServiceFaultPortType;
import cz.toce.learn.javaee.jaxws.simplest.fault.server.WebServicePublisher;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author tomas.cejka
 */
public class WebServiceClient {
    
    public static void main(String[] args) throws InternalErrorExceptionFault, MalformedURLException {
        // -- service, kam budu posilat soap request
        URL wsdlLocation = new URL(WebServicePublisher.SERVICE_URL);

        // -- Request zprava, kt. budu posilat via soap klienta
        HelloRequest request = new HelloRequest();
        request.setGreetings("Hello from Tomas");        
        
        // --
        // -- Tento pristup je mozny, kdyz SEI napises "z ruky" a nedefinejes 
        // -- explicitne nazvy port-u a sluzby pomoci atributu anotace @WebService: 
        // -- a to :
        // -- 1. portName a 
        // -- 2. serviceName
        // -- 3. targetNamespace, viz. NAMESPACE_URI
        // -- 2. endpointInterface
        // --
        /*
        String NAMESPACE_URI = "http://api.fault.simplest.jaxws.javaee.learn.toce.cz";
        String seiName = ""SimplestWebServiceFaultImpl";// je nazev tridy, kt. implementuje port vygenerovany z wsdl a nedefinuje nazvy v anotaci WebService (konkretne: portName a serviceName)
        QName serviceName = new QName(NAMESPACE_URI, seiName+"Service");
        SimpleWebServiceFault service = new SimpleWebServiceFault(wsdlLocation, serviceName);
        //service.setHandlerResolver(new HandlerResolverImpl());
        // -- port
        QName portName = new QName(NAMESPACE_URI, seiName+"Port");
        SimpleWebServiceFaultPortType port = service.getPort(portName, SimpleWebServiceFaultPortType.class);
        // -- URL where api is published
        BindingProvider bindingProvider = (BindingProvider) port;
        bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsdlLocation.toString());
        
        System.out.println(port.helloMessage(request).getResultText());
        */
        
        // --
        // --  Tento pristup sestaveni klienta je nejjednodussi mozny, ale je nutne, aby SEI, 
        // --  tzn. aby implementace na strane serveru pouzivala nazvy v anotaci 
        // --  @WebService (konkretne: portName a serviceName) a jejich hodnoty se odvijely od
        // --  nazvu uvedene v WSDL:
        // --  * portName = /definitions/service/port[@name]
        // --  * serviceName = /definitions/service[@name]
        // --  tzn. uz pri psani SEI tyto hodnoty znam a nemusim generovat klient tridu abych ji zjistil!
        // --  ... pak tedy lze napsat soap klienta takto:
        // --  
        SimpleWebServiceFault s = new SimpleWebServiceFault(wsdlLocation);
        SimpleWebServiceFaultPortType p = s.getSimpleWebServiceFaultSoap12Http();
        System.out.println(p.helloMessage(request).getResultText());
        
    }
}
