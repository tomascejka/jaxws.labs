package cz.toce.learn.javaee.ws.simplest;

import static cz.toce.learn.javaee.ws.simplest.SimplestWebService.SERVICE_TNS;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @author tomas.cejka
 */
@WebService(targetNamespace = SERVICE_TNS)
public interface SimplestWebService {
    
    public static final String SERVICE_TNS = "http://simplest.ws.javaee.learn.toce.cz/client/api";
    
    @WebMethod(operationName = "hello")
    String hello(@WebParam(name = "name")String txt);
    
}
