
package cz.tc.learn.ws.mtom.types;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cz.tc.learn.ws.mtom.types package. 
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

    private final static QName _XFileBinary_QNAME = new QName("http://ws.learn.tc.cz/mtom/types/", "xFileBinary");
    private final static QName _XFileOctetStream_QNAME = new QName("http://ws.learn.tc.cz/mtom/types/", "xFileOctetStream");
    private final static QName _XFileSourceXml_QNAME = new QName("http://ws.learn.tc.cz/mtom/types/", "xFileSourceXml");
    private final static QName _XFileSourcePdf_QNAME = new QName("http://ws.learn.tc.cz/mtom/types/", "xFileSourcePdf");
    private final static QName _XFileSourceTxt_QNAME = new QName("http://ws.learn.tc.cz/mtom/types/", "xFileSourceTxt");
    private final static QName _XFileSourceJpg_QNAME = new QName("http://ws.learn.tc.cz/mtom/types/", "xFileSourceJpg");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cz.tc.learn.ws.mtom.types
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link XFileType }
     * 
     */
    public XFileType createXFileType() {
        return new XFileType();
    }

    /**
     * Create an instance of {@link XFileOctetStreamType }
     * 
     */
    public XFileOctetStreamType createXFileOctetStreamType() {
        return new XFileOctetStreamType();
    }

    /**
     * Create an instance of {@link XFileXmlType }
     * 
     */
    public XFileXmlType createXFileXmlType() {
        return new XFileXmlType();
    }

    /**
     * Create an instance of {@link XFilePdfType }
     * 
     */
    public XFilePdfType createXFilePdfType() {
        return new XFilePdfType();
    }

    /**
     * Create an instance of {@link XFileTxtType }
     * 
     */
    public XFileTxtType createXFileTxtType() {
        return new XFileTxtType();
    }

    /**
     * Create an instance of {@link XFileJpgType }
     * 
     */
    public XFileJpgType createXFileJpgType() {
        return new XFileJpgType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XFileType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link XFileType }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.learn.tc.cz/mtom/types/", name = "xFileBinary")
    public JAXBElement<XFileType> createXFileBinary(XFileType value) {
        return new JAXBElement<XFileType>(_XFileBinary_QNAME, XFileType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XFileOctetStreamType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link XFileOctetStreamType }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.learn.tc.cz/mtom/types/", name = "xFileOctetStream")
    public JAXBElement<XFileOctetStreamType> createXFileOctetStream(XFileOctetStreamType value) {
        return new JAXBElement<XFileOctetStreamType>(_XFileOctetStream_QNAME, XFileOctetStreamType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XFileXmlType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link XFileXmlType }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.learn.tc.cz/mtom/types/", name = "xFileSourceXml")
    public JAXBElement<XFileXmlType> createXFileSourceXml(XFileXmlType value) {
        return new JAXBElement<XFileXmlType>(_XFileSourceXml_QNAME, XFileXmlType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XFilePdfType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link XFilePdfType }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.learn.tc.cz/mtom/types/", name = "xFileSourcePdf")
    public JAXBElement<XFilePdfType> createXFileSourcePdf(XFilePdfType value) {
        return new JAXBElement<XFilePdfType>(_XFileSourcePdf_QNAME, XFilePdfType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XFileTxtType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link XFileTxtType }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.learn.tc.cz/mtom/types/", name = "xFileSourceTxt")
    public JAXBElement<XFileTxtType> createXFileSourceTxt(XFileTxtType value) {
        return new JAXBElement<XFileTxtType>(_XFileSourceTxt_QNAME, XFileTxtType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XFileJpgType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link XFileJpgType }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.learn.tc.cz/mtom/types/", name = "xFileSourceJpg")
    public JAXBElement<XFileJpgType> createXFileSourceJpg(XFileJpgType value) {
        return new JAXBElement<XFileJpgType>(_XFileSourceJpg_QNAME, XFileJpgType.class, null, value);
    }

}
