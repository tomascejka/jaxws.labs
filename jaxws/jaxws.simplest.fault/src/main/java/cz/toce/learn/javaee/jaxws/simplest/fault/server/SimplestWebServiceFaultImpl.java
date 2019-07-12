package cz.toce.learn.javaee.jaxws.simplest.fault.server;

import cz.toce.learn.javaee.jaxws.simplest.fault.api.HelloRequest;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.HelloResponse;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.InternalErrorExceptionFault;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.SimpleWebServiceFaultPortType;
import javax.jws.WebService;

/**
 *
 * @author tomas.cejka
 */
@WebService(targetNamespace = "http://api.fault.simplest.jaxws.javaee.learn.toce.cz/" ,endpointInterface = "cz.toce.learn.javaee.jaxws.simplest.fault.api.SimpleWebServiceFaultPortType")
public class SimplestWebServiceFaultImpl implements SimpleWebServiceFaultPortType {

    @Override
    public HelloResponse helloMessage(HelloRequest parameters) throws InternalErrorExceptionFault {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
