package org.medistor.x_rays;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.5.1
 * 2022-05-02T06:36:18.623+02:00
 * Generated source version: 3.5.1
 *
 */
@WebService(targetNamespace = "http://mediStor.org/x-rays", name = "xRayStoragePort")
@XmlSeeAlso({org.medistor.types.ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface XRayStoragePort {

    @WebMethod
    @WebResult(name = "success", targetNamespace = "", partName = "success")
    public boolean store(

        @WebParam(partName = "record", name = "xRay", targetNamespace = "http://mediStor.org/types/")
        org.medistor.types.XRayType record
    );
}