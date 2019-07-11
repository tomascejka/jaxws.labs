package cz.toce.learn.javaee.ws.simplest;

import javax.xml.ws.Endpoint;

/**
 * Vystavi implementaci webove sluzby na specificke adresa, respektive poskytne jeji WSDL soubor
 * @author tomas.cejka
 */
public class SimplestWebServicePublisher {
    private static final String SERVICE_URL = "http://localhost:8080/ws/SimplestWebService";
    /**
     * Tímto je služba vystavená (pro klientské volání) na adrese: {@link SERVICE_URL}
     * a její WSDL soubor je dostupný (pro vygenerování klienta) na http://localhost:8080/ws/SimplestWebService?WSDL 
     *
     * @param args parameters for call
     */
    public static void main(String[] args) {
        Endpoint.publish(SERVICE_URL, new SimplestWebServiceImpl());
        System.out.println("--");
        System.out.println("WebService has been published at url: "+SERVICE_URL);
        System.out.println("Published wsdl can be downloaded at url: "+SERVICE_URL+"?wsdl");
        System.out.println("--");
        System.out.println("... this thread/console will be blocked/publishing wsdl service");
    }
}
