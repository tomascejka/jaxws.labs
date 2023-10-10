package org.wsdl.taxonomy;

import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.wsdl.taxonomy.model.ValueExampleObject;

import com.predic8.schema.Schema;
import com.predic8.wsdl.AbstractSOAPBinding;
import com.predic8.wsdl.Binding;
import com.predic8.wsdl.BindingOperation;
import com.predic8.wsdl.Definitions;
import com.predic8.wsdl.Fault;
import com.predic8.wsdl.Message;
import com.predic8.wsdl.Operation;
import com.predic8.wsdl.Part;
import com.predic8.wsdl.Port;
import com.predic8.wsdl.PortType;
import com.predic8.wsdl.Service;
import com.predic8.wsdl.WSDLParser;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) throws Exception {
    	
    	useFreemarker();
    	
    }
    
    private static void useFreemarker() throws Exception {
        // 1. Configure FreeMarker
        //
        // You should do this ONLY ONCE, when your application starts,
        // then reuse the same Configuration object elsewhere.
        Configuration cfg = new Configuration(new Version(2, 3, 30));
        // Where do we load the templates from:
        cfg.setClassForTemplateLoading(App.class, "/");//http://zetcode.com/java/freemarker/
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        
        // 2. Proccess template(s)
        //
        // You will do this for several times in typical applications.

        // 2.1. Prepare the template input:

        Map<String, Object> input = new HashMap<String, Object>();

        input.put("title", "Vogella example");

        input.put("exampleObject", new ValueExampleObject("Java object", "me"));

        List<ValueExampleObject> systems = new ArrayList<ValueExampleObject>();
        systems.add(new ValueExampleObject("Android", "Google"));
        systems.add(new ValueExampleObject("iOS States", "Apple"));
        systems.add(new ValueExampleObject("Ubuntu", "Canonical"));
        systems.add(new ValueExampleObject("Windows7", "Microsoft"));
        input.put("systems", systems);
        
        // 2.2. Get the template

        Template template = cfg.getTemplate("helloworld.ftl");

        // 2.3. Generate the output

        // Write output to the console
        template.process(input, new OutputStreamWriter(System.out));

        // For the sake of example, also write output into a file:
        try(Writer fileWriter = new FileWriter(new File("output.html"))) {
            template.process(input, fileWriter);
        }       
    }
    
    @SuppressWarnings("unused")
	private static void parseWsdl() {
        WSDLParser parser = new WSDLParser();
 
        Definitions defs = parser.parse("http://www.thomas-bayer.com/axis2/services/BLZService?wsdl");
 
        out("-------------- WSDL Details --------------");
        out("TargenNamespace: \t" + defs.getTargetNamespace());
        
        @SuppressWarnings("unchecked")
		Map<String, String> namespaces=(Map<String, String>) defs.getNamespaceContext();
        out("Namespaces:");
        for (String ns : namespaces.values()) {
        	out("	"+ns);
		}
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
                out("    Part Type: " + ((part.getType() != null) ? part.getType() : "not available!" ));
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
                } else out("      There are no faults available!");
                 
            }
            out("");
        }
        out("");
 
        out("Bindings: ");
        for (Binding bnd : defs.getBindings()) {
            out("  Binding Name: " + bnd.getName());
            out("  Binding Type: " + bnd.getPortType().getName());
            out("  Binding Protocol: " + bnd.getBinding().getProtocol());
            if(bnd.getBinding() instanceof AbstractSOAPBinding) out("  Style: " + (((AbstractSOAPBinding)bnd.getBinding()).getStyle()));
            out("  Binding Operations: ");
            for (BindingOperation bop : bnd.getOperations()) {
                out("    Operation Name: " + bop.getName());
                if(bnd.getBinding() instanceof AbstractSOAPBinding) {
                    out("    Operation SoapAction: " + bop.getOperation().getSoapAction());
                    out("    SOAP Body Use: " + bop.getInput().getBindingElements().get(0).getUse());
                }
            }
            out("");
        }
        out("");
 
        out("Services: ");
        for (Service service : defs.getServices()) {
            out("  Service Name: " + service.getName());
            out("  Service Potrs: ");
            for (Port port : service.getPorts()) {
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
