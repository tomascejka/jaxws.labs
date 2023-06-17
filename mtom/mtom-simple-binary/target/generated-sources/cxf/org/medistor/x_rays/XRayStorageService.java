package org.medistor.x_rays;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.5.1
 * 2022-05-02T06:36:18.623+02:00
 * Generated source version: 3.5.1
 *
 */
@WebServiceClient(name = "xRayStorageService",
                  wsdlLocation = "file:/D:/Repositories/labs/jaxws.labs/mtom/mtom-simple-binary/src/main/resources/wsdl/xray.wsdl",
                  targetNamespace = "http://mediStor.org/x-rays")
public class XRayStorageService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://mediStor.org/x-rays", "xRayStorageService");
    public final static QName XRayStorageServicePort = new QName("http://mediStor.org/x-rays", "xRayStorageServicePort");
    static {
        URL url = null;
        try {
            url = new URL("file:/D:/Repositories/labs/jaxws.labs/mtom/mtom-simple-binary/src/main/resources/wsdl/xray.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(XRayStorageService.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "file:/D:/Repositories/labs/jaxws.labs/mtom/mtom-simple-binary/src/main/resources/wsdl/xray.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public XRayStorageService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public XRayStorageService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public XRayStorageService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public XRayStorageService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public XRayStorageService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public XRayStorageService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns XRayStoragePort
     */
    @WebEndpoint(name = "xRayStorageServicePort")
    public XRayStoragePort getXRayStorageServicePort() {
        return super.getPort(XRayStorageServicePort, XRayStoragePort.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns XRayStoragePort
     */
    @WebEndpoint(name = "xRayStorageServicePort")
    public XRayStoragePort getXRayStorageServicePort(WebServiceFeature... features) {
        return super.getPort(XRayStorageServicePort, XRayStoragePort.class, features);
    }

}
