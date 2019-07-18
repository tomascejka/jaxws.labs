package cz.toce.learn.javaee.jaxws.simplest.fault.server;

import cz.toce.learn.javaee.jaxws.simplest.fault.api.HelloCheckedExceptionRequest;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.HelloCheckedExceptionResponse;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.HelloRuntimeExceptionRequest;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.HelloRuntimeExceptionResponse;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.HelloSoapFaultExceptionRequest;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.HelloSoapFaultExceptionResponse;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.HelloWebServiceExceptionRequest;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.HelloWebServiceExceptionResponse;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.InternalErrorException;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.InternalErrorExceptionFault;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.SimpleWebServiceFaultPortType;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.soap.Detail;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPFault;
import javax.xml.ws.BindingType;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPFaultException;

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

    private static final Logger LOG = Logger.getLogger(SimpleWebServiceFault12Impl.class.getName());
    
    @Override
    public HelloRuntimeExceptionResponse helloRuntimeException(HelloRuntimeExceptionRequest parameters) {
        throw new RuntimeException("Not supported yet.");
    }

    @Override
    public HelloCheckedExceptionResponse helloCheckedException(HelloCheckedExceptionRequest parameters) throws InternalErrorExceptionFault {
        InternalErrorException faultInfo = new InternalErrorException();
        faultInfo.setMessage("shit happens, dontya?!");
        throw new InternalErrorExceptionFault("Fack up, man", faultInfo, new IllegalStateException("deep-shit message, man"));
    }

    @Override
    public HelloWebServiceExceptionResponse helloWebServiceException(HelloWebServiceExceptionRequest parameters) {
        throw new WebServiceException("Web service exception.");
    }

    @Override
    public HelloSoapFaultExceptionResponse helloSoapFaultException(HelloSoapFaultExceptionRequest parameters) {
        try {
            SOAPFactory soapFactory = SOAPFactory.newInstance( SOAPConstants.SOAP_1_2_PROTOCOL );
            SOAPFault soapFault = soapFactory.createFault();
            soapFault.appendFaultSubcode( new QName( "http://api.fault.simplest.jaxws.javaee.learn.toce.cz", "SomethingDefined" ) );
            soapFault.setFaultRole( "http://api.fault.simplest.jaxws.javaee.learn.toce.cz/sample" );
            soapFault.addFaultReasonText( "SOAPFaultException happens.", Locale.getDefault() );
            Detail detail = soapFault.addDetail();
            SOAPElement soapElement = detail.addChildElement( new QName( "http://api.fault.simplest.jaxws.javaee.learn.toce.cz", "SomeSpecificReason" ) );
            soapElement.addTextNode( "TEST something detail section." );
            throw new SOAPFaultException( soapFault );
        } catch (SOAPException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new WebServiceException("Soap fault exception fack up ...");
        }
    }
    
}
