
package org.medistor.types;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.medistor.types package. 
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

    private final static QName _XRay_QNAME = new QName("http://mediStor.org/types/", "xRay");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.medistor.types
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link XRayType }
     * 
     */
    public XRayType createXRayType() {
        return new XRayType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XRayType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link XRayType }{@code >}
     */
    @XmlElementDecl(namespace = "http://mediStor.org/types/", name = "xRay")
    public JAXBElement<XRayType> createXRay(XRayType value) {
        return new JAXBElement<XRayType>(_XRay_QNAME, XRayType.class, null, value);
    }

}
