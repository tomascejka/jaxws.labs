package cz.tc.learn.ws.mtom.optimize;

import com.sun.xml.ws.developer.StreamingAttachment;
import com.sun.xml.ws.developer.StreamingDataHandler;
import cz.tc.learn.ws.mtom.stream.*;
import cz.tc.learn.ws.mtom.stream.api.FileStorageStreamPort;
import cz.tc.learn.ws.mtom.stream.types.FileStreamType;
import java.io.File;
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
import javax.xml.ws.WebServiceException;

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
public class FileStorageStreamOptimizeServiceImpl implements FileStorageStreamPort {
    
    private static final Logger LOG = Logger.getLogger(FileStorageStreamOptimizeServiceImpl.class.getName());
    
    public static final String SMALL_SAMPLE_FILE_XML = "small_sample.xml";
    public static final String SAMPLE_FILE_XML = "sample.xml";
    public static final String SAMPLE_FILE_PDF = "sample.pdf";

    @Override
    public boolean uploadStream(FileStreamType file) {
        try(StreamingDataHandler dh = (StreamingDataHandler) file.getContentData();) {
            File up = new File("src/test/resources/"+file.getFileName());
            dh.moveTo(up);
            dh.close();
            return true;
        } catch (Exception e) {
            throw new WebServiceException(e);
        }
    }

    /**
     * Download file via MTOM mechanism which it is optimized by <code>@StreamingAttachment</code> feature. 
     * 
     * @param filename of file which will be downloaded
     * @return soap response with mtom-based attachement (which it is give as stream of bytes)
     * 
     * @see https://docs.oracle.com/cd/E14571_01/web.1111/e13734/mtom.htm#WSADV143
     */
    @StreamingAttachment(dir = "/tmp" , parseEagerly = true, memoryThreshold=4L)//Attachments under (4000000L=)4MB are stored in memory.
    @Override
    public FileStreamType downloadStream(String filename) {
        FileStreamType response;
        try {
            URL resource = this.getClass().getClassLoader().getResource(filename);
            byte[] data = Files.readAllBytes(Paths.get(resource.toURI()));
            response = new FileStreamType();
            response.setFileName(filename);
            
            DataHandler dh = new DataHandler(new ByteArrayDataSource(data, "application/octet-stream"));
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
