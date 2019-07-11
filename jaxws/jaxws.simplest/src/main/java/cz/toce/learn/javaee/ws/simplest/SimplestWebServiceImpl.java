package cz.toce.learn.javaee.ws.simplest;

import javax.jws.WebService;

/**
 * <p>Toto je nejjednodussi zapis webove sluzby, kt. je mozne vystavit a je 
 * spustitelny/volatelny klientkymi systemy.</p>
 * 
 * @author tomas.cejka
 */
@WebService(
        /*serviceName = "SimplestWebService",*/ // kdyz zakomentovano tzn., ze vznikne @WebServiceClient s nazvem SimplestWebServiceImpl+Service 
        portName = "SimplestWebServicePort",
        targetNamespace = SimplestWebService.SERVICE_TNS,
        endpointInterface = "cz.toce.learn.javaee.ws.simplest.SimplestWebService"
)
public class SimplestWebServiceImpl implements SimplestWebService {

    @Override
    public String hello(String txt) {
        return "Hello " + txt + " !";
    }
}
