package cz.tc.learn.cxf;

import cz.sodexo.am.intr.ntf.service.AmIntrNtfmWSPortType;
import cz.sodexo.am.intr.ntf.service.NotificationDataRequestType;
import cz.sodexo.am.intr.ntf.service.NotificationDataResponseType;
import cz.sodexo.am.intr.ntf.service.NotificationDataResultRequestType;
import cz.sodexo.am.intr.ntf.service.NotificationDataResultResponseType;
import javax.jws.WebService;

/**
 *
 * @author tomas.cejka
 */
@WebService(targetNamespace = "http://am.sodexo.cz/intr/ntf/service",
    portName="AmIntrNtfmWSPort",
    serviceName="AmIntrNtfmWSService",
    endpointInterface = "cz.sodexo.am.intr.ntf.service.AmIntrNtfmWSPortType")
public class AmIntrNtfmWsPortImpl implements AmIntrNtfmWSPortType {

    @Override
    public NotificationDataResultResponseType ntfmEventDataResponse(NotificationDataResultRequestType parameters) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public NotificationDataResponseType ntfmEventDataRequest(NotificationDataRequestType parameters) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
