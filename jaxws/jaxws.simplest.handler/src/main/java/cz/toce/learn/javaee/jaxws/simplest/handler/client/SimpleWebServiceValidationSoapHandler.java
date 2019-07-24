package cz.toce.learn.javaee.jaxws.simplest.handler.client;

import cz.toce.learn.javaee.jaxws.simplest.handler.tool.XmlFormatter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.soap.SOAPException;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Jednoducha implementace validacniho handler-u
 *
 * @author tomas.cejka
 */
public class SimpleWebServiceValidationSoapHandler implements SOAPHandler<SOAPMessageContext> {

    private static final Logger LOG = Logger.getLogger(SimpleWebServiceValidationSoapHandler.class.getName());

    private static final String MSG_IO_ERROR = "Problem read/write soap message into stream: {0}";
    private static final String MSG_SAX_ERROR = "Problem parsing soap message: {0}";
    private static final String MSG_SAX_NULL_ERROR = "Cannot fing a parser: {0}";
    private static final String MSG_SAX_IO_ERROR = "Problem parsing soap message (unable to be read): {0}";

    @Override
    public Set<QName> getHeaders() {
        return Collections.EMPTY_SET;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        QName operation = (QName) context.get(MessageContext.WSDL_OPERATION);
        Boolean isOutbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        String direction = isOutbound ? "outbound" : "inbound";
        try {
            context.getMessage().writeTo(baos);
        } catch (SOAPException | IOException ex) {
            LOG.severe(MessageFormat.format(MSG_IO_ERROR, ex.getMessage()));
            throw new IllegalStateException(MessageFormat.format(MSG_IO_ERROR, "for ("+direction+") " + operation));
        }
        InputSource source = new InputSource(new ByteArrayInputStream(baos.toByteArray()));

        try {
            //tip- build (sax parser-y) by mel byt poolovatelne;
            SAXParser parser = buildParser(getSchema());
            if (parser != null) {
                parser.parse(source, new ValidationHandlerContent());
            } else {
                LOG.warning(MessageFormat.format(MSG_SAX_NULL_ERROR, "for ("+direction+") " + operation));
            }
        } catch (SAXException ex) {
            LOG.severe(MessageFormat.format(MSG_SAX_ERROR, ex.getMessage()));
            throw new IllegalStateException(MessageFormat.format(MSG_SAX_ERROR, "for ("+direction+") " + operation));
        } catch (IOException ex) {
            LOG.severe(MessageFormat.format(MSG_SAX_IO_ERROR, ex.getMessage()));
            throw new IllegalStateException(MessageFormat.format(MSG_SAX_IO_ERROR, "for ("+direction+") " + operation));
        }

        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        LOG.info("-- Client soap handler -- ");
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
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

    private Schema getSchema() {
        try {
            Source[] schemas = new SAXSource[0];//vime,ze bude jedno xsd
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new File("src/main/webapp/WEB-INF/wsdl/simplest.xsd"));
            return schema;
        } catch (SAXException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new IllegalStateException("Problem during create schema", ex);
        }
    }

    private SAXParser buildParser(Schema schema) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        // Must be namespace aware to receive element names
        factory.setNamespaceAware(true);
        // Setting the Schema for validation
        factory.setSchema(schema);
        try {
            return factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new IllegalStateException("Problem with build SAX parser", ex);
        }
    }
}
