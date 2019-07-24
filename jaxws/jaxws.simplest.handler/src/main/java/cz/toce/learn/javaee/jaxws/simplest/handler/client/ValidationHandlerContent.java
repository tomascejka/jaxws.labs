package cz.toce.learn.javaee.jaxws.simplest.handler.client;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * SAX parser content handler - it servers for information about validation events (source of problem=>element, line, column)
 *
 * @author tomas.cejka
 */
public class ValidationHandlerContent extends DefaultHandler {

    private static final Logger LOG = Logger.getLogger(ValidationHandlerContent.class.getName());
    private static final boolean VERBOSE = false;
    private String element = "";

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        message(exception);
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        message(exception);
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        message(exception);
    }

    private void message(SAXParseException e) throws SAXException {
        int line = e.getLineNumber();
        int column = e.getColumnNumber();
        String msg = "Error (after/in): " + element + ", line: " + line + ", column: " + column + ", message: " + e.getMessage();
        throw new SAXException(msg, e);
    }

    @Override
    public void characters(char[] buf, int offset, int length) throws SAXException {
        if (VERBOSE) {
            LOG.info(new String(buf, offset, length));
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        if (VERBOSE) {
            LOG.log(Level.SEVERE, "START: {}, {}, {}", new Object[]{uri, localName, qName});
        }
        if (localName != null && !localName.isEmpty()) {
            element = localName;
        } else {
            element = qName;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (VERBOSE) {
            LOG.log(Level.SEVERE, "END: {}, {}, {}", new Object[]{uri, localName, qName});
        }
    }
}

