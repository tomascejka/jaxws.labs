package cz.toce.learn.javaee.ws.simplest.client;

import cz.toce.learn.javaee.ws.simplest.api.SimpleWebServicePortType;
import cz.toce.learn.javaee.ws.simplest.api.SimpleWebServiceImpl;

/**
 * <p>Tyto importy cz.toce.learn.javaee.ws.simplest.api.* jsou struktury, 
 * kt. vygeneroval wsimport, tzn. api, kt. vzeslo z vystaveného WSDL.</p>
 *
 * <p>Jsou v package, kt. je definován v anotacich a jeho atributu ´targetNamespace´ u interface/implementace na serverové
 * straně, viz. SimplestWebService, SimplestWebServiceImpl java soubory, viz. Code 0 a 1.</p>
 */


/**
 * <p>
 * Klientska cast, ktera potrebuje mit vygenerovane struktury dle WSDL souboru,
 * tzn. bacha na package (protoze mam server/client v jednom java projektu).</p>
 *
 * @author tomas.cejka
 */
public class WebServiceClient {

    public static void main(String[] args) {
        SimpleWebServiceImpl service = new SimpleWebServiceImpl();
        SimpleWebServicePortType port = service.getSimpleWebServiceSoap11Http();
        System.out.println(port.hello("Tomas"));
    }
}