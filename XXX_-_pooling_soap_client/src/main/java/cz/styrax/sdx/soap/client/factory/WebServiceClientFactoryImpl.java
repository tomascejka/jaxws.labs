package cz.styrax.sdx.soap.client.factory;

import com.predic8.schema.Schema;
import com.predic8.wsdl.AbstractSOAPBinding;
import com.predic8.wsdl.Binding;
import com.predic8.wsdl.BindingOperation;
import com.predic8.wsdl.Definitions;
import com.predic8.wsdl.Fault;
import com.predic8.wsdl.Message;
import com.predic8.wsdl.Operation;
import com.predic8.wsdl.Part;
import com.predic8.wsdl.PortType;
import com.predic8.wsdl.WSDLParser;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;

/**
 * Dynamic approach to create web service client which generate instances {@link Service} and web service port. WSDL file is parsed and read specific metadata
 * in order to create web service client, using library {@link WSDLParser}.
 *
 * @author tomas.cejka
 *
 * @see https://www.membrane-soa.org/soa-model-doc/1.4/java-api/parse-wsdl-java-api.htm
 */
public class WebServiceClientFactoryImpl implements WebServiceClientFactory {

    @Override
    public Service createService(String wsdlUrl) throws MalformedURLException {
        // parse/build valid URL
        URL serviceUrl = new URL(wsdlUrl);
        // parse exposed WSDL (in order to be able to create client programmatically)
        WSDLParser parser = new WSDLParser();
        Definitions defs = parser.parse(wsdlUrl);
        // GET:targetNamespace
        String tns = defs.getTargetNamespace();
        // GET:serviceName
        List<com.predic8.wsdl.Service> services = defs.getServices();
        if (services.isEmpty() || services.size() > 1) {
            throw new IllegalStateException("Unpredictable count of service(s)");//nechce se mi vymyslet slozitost >> navrh wsdl musi byt s jednou sluzbou
        }
        return Service.create(serviceUrl, new QName(tns, services.get(0).getName()));
    }

    @Override
    public <T> T createPort(String wsdlUrl, Class<T> serviceEndpointInterface) throws MalformedURLException {
        Service service = createService(wsdlUrl);
        T proxy = service.getPort(serviceEndpointInterface);

        // only for apache cxf (ws implementor) it allows to use thread-local in request context == request are always thread-safe,
        // see
        // https://cxf.apache.org/faq.html#FAQ-AreJAX-WSclientproxiesthreadsafe?
        // https://stackoverflow.com/questions/10599959/is-this-jax-ws-client-call-thread-safe
        ((BindingProvider) proxy).getRequestContext().put("thread.local.request.context", "true");

        return proxy;
    }

    /**
     * Show details information (at console actually) about exposed WSDL file at given location <code>wsdlUrl</code>
     *
     * @param wsdlUrl location of exposed WSDL file
     *
     * @see https://www.membrane-soa.org/soa-model-doc/1.4/java-api/parse-wsdl-java-api.htm
     */
    public void showWsdlApiInfo(String wsdlUrl) {
        WSDLParser parser = new WSDLParser();

        Definitions defs = parser.parse(wsdlUrl);//http://www.thomas-bayer.com/axis2/services/BLZService?wsdl"

        out("-------------- WSDL Details --------------");
        out("TargenNamespace: \t" + defs.getTargetNamespace());
        //out("Style: \t\t\t" + defs.getStyle());
        if (defs.getDocumentation() != null) {
            out("Documentation: \t\t" + defs.getDocumentation());
        }
        out("\n");

        /* For detailed schema information see the FullSchemaParser.java sample.*/
        out("Schemas: ");
        for (Schema schema : defs.getSchemas()) {
            out("  TargetNamespace: \t" + schema.getTargetNamespace());
        }
        out("\n");

        out("Messages: ");
        for (Message msg : defs.getMessages()) {
            out("  Message Name: " + msg.getName());
            out("  Message Parts: ");
            for (Part part : msg.getParts()) {
                out("    Part Name: " + part.getName());
                out("    Part Element: " + ((part.getElement() != null) ? part.getElement() : "not available!"));
                out("    Part Type: " + ((part.getType() != null) ? part.getType() : "not available!"));
                out("");
            }
        }
        out("");

        out("PortTypes: ");
        for (PortType pt : defs.getPortTypes()) {
            out("  PortType Name: " + pt.getName());
            out("  PortType Operations: ");
            for (Operation op : pt.getOperations()) {
                out("    Operation Name: " + op.getName());
                out("    Operation Input Name: "
                        + ((op.getInput().getName() != null) ? op.getInput().getName() : "not available!"));
                out("    Operation Input Message: "
                        + op.getInput().getMessage().getQname());
                out("    Operation Output Name: "
                        + ((op.getOutput().getName() != null) ? op.getOutput().getName() : "not available!"));
                out("    Operation Output Message: "
                        + op.getOutput().getMessage().getQname());
                out("    Operation Faults: ");
                if (op.getFaults().size() > 0) {
                    for (Fault fault : op.getFaults()) {
                        out("      Fault Name: " + fault.getName());
                        out("      Fault Message: " + fault.getMessage().getQname());
                    }
                } else {
                    out("      There are no faults available!");
                }

            }
            out("");
        }
        out("");

        out("Bindings: ");
        for (Binding bnd : defs.getBindings()) {
            out("  Binding Name: " + bnd.getName());
            out("  Binding Type: " + bnd.getPortType().getName());
            out("  Binding Protocol: " + bnd.getBinding().getProtocol());
            if (bnd.getBinding() instanceof AbstractSOAPBinding) {
                out("  Style: " + (((AbstractSOAPBinding) bnd.getBinding()).getStyle()));
            }
            out("  Binding Operations: ");
            for (BindingOperation bop : bnd.getOperations()) {
                out("    Operation Name: " + bop.getName());
                if (bnd.getBinding() instanceof AbstractSOAPBinding) {
                    out("    Operation SoapAction: " + bop.getOperation().getSoapAction());
                    out("    SOAP Body Use: " + bop.getInput().getBindingElements().get(0).getUse());
                }
            }
            out("");
        }
        out("");

        out("Services: ");
        for (com.predic8.wsdl.Service service : defs.getServices()) {
            out("  Service Name: " + service.getName());
            out("  Service Potrs: ");
            for (com.predic8.wsdl.Port port : service.getPorts()) {
                out("    Port Name: " + port.getName());
                out("    Port Binding: " + port.getBinding().getName());
                out("    Port Address Location: " + port.getAddress().getLocation()
                        + "\n");
            }
        }
        out("");
    }

    private static void out(String str) {
        System.out.println(str);
    }
}
