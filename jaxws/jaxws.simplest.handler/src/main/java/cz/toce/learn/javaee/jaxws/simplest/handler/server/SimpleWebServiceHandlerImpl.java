package cz.toce.learn.javaee.jaxws.simplest.handler.server;

import cz.toce.learn.javaee.jaxws.simplest.handler.api.InternalErrorExceptionFault;
import cz.toce.learn.javaee.jaxws.simplest.handler.api.SimpleWebServiceHandlerPortType;
import cz.toce.learn.javaee.jaxws.simplest.handler.api.model.HelloCheckedExceptionRequest;
import cz.toce.learn.javaee.jaxws.simplest.handler.api.model.HelloCheckedExceptionResponse;
import cz.toce.learn.javaee.jaxws.simplest.handler.api.model.HelloRequest;
import cz.toce.learn.javaee.jaxws.simplest.handler.api.model.HelloResponse;
import cz.toce.learn.javaee.jaxws.simplest.handler.api.model.HelloRuntimeExceptionRequest;
import cz.toce.learn.javaee.jaxws.simplest.handler.api.model.HelloRuntimeExceptionResponse;
import cz.toce.learn.javaee.jaxws.simplest.handler.api.model.HelloSoapFaultExceptionRequest;
import cz.toce.learn.javaee.jaxws.simplest.handler.api.model.HelloSoapFaultExceptionResponse;
import cz.toce.learn.javaee.jaxws.simplest.handler.api.model.HelloWebServiceExceptionRequest;
import cz.toce.learn.javaee.jaxws.simplest.handler.api.model.HelloWebServiceExceptionResponse;
import cz.toce.learn.javaee.jaxws.simplest.handler.api.model.InternalErrorException;
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
        serviceName="SimpleWebServiceHandler", // viz. /definitions/service[@name]
        portName = "SimpleWebServiceHandlerSoap12Http",// viz. /definitions/service/port[@name]
        targetNamespace = "http://api.handler.simplest.jaxws.javaee.learn.toce.cz", // viz. /definitions[@targetNamespace]
        endpointInterface = "cz.toce.learn.javaee.jaxws.simplest.handler.api.SimpleWebServiceHandlerPortType" // viz. složenina, viz. targetNamespace + /definitions/portType[@name]
)
@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
public class SimpleWebServiceHandlerImpl implements SimpleWebServiceHandlerPortType {

    private static final Logger LOG = Logger.getLogger(SimpleWebServiceHandlerImpl.class.getName());
    
    @Override
    public HelloWebServiceExceptionResponse helloWebServiceException(HelloWebServiceExceptionRequest arg0) {
        throw new WebServiceException("Not supported yet (fault handler example).");
    }

    private static final String WEB_SERVICE_TNS = "http://api.handler.simplest.jaxws.javaee.learn.toce.cz";
    
    @Override
    public HelloSoapFaultExceptionResponse helloSoapFaultException(HelloSoapFaultExceptionRequest arg0) {
        try {
            SOAPFactory soapFactory = SOAPFactory.newInstance( SOAPConstants.SOAP_1_2_PROTOCOL );
            SOAPFault soapFault = soapFactory.createFault();
            soapFault.appendFaultSubcode( new QName( WEB_SERVICE_TNS, "SomethingDefined" ) );
            soapFault.setFaultRole( WEB_SERVICE_TNS+"/sampleRole" );
            soapFault.addFaultReasonText( "SOAPFaultException happens.", Locale.getDefault() );
            Detail detail = soapFault.addDetail();
            SOAPElement soapElement = detail.addChildElement( new QName( WEB_SERVICE_TNS, "SomeSpecificReason" ) );
            soapElement.addTextNode( "TEST something detail section (soap fault handler)." );
            throw new SOAPFaultException( soapFault );
        } catch (SOAPException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public HelloRuntimeExceptionResponse helloRuntimeException(HelloRuntimeExceptionRequest arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public HelloCheckedExceptionResponse helloCheckedException(HelloCheckedExceptionRequest arg0) throws InternalErrorExceptionFault {
        InternalErrorException faultInfo = new InternalErrorException();
        faultInfo.setMessage("shit happens, dontya?! (soap fault handler)");
        throw new InternalErrorExceptionFault("Fack up, man (soap fault handler)", faultInfo, new IllegalStateException("deep-shit message (soap fault handler), man"));
    }

    @Override
    public HelloResponse hello(HelloRequest parameters) {
        HelloResponse retval = new HelloResponse();
        retval.setResultCode("OK");
        retval.setResultText("Approved");
        return retval;
    }
}