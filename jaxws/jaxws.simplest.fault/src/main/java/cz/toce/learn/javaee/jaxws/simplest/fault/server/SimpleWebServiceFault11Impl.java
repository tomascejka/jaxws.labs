package cz.toce.learn.javaee.jaxws.simplest.fault.server;

import cz.toce.learn.javaee.jaxws.simplest.fault.api.InternalErrorExceptionFault;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.SimplestWebServiceFaultPortType;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.model.HelloCheckedExceptionRequest;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.model.HelloCheckedExceptionResponse;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.model.HelloRuntimeExceptionRequest;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.model.HelloRuntimeExceptionResponse;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.model.HelloSoapFaultExceptionRequest;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.model.HelloSoapFaultExceptionResponse;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.model.HelloWebServiceExceptionRequest;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.model.HelloWebServiceExceptionResponse;
import cz.toce.learn.javaee.jaxws.simplest.fault.api.model.InternalErrorException;
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
        serviceName="SimplestWebServiceFault", // viz. /definitions/service[@name]
        portName = "SimplestWebServiceFaultSoap11Http",// viz. /definitions/service/port[@name]
        targetNamespace = "http://api.fault.simplest.jaxws.javaee.learn.toce.cz", // viz. /definitions[@targetNamespace]
        endpointInterface = "cz.toce.learn.javaee.jaxws.simplest.fault.api.SimplestWebServiceFaultPortType" // viz. složenina, viz. targetNamespace + /definitions/portType[@name]
)
@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP11HTTP_BINDING)
public class SimpleWebServiceFault11Impl implements SimplestWebServiceFaultPortType {

    private static final Logger LOG = Logger.getLogger(SimpleWebServiceFault11Impl.class.getName());
    
    public static final String RUNTIME_EXCEPTION_MESSAGE= "RuntimeException: Not supported yet.";
    public static final String CHECKED_EXCEPTION_MESSAGE= "Shit happens, dontya?!";
    
    @Override
    public HelloRuntimeExceptionResponse helloRuntimeException(HelloRuntimeExceptionRequest parameters) {
        throw new RuntimeException(RUNTIME_EXCEPTION_MESSAGE);
    }

    @Override
    public HelloCheckedExceptionResponse helloCheckedException(HelloCheckedExceptionRequest parameters) throws InternalErrorExceptionFault {
        InternalErrorException faultInfo = new InternalErrorException();
        faultInfo.setMessage("Shit happens (fault info), dontya?!");
        throw new InternalErrorExceptionFault(CHECKED_EXCEPTION_MESSAGE, faultInfo, new IllegalStateException("deep-shit message, man"));
    }

    @Override
    public HelloWebServiceExceptionResponse helloWebServiceException(HelloWebServiceExceptionRequest parameters) {
        throw new WebServiceException("Web service exception.");
    }

    @Override
    public HelloSoapFaultExceptionResponse helloSoapFaultException(HelloSoapFaultExceptionRequest parameters) {
        try {
            String nsUri = "http://api.fault.simplest.jaxws.javaee.learn.toce.cz";
            SOAPFactory soapFactory = SOAPFactory.newInstance( SOAPConstants.SOAP_1_1_PROTOCOL );
            SOAPFault soapFault = soapFactory.createFault();
            soapFault.appendFaultSubcode( new QName( nsUri, "SomethingDefined" ) );
            soapFault.setFaultRole( "http://api.fault.simplest.jaxws.javaee.learn.toce.cz/sample" );
            soapFault.addFaultReasonText( "SOAPFaultException happens.", Locale.getDefault() );
            Detail detail = soapFault.addDetail();
            SOAPElement soapElement = detail.addChildElement( new QName( nsUri, "SomeSpecificDetail" ) );
            soapElement.addTextNode( "TEST something detail section." );
            throw new SOAPFaultException( soapFault );
        } catch (SOAPException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new WebServiceException("Soap fault exception fack up ...");
        }
    }
    
}
