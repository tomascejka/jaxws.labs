
package cz.tc.learn.ws.mtom.stream.types;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cz.tc.learn.ws.mtom.stream.types package. 
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

    private final static QName _FileStream_QNAME = new QName("http://ws.learn.tc.cz/mtom/stream/types/", "fileStream");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cz.tc.learn.ws.mtom.stream.types
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FileStreamType }
     * 
     */
    public FileStreamType createFileStreamType() {
        return new FileStreamType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FileStreamType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link FileStreamType }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.learn.tc.cz/mtom/stream/types/", name = "fileStream")
    public JAXBElement<FileStreamType> createFileStream(FileStreamType value) {
        return new JAXBElement<FileStreamType>(_FileStream_QNAME, FileStreamType.class, null, value);
    }

}
