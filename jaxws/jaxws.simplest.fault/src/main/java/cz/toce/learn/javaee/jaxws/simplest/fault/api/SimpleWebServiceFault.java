
package cz.toce.learn.javaee.jaxws.simplest.fault.api;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * Simplest soap fault web service example - to demonstrated soap fault behaviour
 * 
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "SimpleWebServiceFault", targetNamespace = "http://api.fault.simplest.jaxws.javaee.learn.toce.cz", wsdlLocation = "file:/F:/Repositories/labs/jaxws.labs/jaxws/jaxws.simplest.fault/src/main/resources/WEB-INF/SimpleWebServiceFault.wsdl")
public class SimpleWebServiceFault
    extends Service
{

    private final static URL SIMPLEWEBSERVICEFAULT_WSDL_LOCATION;
    private final static WebServiceException SIMPLEWEBSERVICEFAULT_EXCEPTION;
    private final static QName SIMPLEWEBSERVICEFAULT_QNAME = new QName("http://api.fault.simplest.jaxws.javaee.learn.toce.cz", "SimpleWebServiceFault");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/F:/Repositories/labs/jaxws.labs/jaxws/jaxws.simplest.fault/src/main/resources/WEB-INF/SimpleWebServiceFault.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        SIMPLEWEBSERVICEFAULT_WSDL_LOCATION = url;
        SIMPLEWEBSERVICEFAULT_EXCEPTION = e;
    }

    public SimpleWebServiceFault() {
        super(__getWsdlLocation(), SIMPLEWEBSERVICEFAULT_QNAME);
    }

    public SimpleWebServiceFault(WebServiceFeature... features) {
        super(__getWsdlLocation(), SIMPLEWEBSERVICEFAULT_QNAME, features);
    }

    public SimpleWebServiceFault(URL wsdlLocation) {
        super(wsdlLocation, SIMPLEWEBSERVICEFAULT_QNAME);
    }

    public SimpleWebServiceFault(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, SIMPLEWEBSERVICEFAULT_QNAME, features);
    }

    public SimpleWebServiceFault(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SimpleWebServiceFault(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns SimpleWebServiceFaultPortType
     */
    @WebEndpoint(name = "SimpleWebServiceFaultSoap12Http")
    public SimpleWebServiceFaultPortType getSimpleWebServiceFaultSoap12Http() {
        return super.getPort(new QName("http://api.fault.simplest.jaxws.javaee.learn.toce.cz", "SimpleWebServiceFaultSoap12Http"), SimpleWebServiceFaultPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SimpleWebServiceFaultPortType
     */
    @WebEndpoint(name = "SimpleWebServiceFaultSoap12Http")
    public SimpleWebServiceFaultPortType getSimpleWebServiceFaultSoap12Http(WebServiceFeature... features) {
        return super.getPort(new QName("http://api.fault.simplest.jaxws.javaee.learn.toce.cz", "SimpleWebServiceFaultSoap12Http"), SimpleWebServiceFaultPortType.class, features);
    }

    private static URL __getWsdlLocation() {
        if (SIMPLEWEBSERVICEFAULT_EXCEPTION!= null) {
            throw SIMPLEWEBSERVICEFAULT_EXCEPTION;
        }
        return SIMPLEWEBSERVICEFAULT_WSDL_LOCATION;
    }

}
