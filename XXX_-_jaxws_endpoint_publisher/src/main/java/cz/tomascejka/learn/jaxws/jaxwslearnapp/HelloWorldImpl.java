package cz.tomascejka.learn.jaxws.jaxwslearnapp;

import javax.jws.WebService;

@WebService(endpointInterface = "com.mkyong.ws.HelloWorld")
public class HelloWorldImpl implements HelloWorld{

	@Override
	public String getHelloWorldAsString(String name) {
		return "Hello World JAX-WS " + name;
	}

}
