package com.examples.wsdl.helloservice_wsdl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * This class was generated by Apache CXF 3.1.6
 * 2016-06-19T22:26:58.693+02:00
 * Generated source version: 3.1.6
 * 
 */
@WebService(targetNamespace = "http://www.examples.com/wsdl/HelloService.wsdl", name = "HelloPortType")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface HelloPortType {

    @WebMethod(action = "sayHello")
    @WebResult(name = "greeting", targetNamespace = "", partName = "greeting")
    public java.lang.String sayHello(
        @WebParam(partName = "firstName", name = "firstName", targetNamespace = "")
        java.lang.String firstName
    );
}
