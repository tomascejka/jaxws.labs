package cz.tc.learn.mtom;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.BodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.*;
import javax.xml.ws.handler.soap.*;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

/**
 *
 * @author tomas.cejka
 * 
 * @see https://examples.javacodegeeks.com/enterprise-java/jws/jax-ws-logging-with-handler-example/
 */
public class LogHandler implements SOAPHandler<SOAPMessageContext> {
    
    private static final Logger LOG = Logger.getLogger(LogHandler.class.getName());
 
    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        LOG.info("***handleMessage***");
        logSoapMessage(context);
        return true;
    }
 
    @Override
    public boolean handleFault(SOAPMessageContext context) {
        LOG.info("***handleFault***");
        logSoapMessage(context);
        return false;
    }
 
    @Override
    public void close(MessageContext context) {
        LOG.info("_______________close_____________ ");
    }
 
    @Override
    public Set<QName> getHeaders() {
        return new HashSet<>();
    }
 
    private void logSoapMessage(SOAPMessageContext context) {
        Boolean isOutBound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        SOAPMessage soapMsg = context.getMessage();
 
        try {
            if (isOutBound) {
                LOG.info("Intercepting outbound message:");
            } else {
                LOG.info("Intercepting inbound message:");
            }
 
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            soapMsg.writeTo(baos);
            LOG.info(new String(baos.toByteArray()));
            
            readResponseAsMtom(new ByteArrayInputStream(baos.toByteArray()));
 
            LOG.info("\n________________________________");
 
        } catch (SOAPException | IOException e) {
            e.printStackTrace();
        }
    }
    
    private void readResponseAsMtom(InputStream bais) {
        try {
            MimeMultipart mp = new MimeMultipart(new ByteArrayDataSource(bais, "multipart/related"));
            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart bodyPart = mp.getBodyPart(i);
                String partContentType = bodyPart.getContentType();
                //log.info partContentType;
                if (!partContentType.contains("application/xop")) {
                    byte[] mtomData = readAllBytes((InputStream) bodyPart.getContent());//pro java 1.9+((InputStream)bodyPart.getContent()).readAllBytes();//bytearrayinputstream
                    LOG.log(Level.INFO, "MTOM content:\n\n{0}", new String(mtomData));
                } else {
                    LOG.log(Level.INFO, "SOAP content:\n\n{0}", partContentType);
                }
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Problem parse SOAP/MTOM response", e);
        }
    }

    public static byte[] readAllBytes(InputStream inputStream) throws IOException {
        final int bufLen = 1024;
        byte[] buf = new byte[bufLen];
        int readLen;
        IOException exception = null;

        try ( ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
            while ((readLen = inputStream.read(buf, 0, bufLen)) != -1) {
                outputStream.write(buf, 0, readLen);
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            exception = e;
            throw e;
        } finally {
            if (exception == null) {
                inputStream.close();
            } else try {
                inputStream.close();
            } catch (IOException e) {
                exception.addSuppressed(e);
            }
        }
    }    
 
}
