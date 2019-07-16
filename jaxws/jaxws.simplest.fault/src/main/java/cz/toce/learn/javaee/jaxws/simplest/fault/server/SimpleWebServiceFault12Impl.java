package cz.toce.learn.javaee.jaxws.simplest.fault.server;

import cz.toce.learn.javaee.jaxws.simplest.fault.api.HelloCheckedRequest;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.HelloCheckedResponse;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.HelloRequest;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.HelloResponse;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.InternalErrorException;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.InternalErrorExceptionFault;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.SimpleWebServiceFaultPortType;
import javax.jws.WebService;
import javax.xml.ws.BindingType;

/**
 * <p>Pri psani SEI (teto tridy) pouzivej v anotaci @WebService tyto atributy:</p>
 * <ul>
 * <li>serviceName, viz. /definitions/service[@name] (wsdl 1.0)</li>
 * <li>portName, viz. /definitions/service/port[@name] (wsdl 1.0)</li>
 * <li>targetNamespace, viz. /definitions[@targetNamespace] </li>
 * <li>endpointInterface (targetNamespace+name), viz. /definitions/portType[@name]</li>
 * </ul>
 * 
 * @author tomas.cejka
 */
@WebService(
        serviceName="SimpleWebServiceFault", // viz. /definitions/service[@name]
        portName = "SimpleWebServiceFaultSoap12Http",// viz. /definitions/service/port[@name]
        targetNamespace = "http://api.fault.simplest.jaxws.javaee.learn.toce.cz", // viz. /definitions[@targetNamespace]
        endpointInterface = "cz.toce.learn.javaee.jaxws.simplest.fault.api.SimpleWebServiceFaultPortType" // viz. složenina, viz. targetNamespace + /definitions/portType[@name]
)
@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
public class SimpleWebServiceFault12Impl implements SimpleWebServiceFaultPortType {

    @Override
    public HelloResponse helloRuntimeException(HelloRequest parameters) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public HelloCheckedResponse helloCheckedException(HelloCheckedRequest parameters) throws InternalErrorExceptionFault {
        InternalErrorException faultInfo = new InternalErrorException();
        faultInfo.setMessage("shit happens, dontya?!");
        throw new InternalErrorExceptionFault("Fack up, man", faultInfo, new IllegalStateException("deep-shit message, man"));
    }
    
}
