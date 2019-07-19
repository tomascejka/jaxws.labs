package cz.toce.learn.javaee.jaxws.simplest.handler.server;

import javax.xml.ws.Endpoint;

/**
 * Vystavi implementaci webove sluzby na specificke adresa, respektive poskytne jeji WSDL soubor
 * @author tomas.cejka
 */
public class WebServicePublisher {
    public static final String SERVICE_URL = "http://localhost:8082/ws/SimplestWebServiceHandler";
    /**
     * Tímto je služba vystavená (pro klientské volání) na adrese: {@link SERVICE_URL}
     * a její WSDL soubor je dostupný (pro vygenerování klienta) na {@link SERVICE_URL}?WSDL 
     *
     * @param args parameters for call
     */
    public static void main(String[] args) {
        Endpoint.publish(SERVICE_URL, new SimpleWebServiceHandlerImpl());
        System.out.println("--");
        System.out.println("WebService has been published at url: "+SERVICE_URL);
        System.out.println("Published wsdl can be downloaded at url: "+SERVICE_URL+"?wsdl");
        System.out.println("--");
        System.out.println("... this thread/console will be blocked/publishing wsdl service");
    }
}
