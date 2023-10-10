package cz.toce.learn.javaee.jaxws.simplest.faulthandler.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

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
        try ( ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
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

    /**
      * No jeste proverit to parsovani stringu apod... prevzato z webu
      * 
      * @author tomas.cejka
      * 
      * @see https://stackoverflow.com/questions/139076/how-to-pretty-print-xml-from-java?page=1&tab=votes#tab-top
      */
    private static class XmlFormatter {

        private static final Logger LOG = Logger.getLogger(XmlFormatter.class.getName());

        public static String format(String unformattedXmlString) {
            try {
                Document parsed = parseXmlString(unformattedXmlString);
                if (parsed != null) {
                    StreamResult result = new StreamResult(new StringWriter());
                    DOMSource source = new DOMSource(parsed);
                    Transformer transformer = TransformerFactory.newInstance().newTransformer();
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                    transformer.transform(source, result);
                    return result.getWriter().toString();
                }
            } catch (TransformerException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
            return "N/A";
        }

        private static Document parseXmlString(String in) {
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                InputSource is = new InputSource(new StringReader(in));
                return db.parse(is);
            } catch (ParserConfigurationException | IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
            } catch (org.xml.sax.SAXException ex) {
                Logger.getLogger(SimpleWebServiceSoapHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }
}
