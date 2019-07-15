package cz.toce.learn.javaee.ws.simplest.server;

import javax.jws.WebService;
import javax.xml.ws.BindingType;

/**
 * <p>Toto je nejjednodussi zapis webove sluzby, kt. je mozne vystavit a je 
 * spustitelny/volatelny klientkymi systemy.</p>
 * 
 * @author tomas.cejka
 */
@WebService(
        serviceName = "SimpleWebServiceImpl",
        portName = "SimpleWebServiceSoap11Http",
        targetNamespace = SimpleWebServicePortType.ENDPOINT_TNS,
        endpointInterface = SimpleWebServicePortType.ENDPOINT_INTERFACE
)
@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP11HTTP_BINDING)
public class SimpleWebServiceImpl implements SimpleWebServicePortType {

    @Override
    public String hello(String txt) {
        return "Hello " + txt + " !";
    }
}
