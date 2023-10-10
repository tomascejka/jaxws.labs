package cz.tomascejka.learn.jaxws.jaxwslearnapp;

import javax.xml.ws.Endpoint;

public class EndpointPublisher {
	public static void main(String[] args) {
		Endpoint.publish("http://localhost:9999/ws/hello", new HelloWorldImpl());
	}
}
