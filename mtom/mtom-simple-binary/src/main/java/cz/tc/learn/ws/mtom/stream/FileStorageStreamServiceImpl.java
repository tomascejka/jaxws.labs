package cz.tc.learn.ws.mtom.stream;

import cz.tc.learn.ws.mtom.stream.api.FileStorageStreamPort;
import cz.tc.learn.ws.mtom.stream.types.FileStreamType;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.jws.WebService;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.ws.BindingType;

/**
 * @author tomas.cejka
 */
@WebService(
        serviceName="fileStorageStreamService", // viz. /definitions/service[@name]
        portName = "fileStorageStreamServicePort",// viz. /definitions/service/port[@name]
        targetNamespace = "http://ws.learn.tc.cz/mtom/stream/api", // viz. /definitions[@targetNamespace]
        endpointInterface = "cz.tc.learn.ws.mtom.stream.api.FileStorageStreamPort" // viz. složenina, viz. targetNamespace + /definitions/portType[@name]
)
@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_MTOM_BINDING)
public class FileStorageStreamServiceImpl implements FileStorageStreamPort {
    
    private static final Logger LOG = Logger.getLogger(FileStorageStreamServiceImpl.class.getName());
    
    public static final String SMALL_SAMPLE_FILE_XML = "small_sample.xml";
    public static final String SAMPLE_FILE_XML = "sample.xml";
    public static final String SAMPLE_FILE_PDF = "sample.pdf";

    @Override
    public boolean uploadStream(FileStreamType file) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public FileStreamType downloadStream(String filename) {
        FileStreamType response;
        try {
            URL resource = this.getClass().getClassLoader().getResource(filename);
            byte[] data = Files.readAllBytes(Paths.get(resource.toURI()));
            response = new FileStreamType();
            response.setFileName(filename);
            
            DataHandler dh = new DataHandler(new ByteArrayDataSource(data, filename));
            response.setContentData(dh);
            return response;
        } catch (IOException | URISyntaxException ex) {
            LOG.log(Level.SEVERE, "file cannot be found", ex);
            response = new FileStreamType();
            response.setFileName("file cannot be found");
            return response;
        }
    }
}
