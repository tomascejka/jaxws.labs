package cz.styrax.sdx.soap.client.factory;

import java.net.MalformedURLException;
import javax.xml.ws.Service;

/**
 * Dynamic approach to create web service clients (without knowledge of other metadata, eg. targetNamespace and serviceName - necessary to create client from
 * WSDL description).
 *
 * @author tomas.cejka
 */
public interface WebServiceClientFactory {

    /**
     * Do same as {@link WebServiceClientFactory#createService(java.lang.String)} (download/parse exposed WSDL before port is built) but order to get specific
     * port from given web service. It is usefull when client code generates API and knows exactly implementation <code>serviceEndpointInterface</code> of
     * service port. Port instance are complex so there are not thread-safe.
     *
     * @param <T> data type of service port implementation
     * @param wsdlUrl location where wsdl is exposed
     * @param serviceEndpointInterface implementation (class type) of service port
     * @return implementation (instance type) of service port
     *
     * @throws MalformedURLException if given <code>wsdlUrl</code> is not valid
     */
    <T> T createPort(String wsdlUrl, Class<T> serviceEndpointInterface) throws MalformedURLException;

    /**
     * Try to get (download) WSDL from given url <code>wsdlUrl</code> and parse it in order to get metadata (targetNamespace, serviceName) and use them during
     * create one instance of {@link Service}. Instance of {@link Service} are thread safe (port is complex that's why is not thread-safe).
     *
     * @param wsdlUrl location where the wsdl file is exposed
     * @return client view of web service
     *
     * @throws MalformedURLException if given <code>wsdlUrl</code> is not valid
     */
    Service createService(String wsdlUrl) throws MalformedURLException;

}
