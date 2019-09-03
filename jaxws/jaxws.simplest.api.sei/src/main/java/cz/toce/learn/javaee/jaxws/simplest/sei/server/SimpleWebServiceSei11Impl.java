package cz.toce.learn.javaee.jaxws.simplest.sei.server;

import cz.toce.learn.javaee.jaxws.simplest.api.*;
import cz.toce.learn.javaee.jaxws.simplest.api.model.*;
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
        serviceName="SimplestWebServiceApi", // viz. /definitions/service[@name]
        portName = "SimplestWebServiceApiSoap11Http",// viz. /definitions/service/port[@name]
        targetNamespace = "http://api.simplest.jaxws.javaee.learn.toce.cz", // viz. /definitions[@targetNamespace]
        endpointInterface = "cz.toce.learn.javaee.jaxws.simplest.api.SimplestWebServiceApiPortType" // viz. složenina, viz. targetNamespace + /definitions/portType[@name]
)
@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP11HTTP_BINDING)
public class SimpleWebServiceSei11Impl implements SimplestWebServiceApiPortType {

    private static final Logger LOG = Logger.getLogger(SimpleWebServiceSei11Impl.class.getName());

    @Override
    public HelloResponseType hello(HelloRequestType parameters) {
        HelloResponseType response = new HelloResponseType();
        response.setResultCode("OK");
        response.setResultText("Approved");
        return response;
    }
    
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

    private static final String WEB_SERVICE_TNS = "http://api.simplest.jaxws.javaee.learn.toce.cz";
    
    @Override
    public HelloSoapFaultExceptionResponse helloSoapFaultException(HelloSoapFaultExceptionRequest parameters) {
        try {
            SOAPFactory soapFactory = SOAPFactory.newInstance( SOAPConstants.SOAP_1_1_PROTOCOL );
            SOAPFault soapFault = soapFactory.createFault();
            soapFault.appendFaultSubcode( new QName( WEB_SERVICE_TNS, "SomethingDefined" ) );
            soapFault.setFaultRole( WEB_SERVICE_TNS+"/sample" );
            soapFault.addFaultReasonText( "SOAPFaultException happens.", Locale.getDefault() );
            Detail detail = soapFault.addDetail();
            SOAPElement soapElement = detail.addChildElement( new QName( WEB_SERVICE_TNS, "SomeSpecificDetail" ) );
            soapElement.addTextNode( "TEST something detail section." );
            throw new SOAPFaultException( soapFault );
        } catch (SOAPException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new WebServiceException("Soap fault exception fack up ...");
        }
    }
    
}
