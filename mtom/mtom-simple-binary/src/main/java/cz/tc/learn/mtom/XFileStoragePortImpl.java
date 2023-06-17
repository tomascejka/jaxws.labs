package cz.tc.learn.mtom;

import cz.tc.learn.ws.mtom.api.XFileStoragePort;
import cz.tc.learn.ws.mtom.types.XFileOctetStreamType;
import cz.tc.learn.ws.mtom.types.XFileType;
import cz.tc.learn.ws.mtom.types.XFileXmlType;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.MTOM;

/**
 * MTOM example - sample mtom using via saaj/jax-ws java implementation
 * 
 * @author tomas.cejka
 * 
 * @see https://docs.oracle.com/cd/E13222_01/wls/docs103/webserv/jws.html
 * @see https://docs.jboss.org/author/display/AS71/Java%20API%20for%20XML%20Web%20Services%20(JAX-WS).html
 */
@WebService(
        serviceName="xFileStorageService", // viz. /definitions/service[@name]
        portName = "xFileStorageServicePort",// viz. /definitions/service/port[@name]
        targetNamespace = "http://ws.learn.tc.cz/mtom/api", // viz. /definitions[@targetNamespace]
        endpointInterface = "cz.tc.learn.ws.mtom.api.XFileStoragePort" // viz. složenina, viz. targetNamespace + /definitions/portType[@name]
)
/* 
 * javax.xml.ws.BindingType - You can use the @BindingType annotation on the JavaBeans endpoint implementation class to enable MTOM
 * @see https://www.ibm.com/docs/en/was/9.0.5?topic=ws-jax-annotations
 * @see https://www.ibm.com/docs/en/was/8.5.5?topic=up-enabling-mtom-jax-ws-web-services
 * @see https://docs.oracle.com/javase/7/docs/api/javax/xml/ws/BindingType.html
 */
@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_MTOM_BINDING)
@MTOM(/*threshold = 0*/) // javax.xml.ws.soap.MTOM, @see https://www.ibm.com/docs/en/was/8.5.5?topic=up-enabling-mtom-jax-ws-web-services
//@HandlerChain(file = "handlers.xml")
public class XFileStoragePortImpl implements XFileStoragePort {
    
    private static final Logger LOG = Logger.getLogger(XFileStoragePortImpl.class.getName());
    
    public static final String SAMPLE_FILE_XML = "sample.xml";
    public static final String SAMPLE_FILE_PDF = "sample.pdf";

    @Override
    public boolean uploadBinary(XFileType file) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public XFileType downloadBinary(String filename) {
        try {
            URL resource = this.getClass().getClassLoader().getResource(filename);
            byte[] data = Files.readAllBytes(Paths.get(resource.toURI()));
            XFileType response = new XFileType();
            response.setFileName(filename);
            response.setContentData(data);
            return response;
        } catch (IOException | URISyntaxException ex) {
            LOG.log(Level.SEVERE, "file cannot be found", ex);
            XFileType response = new XFileType();
            response.setFileName("file cannot be found");
            return response;
        }
    }

    @Override
    public boolean uploadOctetStream(XFileOctetStreamType file) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public XFileOctetStreamType downloadOctetStream(String filename) {
        XFileOctetStreamType response = new XFileOctetStreamType();
        try {
            ByteArrayDataSource bad = new ByteArrayDataSource(getFileContentAsBytes(filename), "application/octet-stream; charset=UTF-8");
            response.setFileName(filename);
            response.setContentData(new DataHandler(bad));
            return response;
        } catch (IOException | URISyntaxException ex) {
            LOG.log(Level.SEVERE, "file cannot be found", ex);
            response.setFileName("file cannot be found");
            return response;
        }
    }

    @Override
    public boolean uploadXml(XFileXmlType file) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public XFileXmlType downloadXml(String filename) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    private byte[] getFileContentAsBytes(String filename) throws URISyntaxException, IOException{
        URL resource = this.getClass().getClassLoader().getResource(filename);
        byte[] data = Files.readAllBytes(Paths.get(resource.toURI()));
        return data;
    }
    
}
