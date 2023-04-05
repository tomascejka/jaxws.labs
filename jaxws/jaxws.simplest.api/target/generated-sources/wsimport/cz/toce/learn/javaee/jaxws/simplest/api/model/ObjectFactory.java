
package cz.toce.learn.javaee.jaxws.simplest.api.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cz.toce.learn.javaee.jaxws.simplest.api.model package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _HelloRequest_QNAME = new QName("http://model.api.simplest.jaxws.javaee.learn.toce.cz", "HelloRequest");
    private final static QName _HelloResponse_QNAME = new QName("http://model.api.simplest.jaxws.javaee.learn.toce.cz", "HelloResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cz.toce.learn.javaee.jaxws.simplest.api.model
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link HelloRequestType }
     * 
     */
    public HelloRequestType createHelloRequestType() {
        return new HelloRequestType();
    }

    /**
     * Create an instance of {@link HelloResponseType }
     * 
     */
    public HelloResponseType createHelloResponseType() {
        return new HelloResponseType();
    }

    /**
     * Create an instance of {@link HelloRuntimeExceptionRequest }
     * 
     */
    public HelloRuntimeExceptionRequest createHelloRuntimeExceptionRequest() {
        return new HelloRuntimeExceptionRequest();
    }

    /**
     * Create an instance of {@link HelloRuntimeExceptionResponse }
     * 
     */
    public HelloRuntimeExceptionResponse createHelloRuntimeExceptionResponse() {
        return new HelloRuntimeExceptionResponse();
    }

    /**
     * Create an instance of {@link HelloCheckedExceptionRequest }
     * 
     */
    public HelloCheckedExceptionRequest createHelloCheckedExceptionRequest() {
        return new HelloCheckedExceptionRequest();
    }

    /**
     * Create an instance of {@link HelloCheckedExceptionResponse }
     * 
     */
    public HelloCheckedExceptionResponse createHelloCheckedExceptionResponse() {
        return new HelloCheckedExceptionResponse();
    }

    /**
     * Create an instance of {@link HelloWebServiceExceptionRequest }
     * 
     */
    public HelloWebServiceExceptionRequest createHelloWebServiceExceptionRequest() {
        return new HelloWebServiceExceptionRequest();
    }

    /**
     * Create an instance of {@link HelloWebServiceExceptionResponse }
     * 
     */
    public HelloWebServiceExceptionResponse createHelloWebServiceExceptionResponse() {
        return new HelloWebServiceExceptionResponse();
    }

    /**
     * Create an instance of {@link HelloSoapFaultExceptionRequest }
     * 
     */
    public HelloSoapFaultExceptionRequest createHelloSoapFaultExceptionRequest() {
        return new HelloSoapFaultExceptionRequest();
    }

    /**
     * Create an instance of {@link HelloSoapFaultExceptionResponse }
     * 
     */
    public HelloSoapFaultExceptionResponse createHelloSoapFaultExceptionResponse() {
        return new HelloSoapFaultExceptionResponse();
    }

    /**
     * Create an instance of {@link InternalErrorException }
     * 
     */
    public InternalErrorException createInternalErrorException() {
        return new InternalErrorException();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HelloRequestType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link HelloRequestType }{@code >}
     */
    @XmlElementDecl(namespace = "http://model.api.simplest.jaxws.javaee.learn.toce.cz", name = "HelloRequest")
    public JAXBElement<HelloRequestType> createHelloRequest(HelloRequestType value) {
        return new JAXBElement<HelloRequestType>(_HelloRequest_QNAME, HelloRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HelloResponseType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link HelloResponseType }{@code >}
     */
    @XmlElementDecl(namespace = "http://model.api.simplest.jaxws.javaee.learn.toce.cz", name = "HelloResponse")
    public JAXBElement<HelloResponseType> createHelloResponse(HelloResponseType value) {
        return new JAXBElement<HelloResponseType>(_HelloResponse_QNAME, HelloResponseType.class, null, value);
    }

}
