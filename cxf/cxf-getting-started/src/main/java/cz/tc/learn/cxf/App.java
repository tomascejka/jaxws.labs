package cz.tc.learn.cxf;

import cz.sodexo.am.intr.ntf.service.NotificationDataRequestType;
import cz.sodexo.am.intr.ntf.type.ProductType;
import cz.sodexo.cmn.type.common.ExternalIdentificator;
import javax.xml.ws.Endpoint;
import javax.xml.ws.soap.SOAPBinding;

/**
 * @author tomas.cejka
 */
public class App {
    
    public static void main(String[] args) {
        NotificationDataRequestType t = new NotificationDataRequestType();
        t.setCompany("Neco");
        t.setEmployee("tomas");
        t.setExtReqId("12345");
        t.setNotificationType("NTFM_EVENT_TEST");
        t.setProductType(ProductType.ACCOUNT_APC);
        ExternalIdentificator ex = new ExternalIdentificator();
        ex.setApplication("12345");
        ex.setCorellationId("CID12345");
        ex.setIdInApplication("IIA12345");
        t.setRequestId(ex);
        System.out.println("info:\n"+t);
        
        Endpoint server = Endpoint.create(SOAPBinding.SOAP12HTTP_BINDING, new AmIntrNtfmWsPortImpl());
        server.publish("http://localhost:8888/ws/test");
    }
    
}
