package cz.tc.learn.mtom;

import cz.tc.learn.ws.mtom.api.XFileStoragePort;
import cz.tc.learn.ws.mtom.types.XFileOctetStreamType;
import cz.tc.learn.ws.mtom.types.XFileType;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.*;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.mail.BodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.ws.soap.MTOMFeature;
import org.junit.Before;

/**
 * @author tomas.cejka
 */
public class XFileStoragePortImplTest {

    private static Logger LOG = Logger.getLogger(XFileStoragePortImplTest.class.getName());
    private static final String URL_SERVICE = "http://localhost:8081/ws/xfiletorageservice";
    
    private static Endpoint endpoint;
    private XFileStoragePort port;

    @BeforeClass
    public static void beforeClass() throws Exception {

        // byt ci nebyt - je internal/nebo transport
        System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
        System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
        
        // byt ci nebyt - je internal/nebo transport
        System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
        // -- aby vypis mohl byt hrozne dlouhej
        System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dumpTreshold", "99999");
        System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dumpTreshold", "99999");
        
        // @see https://stackoverflow.com/questions/2043792/missing-start-boundary-exception-when-reading-messages-with-an-attachment-file
        //System.setProperty("mail.mime.multipart.ignoreexistingboundaryparameter", "true");
        //System.setProperty("mail.mime.multipart.ignoremissingboundaryparameter", "true");
        //System.setProperty("mail.mime.multipart.allowempty", "true");//me pomohlo az toto (dva vyse parametry uvedene nepomohli)

        endpoint = Endpoint.publish(URL_SERVICE, new XFileStoragePortImpl());
    }

    @AfterClass
    public static void afterClass() throws Exception {
        if (endpoint != null) {
            endpoint.stop();
        }
    }
    
    private Service service;
    
    @Before
    public void setUp() throws MalformedURLException {
        URL wsdlUrl = new URL(URL_SERVICE + "?wsdl");
        QName serviceName = new QName("http://ws.learn.tc.cz/mtom/api", "xFileStorageService");
        service = Service.create(wsdlUrl, serviceName);
        port = service.getPort(XFileStoragePort.class);
    }

    //@Test
    public void testAddNoMockSetup() throws Exception {
        URL wsdlUrl = new URL("http://localhost:8081/ws/xfiletorageservice?wsdl");
        QName serviceName = new QName("http://ws.learn.tc.cz/mtom/api", "xFileStorageService");
        Service service = Service.create(wsdlUrl, serviceName);
        XFileStoragePort port = service.getPort(XFileStoragePort.class);

        XFileType file = port.downloadBinary(XFileStoragePortImpl.SAMPLE_FILE_XML);
        Assert.assertEquals(XFileStoragePortImpl.SAMPLE_FILE_XML, file.getFileName());
    }  

    @Test
    public void testSmallDownloadBinary() throws Exception {
        String filename="small_sample.xml";
        MTOMFeature feature = new MTOMFeature(true, 60000000);
        port = service.getPort(XFileStoragePort.class, feature);
        XFileType file = port.downloadBinary(filename);
        Assert.assertEquals(filename, file.getFileName());
    }
    
    //@Test
    public void testDownloadBinary() throws Exception {
        String filename=XFileStoragePortImpl.SAMPLE_FILE_XML;
        MTOMFeature feature = new MTOMFeature(true);
        port = service.getPort(XFileStoragePort.class, feature);
        XFileType file = port.downloadBinary(filename);
        Assert.assertEquals(filename, file.getFileName());
    }

    //@Test
    public void testSmallDownloadStream() throws Exception {
        String filename="small_sample.xml";
        MTOMFeature feature = new MTOMFeature(true);
        port = service.getPort(XFileStoragePort.class, feature);
        XFileOctetStreamType file = port.downloadOctetStream(filename);
        Assert.assertEquals(filename, file.getFileName());
    }

