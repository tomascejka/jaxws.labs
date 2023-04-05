package cz.tc.learn.ws.mtom.binary;

import cz.tc.learn.ws.mtom.binary.api.FileStorageBinaryPort;
import cz.tc.learn.ws.mtom.binary.types.FileBinaryType;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

/**
 * @author tomas.cejka
 * 
 * changes:
 *  - @MTOM(enabled = true, threshold = 6000)
 */
@WebService(
        serviceName="fileStorageBinaryService", // viz. /definitions/service[@name]
        portName = "fileStorageBinaryServicePort",// viz. /definitions/service/port[@name]
        targetNamespace = "http://ws.learn.tc.cz/mtom/binary/api", // viz. /definitions[@targetNamespace]
        endpointInterface = "cz.tc.learn.ws.mtom.binary.api.FileStorageBinaryPort" // viz. složenina, viz. targetNamespace + /definitions/portType[@name]
)
public class FileStorageBinaryServiceImplV3 implements FileStorageBinaryPort {
    
    private static final Logger LOG = Logger.getLogger(FileStorageBinaryServiceImplV3.class.getName());
    
    public static final String SMALL_SAMPLE_FILE_XML = "small_sample.xml";
    public static final String SAMPLE_FILE_XML = "sample.xml";
    public static final String SAMPLE_FILE_PDF = "sample.pdf";

    @Override
    public boolean uploadBinary(FileBinaryType file) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @MTOM(threshold = 60000)
    @Override
    public FileBinaryType downloadBinary(String filename) {
        FileBinaryType response;
        try {
            URL resource = this.getClass().getClassLoader().getResource(filename);
            byte[] data = Files.readAllBytes(Paths.get(resource.toURI()));
            response = new FileBinaryType();
            response.setFileName(filename);
            response.setContentData(data);
            return response;
        } catch (IOException | URISyntaxException ex) {
            LOG.log(Level.SEVERE, "file cannot be found", ex);
            response = new FileBinaryType();
            response.setFileName("file cannot be found");
            return response;
        }
    }
}
