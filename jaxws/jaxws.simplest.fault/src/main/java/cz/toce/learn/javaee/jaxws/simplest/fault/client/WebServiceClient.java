package cz.toce.learn.javaee.jaxws.simplest.fault.client;

import cz.toce.learn.javaee.jaxws.simplest.fault.api.HelloCheckedExceptionRequest;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.HelloRuntimeExceptionRequest;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.InternalErrorExceptionFault;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.SimpleWebServiceFault;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.SimpleWebServiceFaultPortType;
import cz.toce.learn.javaee.jaxws.simplest.fault.server.WebServicePublisher;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author tomas.cejka
 */
public class WebServiceClient {
//*
    private static final Logger LOG = Logger.getLogger(WebServiceClient.class.getName());
    private static final boolean IS_EXEC_RUNTIME = true;

    public static void main(String[] args) throws InternalErrorExceptionFault, MalformedURLException {
        try {
            URL wsdlLocation = new URL(WebServicePublisher.SERVICE_URL);
            SimpleWebServiceFault service = new SimpleWebServiceFault(wsdlLocation);
            SimpleWebServiceFaultPortType port = service.getSimpleWebServiceFaultSoap12Http();

            // -- Volani serverové časti
            System.out.println("--- Hello request ---");
            if (IS_EXEC_RUNTIME) {
                HelloRuntimeExceptionRequest request = new HelloRuntimeExceptionRequest();
                request.setGreetings("Hello from RUNTIME Tomas");
                System.out.println(port.helloRuntimeException(request).getResultText());
            } else {
                HelloCheckedExceptionRequest crequest = new HelloCheckedExceptionRequest();
                crequest.setGreetings("Hello from CHECKED Tomas");
                System.out.println(port.helloCheckedException(crequest).getResultText());
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "" , e);
        }
        System.out.println("--");

    }
//*/
}
