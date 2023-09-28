
package cz.toce.learn.javaee.jaxws.simplest.api;

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
 * JAX-WS RI 2.3.2
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "SimplestWebServiceApi", targetNamespace = "http://api.simplest.jaxws.javaee.learn.toce.cz", wsdlLocation = "file:/F:/Repositories/labs/jaxws.labs/jaxws/jaxws.simplest.api/src/main/resources/META-INF/wsdl/SimplestWebServiceApi.wsdl")
public class SimplestWebServiceApi
    extends Service
{

    private final static URL SIMPLESTWEBSERVICEAPI_WSDL_LOCATION;
    private final static WebServiceException SIMPLESTWEBSERVICEAPI_EXCEPTION;
    private final static QName SIMPLESTWEBSERVICEAPI_QNAME = new QName("http://api.simplest.jaxws.javaee.learn.toce.cz", "SimplestWebServiceApi");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/F:/Repositories/labs/jaxws.labs/jaxws/jaxws.simplest.api/src/main/resources/META-INF/wsdl/SimplestWebServiceApi.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        SIMPLESTWEBSERVICEAPI_WSDL_LOCATION = url;
        SIMPLESTWEBSERVICEAPI_EXCEPTION = e;
    }

    public SimplestWebServiceApi() {
        super(__getWsdlLocation(), SIMPLESTWEBSERVICEAPI_QNAME);
    }

    public SimplestWebServiceApi(WebServiceFeature... features) {
        super(__getWsdlLocation(), SIMPLESTWEBSERVICEAPI_QNAME, features);
    }

    public SimplestWebServiceApi(URL wsdlLocation) {
        super(wsdlLocation, SIMPLESTWEBSERVICEAPI_QNAME);
    }

    public SimplestWebServiceApi(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, SIMPLESTWEBSERVICEAPI_QNAME, features);
    }

    public SimplestWebServiceApi(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SimplestWebServiceApi(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * Soap fault example processing via soap 1.1 version
     * 
     * @return
     *     returns SimplestWebServiceApiPortType
     */
    @WebEndpoint(name = "SimplestWebServiceApiSoap11Http")
    public SimplestWebServiceApiPortType getSimplestWebServiceApiSoap11Http() {
        return super.getPort(new QName("http://api.simplest.jaxws.javaee.learn.toce.cz", "SimplestWebServiceApiSoap11Http"), SimplestWebServiceApiPortType.class);
    }

    /**
     * Soap fault example processing via soap 1.1 version
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SimplestWebServiceApiPortType
     */
    @WebEndpoint(name = "SimplestWebServiceApiSoap11Http")
    public SimplestWebServiceApiPortType getSimplestWebServiceApiSoap11Http(WebServiceFeature... features) {
        return super.getPort(new QName("http://api.simplest.jaxws.javaee.learn.toce.cz", "SimplestWebServiceApiSoap11Http"), SimplestWebServiceApiPortType.class, features);
    }

    /**
     * Soap fault example processing via soap 1.2 version
     * 
     * @return
     *     returns SimplestWebServiceApiPortType
     */
    @WebEndpoint(name = "SimplestWebServiceApiSoap12Http")
    public SimplestWebServiceApiPortType getSimplestWebServiceApiSoap12Http() {
        return super.getPort(new QName("http://api.simplest.jaxws.javaee.learn.toce.cz", "SimplestWebServiceApiSoap12Http"), SimplestWebServiceApiPortType.class);
    }

    /**
     * Soap fault example processing via soap 1.2 version
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SimplestWebServiceApiPortType
     */
    @WebEndpoint(name = "SimplestWebServiceApiSoap12Http")
    public SimplestWebServiceApiPortType getSimplestWebServiceApiSoap12Http(WebServiceFeature... features) {
        return super.getPort(new QName("http://api.simplest.jaxws.javaee.learn.toce.cz", "SimplestWebServiceApiSoap12Http"), SimplestWebServiceApiPortType.class, features);
    }

    private static URL __getWsdlLocation() {
        if (SIMPLESTWEBSERVICEAPI_EXCEPTION!= null) {
            throw SIMPLESTWEBSERVICEAPI_EXCEPTION;
        }
        return SIMPLESTWEBSERVICEAPI_WSDL_LOCATION;
    }

}
