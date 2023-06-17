package cz.tc.learn.mtom;

import javax.xml.ws.Endpoint;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final String URL_SERVICE="http://localhost:8080/ws/xfiletorageservice";
    
    public static void main( String[] args ) {
        Endpoint.publish(URL_SERVICE, new XFileStoragePortImpl());
    }
}
