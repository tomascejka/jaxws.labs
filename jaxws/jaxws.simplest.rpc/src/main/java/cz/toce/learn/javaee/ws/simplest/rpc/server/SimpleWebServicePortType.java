package cz.toce.learn.javaee.ws.simplest.rpc.server;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import static cz.toce.learn.javaee.ws.simplest.rpc.server.SimpleWebServicePortType.ENDPOINT_TNS;

/**
 * @author tomas.cejka
 */
@WebService(targetNamespace = ENDPOINT_TNS)
public interface SimpleWebServicePortType {
    
    public static final String ENDPOINT_INTERFACE = "cz.toce.learn.javaee.ws.simplest.rpc.server.SimpleWebServicePortType";
    public static final String ENDPOINT_TNS = "http://api.rpc.simplest.ws.javaee.learn.toce.cz";
    
    @WebMethod(operationName = "hello")
    String hello(@WebParam(name = "name")String txt);
    
}
