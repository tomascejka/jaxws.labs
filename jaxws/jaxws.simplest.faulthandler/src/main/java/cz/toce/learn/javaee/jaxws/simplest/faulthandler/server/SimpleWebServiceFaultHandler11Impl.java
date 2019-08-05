package cz.toce.learn.javaee.jaxws.simplest.faulthandler.server;

import cz.toce.learn.javaee.jaxws.simplest.faulthandler.api.*;
import cz.toce.learn.javaee.jaxws.simplest.faulthandler.api.model.*;
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
        serviceName="SimpleWebServiceFaultHandler", // viz. /definitions/service[@name]
        portName = "SimpleWebServiceFaultHandlerSoap11Http",// viz. /definitions/service/port[@name]
        targetNamespace = "http://api.faulthandler.simplest.jaxws.javaee.learn.toce.cz", // viz. /definitions[@targetNamespace]
        endpointInterface = "cz.toce.learn.javaee.jaxws.simplest.faulthandler.api.SimpleWebServiceFaultHandlerPortType" // viz. složenina, viz. targetNamespace + /definitions/portType[@name]
)
@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP11HTTP_BINDING)
public class SimpleWebServiceFaultHandler11Impl implements SimpleWebServiceFaultHandlerPortType {

    private static final Logger LOG = Logger.getLogger(SimpleWebServiceFaultHandler11Impl.class.getName());
    
    public static final String RUNTIME_EXCEPTION_MESSAGE= "RuntimeException: Not supported yet.";
    public static final String CHECKED_EXCEPTION_MESSAGE= "Shit happens, dontya?!";
    
    private static final String WEB_SERVICE_TNS = "http://api.faulthandler.simplest.jaxws.javaee.learn.toce.cz";

    @Override
    public HelloRuntimeExceptionResponse helloRuntimeException(HelloRuntimeExceptionRequest arg0) {
        throw new UnsupportedOperationException(RUNTIME_EXCEPTION_MESSAGE);
    }    

    @Override
    public HelloCheckedExceptionResponse helloCheckedException(HelloCheckedExceptionRequest arg0) throws InternalErrorExceptionFault {
        InternalErrorException faultInfo = new InternalErrorException();
        faultInfo.setMessage("shit happens, dontya?! (soap fault handler)");
        throw new InternalErrorExceptionFault(CHECKED_EXCEPTION_MESSAGE, faultInfo, new IllegalStateException("deep-shit message (soap fault handler), man"));
    }    
        
    @Override
    public HelloWebServiceExceptionResponse helloWebServiceException(HelloWebServiceExceptionRequest arg0) {
        throw new WebServiceException("Web service exception.");
    }
    
    @Override
    public HelloSoapFaultExceptionResponse helloSoapFaultException(HelloSoapFaultExceptionRequest arg0) {
        try {
            SOAPFactory soapFactory = SOAPFactory.newInstance( SOAPConstants.SOAP_1_1_PROTOCOL );
            SOAPFault soapFault = soapFactory.createFault();
            soapFault.setFaultString("SOAPFaultException happens.", Locale.getDefault());
            Detail detail = soapFault.addDetail();
            SOAPElement soapElement = detail.addChildElement( new QName( WEB_SERVICE_TNS, "SomeSpecificReason" ) );
            soapElement.addTextNode( "TEST something detail section (soap fault handler)." );
            throw new SOAPFaultException( soapFault );
        } catch (SOAPException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        return null;
    }    
}
