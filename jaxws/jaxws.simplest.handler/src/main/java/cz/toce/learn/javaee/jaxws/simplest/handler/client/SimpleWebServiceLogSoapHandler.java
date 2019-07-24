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
 * Jednoducha implementace logovaciho handler-u
 * 
 * @author tomas.cejka
 */
public class SimpleWebServiceLogSoapHandler implements SOAPHandler<SOAPMessageContext> {

    private static final Logger LOG = Logger.getLogger(SimpleWebServiceLogSoapHandler.class.getName());
    
    @Override
    public Set<QName> getHeaders() {
        return Collections.EMPTY_SET;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        LOG.info("-- Client soap handler: handleMessage -- ");
        try{
            log(context);
        } catch (SOAPException | IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        LOG.info("-- Client soap handler: handleFault -- ");
        try{
            log(context);
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
    
    private void log(SOAPMessageContext context) throws IOException, SOAPException {
        Boolean isOutbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        String direction = isOutbound ? "outbound" : "inbound";        
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            context.getMessage().writeTo(baos);
            LOG.info(MessageFormat.format("Message ({0}) : \n {1}", direction, XmlFormatter.format(baos.toString())));
        } finally {
        }
    }
    
}
