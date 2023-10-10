package cz.tc.learn.cxf;

import javax.jws.WebService;
//import org.apache.contract.helloworld.HelloWorldPortType;

@WebService(targetNamespace = "http://www.apache.org/contract/HelloWorld",
    portName="HelloWorldPort",
    serviceName="HelloWorldService",
    endpointInterface = "org.apache.contract.helloworld.HelloWorldPortType")
public class HelloWorldPortTypeImpl /*implements HelloWorldPortType*/ {

    public String sayHi(String text) {
        return "Hello " + text;
    }
}

