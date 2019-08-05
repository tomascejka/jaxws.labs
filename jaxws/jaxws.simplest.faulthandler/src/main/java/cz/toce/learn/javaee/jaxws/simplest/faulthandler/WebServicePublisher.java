package cz.toce.learn.javaee.jaxws.simplest.faulthandler;

import cz.toce.learn.javaee.jaxws.simplest.faulthandler.server.SimpleWebServiceFaultHandler11Impl;
import cz.toce.learn.javaee.jaxws.simplest.faulthandler.server.SimpleWebServiceFaultHandler12Impl;
import javax.xml.ws.Endpoint;

/**
 * Vystavi implementaci webove sluzby na specificke adresa, respektive poskytne jeji WSDL soubor
 * @author tomas.cejka
 */
public class WebServicePublisher {
    public static final String SERVICE_URL_11 = "http://localhost:8081/jax-ws/simplest/faulthandler11";
    public static final String SERVICE_URL_12 = "http://localhost:8081/jax-ws/simplest/faulthandler12";
    /**
     * Tímto je služba vystavená (pro klientské volání) na adrese: {@link SERVICE_URL}
     * a její WSDL soubor je dostupný (pro vygenerování klienta) na {@link SERVICE_URL}?WSDL 
     *
     * @param args parameters for call
     */
    public static void main(String[] args) {
        Endpoint.publish(SERVICE_URL_11, new SimpleWebServiceFaultHandler11Impl());
        System.out.println("--");
        System.out.println("Published wsdl (SOAP v1.1) can be downloaded at url: "+SERVICE_URL_11+"?wsdl");
        System.out.println("--");
    
        Endpoint.publish(SERVICE_URL_12, new SimpleWebServiceFaultHandler12Impl());
        System.out.println("--");
        System.out.println("Published wsdl (SOAP v1.2) can be downloaded at url: "+SERVICE_URL_12+"?wsdl");
        System.out.println("--");
    }
}