    //@Test
    public void testDownloadStream() throws Exception {
        String filename = XFileStoragePortImpl.SAMPLE_FILE_XML;
        MTOMFeature feature = new MTOMFeature(true);
        port = service.getPort(XFileStoragePort.class, feature);
        XFileOctetStreamType file = port.downloadOctetStream(filename);
        Assert.assertEquals(filename, file.getFileName());
    }

    //@Test//@see https://gist.github.com/tsirysndr/82042f26240128faaac677da7c77f398
    public void testReadResponse() throws Exception {
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();
        SOAPMessage soapMessage = createSoapMessage();

        // Send SOAP Message to SOAP Server
        String filename=XFileStoragePortImpl.SAMPLE_FILE_XML;//"small_sample.xml";//
        String xmlString = "<auth:filenameStream xmlns:auth=\"http://ws.learn.tc.cz/mtom/api\">"+filename+"</auth:filenameStream>";//(ok)"<filename>ahoj</filename>";//" (ko, byt v soap:envelope je xmlns:auth)<api:filename>ahojky</api:filename>";
        final SOAPElement stringToSOAPElement = stringToSOAPElement(soapMessage, xmlString);

        final SOAPMessage soapResponse = soapConnection.call(
                createSOAPRequest(soapMessage, stringToSOAPElement, "downloadOctetStream"),//downloadBinary, downloadOctetStream
                URL_SERVICE);

        // Print SOAP Response
        /*
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        soapResponse.writeTo(out);
        LOG.log(Level.INFO, "Response SOAP Message : {0}", new String(out.toByteArray()));
        //*/
    }

    private SOAPMessage createSoapMessage() throws SOAPException {
        final MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
        return messageFactory.createMessage();
    }

    private SOAPMessage createSOAPRequest(SOAPMessage soapMessage, SOAPElement body, String operation)
            throws SOAPException, IOException {
        // SOAP Envelope
        final SOAPEnvelope envelope = soapMessage.getSOAPPart().getEnvelope();

        // If you want to add namespace to the header, follow this constant
        String PREFIX_NAMESPACE = "api";
        String NAMESPACE = "http://ws.learn.tc.cz/mtom/api";
        envelope.addNamespaceDeclaration(PREFIX_NAMESPACE, NAMESPACE);

        // SOAP Body
        final SOAPBody soapBody = envelope.getBody();
        //SOAPBodyElement fn = soapBody.addBodyElement(envelope.createName("filename", "auth", "http://ws.learn.tc.cz/mtom/api"));
        //fn.addTextNode("ahojky");
        soapBody.addChildElement(body);

        // Mime Headers
        final MimeHeaders headers = soapMessage.getMimeHeaders();
        LOG.log(Level.INFO, "SOAPAction : " + URL_SERVICE + "{0}", operation);
        headers.addHeader("SOAPAction", URL_SERVICE + "/" + operation);

        soapMessage.saveChanges();

        /* Print the request message */
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        soapMessage.writeTo(out);
        LOG.log(Level.INFO, "Request SOAP Message :{0}", new String(out.toByteArray()));
        return soapMessage;
    }

    /**
     * Transform a String to a SOAP element
     *
     * @param xmlRequestBody the string body representation
     * @return a SOAP element
     * @throws SOAPException
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     */
    private SOAPElement stringToSOAPElement(SOAPMessage message, String xmlRequestBody)
            throws SOAPException, SAXException, IOException, ParserConfigurationException {

        // Load the XML text into a DOM Document
        final DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        final InputStream stream = new ByteArrayInputStream(xmlRequestBody.getBytes());
        final Document doc = builderFactory.newDocumentBuilder().parse(stream);

        // Use SAAJ to convert Document to SOAPElement
        final SOAPBody soapBody = message.getSOAPBody();

        // This returns the SOAPBodyElement that contains ONLY the Payload
        return soapBody.addDocument(doc);
    }

}
