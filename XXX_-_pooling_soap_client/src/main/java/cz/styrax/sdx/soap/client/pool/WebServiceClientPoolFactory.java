package cz.styrax.sdx.soap.client.pool;

import cz.styrax.sdx.soap.client.factory.WebServiceClientFactory;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 *
 * @author tomas.cejka
 *
 * @param <T> data type of web service port class
 */
public class WebServiceClientPoolFactory<T> extends BasePooledObjectFactory<T> {

    private final WebServiceClientFactory clientFactory;
    private final String wsdlUrl;
    private final Class<T> serviceEndpointInterface;

    public WebServiceClientPoolFactory(WebServiceClientFactory factory, String wsdlUrl, Class<T> serviceEndpointInterface) {
        this.clientFactory = factory;
        this.wsdlUrl = wsdlUrl;
        this.serviceEndpointInterface = serviceEndpointInterface;
    }

    @Override
    public T create() throws Exception {
        return clientFactory.createPort(wsdlUrl, serviceEndpointInterface);
    }

    @Override
    public PooledObject<T> wrap(T obj) {
        return new DefaultPooledObject<>(obj);
    }

    /*public GenericObjectPool<T> buildPool(WebServiceClientFactory factory, String wsdlUrl, Class<T> serviceEndpointInterface) {
        return new GenericObjectPool<T>(new WebServiceClientPoolFactory<T>(factory, wsdlUrl, serviceEndpointInterface));
    }*/
}
