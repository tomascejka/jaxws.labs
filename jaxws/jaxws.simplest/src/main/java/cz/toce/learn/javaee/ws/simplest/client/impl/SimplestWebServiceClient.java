package cz.toce.learn.javaee.ws.simplest.client.impl;
/**
 * <p>Tyto importy cz.toce.learn.javaee.ws.simplest.client.api.* jsou struktury, 
 * kt. vygeneroval wsimport, tzn. api, kt. vzeslo z vystaveného WSDL.</p>
 *
 * <p>Jsou v package, kt. je definován v anotacich a jeho atributu ´targetNamespace´ u interface/implementace na serverové
 * straně, viz. SimplestWebService, SimplestWebServiceImpl java soubory, viz. Code 0 a 1.</p>
 */
import cz.toce.learn.javaee.ws.simplest.client.api.SimplestWebService;
import cz.toce.learn.javaee.ws.simplest.client.api.SimplestWebServiceImplService;

/**
 * <p>
 * Klientska cast, ktera potrebuje mit vygenerovane struktury dle WSDL souboru,
 * tzn. bacha na package (protoze mam server/client v jednom java projektu).</p>
 *
 * @author tomas.cejka
 */
public class SimplestWebServiceClient {

    public static void main(String[] args) {
        SimplestWebServiceImplService service = new SimplestWebServiceImplService();
        SimplestWebService port = service.getSimplestWebServicePort();
        System.out.println(port.hello("Tomas"));
    }
}