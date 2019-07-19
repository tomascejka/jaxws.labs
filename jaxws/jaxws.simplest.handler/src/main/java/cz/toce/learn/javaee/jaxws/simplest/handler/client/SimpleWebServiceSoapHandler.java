package cz.toce.learn.javaee.jaxws.simplest.handler.client;

import cz.toce.learn.javaee.jaxws.simplest.handler.tool.XmlFormatter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 * @author tomas.cejka
 */
public class SimpleWebServiceSoapHandler implements SOAPHandler<SOAPMessageContext> {

    private static final Logger LOG = Logger.getLogger(SimpleWebServiceSoapHandler.class.getName());
    
    @Override
    public Set<QName> getHeaders() {
        return Collections.EMPTY_SET;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        LOG.info("-- Client soap handler -- ");
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            context.getMessage().writeTo(baos);
            LOG.info(MessageFormat.format("Response : \n {0}", XmlFormatter.format(baos.toString())));
        } catch (SOAPException | IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    

    @Override
    public void close(MessageContext context) {
        // do nothing ...
    }
    
}
