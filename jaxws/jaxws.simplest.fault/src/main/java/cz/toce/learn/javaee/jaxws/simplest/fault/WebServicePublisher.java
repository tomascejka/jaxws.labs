package cz.toce.learn.javaee.jaxws.simplest.fault;

import cz.toce.learn.javaee.jaxws.simplest.fault.server.SimpleWebServiceFault11Impl;
import cz.toce.learn.javaee.jaxws.simplest.fault.server.SimpleWebServiceFault12Impl;
import javax.xml.ws.Endpoint;

/**
 * Vystavi implementaci webove sluzby na specificke adresa, respektive poskytne jeji WSDL soubor
 * @author tomas.cejka
 */
public class WebServicePublisher {
    public static final String SERVICE_URL_11 = "http://localhost:8081/jax-ws/simplest/fault11";
    public static final String SERVICE_URL_12 = "http://localhost:8081/jax-ws/simplest/fault12";
    /**
     * Tímto je služba vystavená (pro klientské volání) na adrese: {@link SERVICE_URL#SERVICE_URL_12}
     * a její WSDL soubor je dostupný (pro vygenerování klienta) na {@link SERVICE_URL#SERVICE_URL_12}?WSDL 
     *
     * @param args parameters for call
     */
    public static void main(String[] args) {
    
        Endpoint.publish(SERVICE_URL_11, new SimpleWebServiceFault11Impl());
        System.out.println("--");
        System.out.println("Published wsdl (SOAP v1.1) can be downloaded at url: "+SERVICE_URL_11+"?wsdl");
        System.out.println("--");
    
        Endpoint.publish(SERVICE_URL_12, new SimpleWebServiceFault12Impl());
        System.out.println("--");
        System.out.println("Published wsdl (SOAP v1.2) can be downloaded at url: "+SERVICE_URL_12+"?wsdl");
        System.out.println("--");
        
        System.out.println("... this thread/console will be blocked/publishing wsdl service");
    }
}
