package cz.toce.learn.javaee.jaxws.simplest.fault.client;

import cz.toce.learn.javaee.jaxws.simplest.fault.api.HelloCheckedRequest;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.HelloRequest;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.InternalErrorExceptionFault;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.SimpleWebServiceFault;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.SimpleWebServiceFaultPortType;
import cz.toce.learn.javaee.jaxws.simplest.fault.server.WebServicePublisher;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;

/**
 *
 * @author tomas.cejka
 */
public class WebServiceClient {

    public static void main(String[] args) throws InternalErrorExceptionFault, MalformedURLException {
        // -- service, kam budu posilat soap request
        URL wsdlLocation = new URL(WebServicePublisher.SERVICE_URL);
        SimpleWebServiceFault service = new SimpleWebServiceFault(wsdlLocation);
        SimpleWebServiceFaultPortType port = service.getSimpleWebServiceFaultSoap12Http();

        // --
        // -- Volani serverové časti
        // -- 
        System.out.println("--- Runtime hello ---");
        // -- Request zprava, kt. budu posilat via soap klienta
        HelloRequest request = new HelloRequest();
        request.setGreetings("Hello from RUNTIME Tomas");
        System.out.println(port.helloRuntimeException(request).getResultText());
        System.out.println("--");
        System.out.println("--- Checked hello ---");
        // -- Request zprava, kt. budu posilat via soap klienta
        HelloCheckedRequest crequest = new HelloCheckedRequest();
        crequest.setGreetings("Hello from CHECKED Tomas");
        System.out.println(port.helloCheckedException(crequest).getResultText());
        System.out.println("--");

    }
}
