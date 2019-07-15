# Simplest SOAP fault
Studijní projekt pro případ, že chci nasimulovat soap fault chování. Má dva výstupy (nejenom, že jsem zjistil, jak soap fault fungují, ale minimalizoval jsem kód nuntý na straně soap ws klienta - stačí použít "chytře" co již bylo vymyšleno ve specce a implementaci v java ee).

## SOAP fault
Není nic jiného než chybový stav, jehož výstup můžete deklarovat/specifikovat do API (tzn. do WSDL/XSD). Jeho výstup a mapování se liší ve versi 1.1 a 1.2, ale to je detail, nicméně obě varianty se co do výstupu neliší (server odreportuje, že v procesu nastala chyba a popíše ji soap fault zprávou, která je popsaná xsd schématem). V java jazyce je soap fault chápán jako třída dědící z [Exception](https://docs.oracle.com/javase/7/docs/api/java/lang/Exception.html), tzn. je to prostá výjimka, kterou si v klientovy můžeš odchytit (její throws deklarace je vygenerována v příslušném port-u, platí pouze pro checked výjimky).

TODO, případně ještě vyzkoušet, ne-runtime expception, příklad aktuálně používá minimalistický přístup a v SEI metodě vyhodí [UnsupportedOperationException](https://docs.oracle.com/javase/7/docs/api/java/lang/UnsupportedOperationException.html), což runtime výjimka a její výstup v soap fault vypadá takto:
```
<S:Envelope xmlns:S="http://www.w3.org/2003/05/soap-envelope">
   <S:Body>
      <S:Fault xmlns:ns4="http://schemas.xmlsoap.org/soap/envelope/">
         <S:Code>
            <S:Value>S:Receiver</S:Value>
         </S:Code>
         <S:Reason>
            <S:Text xml:lang="cs">Not supported yet.</S:Text>
         </S:Reason>
      </S:Fault>
   </S:Body>
</S:Envelope>
```

Tzn. cílem je manuál obohatit o ukázku checked výjimky, explicitně definované v xsd, a vidět její výstup v soap fault, např. [příklad](http://itdoc.hitachi.co.jp/manuals/3020/30203Y2310e/EY230182.HTM):
```
<?xml version="1.0" ?>
<S:Envelope xmlns:S="http://www.w3.org/2003/05/soap-envelope">
  <S:Body>
    <ns3:Fault xmlns:ns2="http://schemas.xmlsoap.org/soap/envelope/"
               xmlns:ns3="http://www.w3.org/2003/05/soap-envelope">
      <ns3:Code>
        <ns3:Value>ns3:Receiver</ns3:Value>
      </ns3:Code>
      <ns3:Reason>
        <ns3:Text xml:lang="ja">Something happens.</ns3:Text>
      </ns3:Reason>
      <ns3:Detail>
        <env:UserDefinedFault xmlns:env="http://example.com/sample">
          <additionalInfo>257</additionalInfo>
          <detail>Failed by some reason.</detail>
          <message>Contact your administrator.</message>
        </env:UserDefinedFault>
      </ns3:Detail>
    </ns3:Fault>
  </S:Body>
</S:Envelope>
```

## Jak nejjednodušeji definovat SEI
Při psaní implementace, jsem si vylámal zuby, jak pojmenovávat třídy, popř. jaké používat package name a vůbec názvosloví. Po několikahodinovém zkoumání a nutno říct, že jsem ořezával dokud to šlo, jsem skončil s tímto výsledkem:

>**"Všechno je ve WSDL souboru"**

... detailní popis, co kde lze najít ve WSDL souboru, viz. níže.

### Postup pro jednoduché SEI
Stačí použít anotaci [WebService](https://docs.oracle.com/javaee/7/api/javax/jws/WebService.html) a explicitně uvést hodnoty těchto atributech:

 * serviceName, viz. `/definitions/service[@name]`
 * portName, viz. `/definitions/service/port[@name]`
 * targetNamespace, viz. `/definitions[@targetNamespace]`
 * endpointInterface (celá package cesta, tzn. targetNamespace+name), a name zjistim viz. `/definitions/portType[@name]`

... pozn: všechny xml cesty výše uvedené platí pro názvosloví praktikované/používané v [WSDL 1.1](https://www.w3.org/TR/wsdl.html#_introduction). A pak Váš soap klient v java jazyce vypadá následovně:

```
public class WebServiceClient {
    public static void main(String[] args) throws InternalErrorExceptionFault, MalformedURLException {
        SimpleWebServiceFault service = new SimpleWebServiceFault(new URL("http://localhost:8080/ws/simplest/fault/api"));
        SimpleWebServiceFaultPortType port = service.getSimpleWebServiceFaultSoap12Http();
        HelloRequest request = new HelloRequest();
        request.setGreetings("Hello from Tomas");
        System.out.println(port.helloMessage(request).getResultText());// k tomuto nedojde(resp. k getResultText), upadne to zamerna na soap fault
    }
}
```

## Jak projekt použít/spustit
Naklonuj si příslušný adresář via git a přesuň se do root adresáře projektu.

### Build project
Používám maven, takže v příslušném modulu (více modulový projekt v maven terminologii) spusť tento příkaz:

```
mvn clean package
``` 

### Publikování endpoint-u
Pro publikování WSDL via ([Endpoint](https://docs.oracle.com/javase/7/docs/api/javax/xml/ws/Endpoint.html)) používám nativní JDK chování/nástroj a poté jsem schopen spustit zbuildovanou/zkompilovanou třídu ([WebServicePublisher.java](https://github.com/tomascejka/jaxws.labs/blob/master/jaxws/jaxws.simplest/src/main/java/cz/toce/learn/javaee/ws/simplest/fault/server/WebServicePublisher.java)), který vystaví WSDL na adrese = `http://localhost:8080/ws/SimpleWebService`. Pro spuštění použiji maven plugin [exec](http://www.mojohaus.org/exec-maven-plugin/usage.html) - spuštění je blokující, takže tento skript spouštím ve svém vlastním okně (v tomto případě pomocí Windows CMD/Batch příkazu: `start call ...`):

```
start call mvn exec:java -Dexec.mainClass="cz.toce.learn.javaee.jaxws.simplest.fault.server.WebServicePublisher"
```

### Spuštění klienta
Pro spuštění na klientské straně - použiji opět předkompilovanou třídu ([WebServiceClient.java](https://github.com/tomascejka/jaxws.labs/blob/master/jaxws/jaxws.simplest/src/main/java/cz/toce/learn/javaee/ws/simplest/client/impl/WebServiceClient.java))(připravenou ve stejném projektu,pouze pro jednoduchost, v praxi by byla klientská implementace v jiném projektu) a opět ji zavolám pomocí maven pluginu [exec](http://www.mojohaus.org/exec-maven-plugin/usage.html):

```
mvn exec:java -Dexec.mainClass="cz.toce.learn.javaee.jaxws.simplest.fault.client.WebServiceClient"
```

Výstupem je výpis z konsole, že je vrácen soap fault (v1.2). viz níže:

<pre>
[INFO] --- exec-maven-plugin:1.6.0:java (default-cli) @ jaxws.simplest.fault ---
[WARNING]
com.sun.xml.internal.ws.fault.ServerSOAPFaultException: Client received SOAP Fault from server: Not supported yet. Please see the server log to find more detail regarding exact cause of the failure.
        at com.sun.xml.internal.ws.fault.SOAP12Fault.getProtocolException(SOAP12Fault.java:214)
        at com.sun.xml.internal.ws.fault.SOAPFaultBuilder.createException(SOAPFaultBuilder.java:116)
        at com.sun.xml.internal.ws.client.sei.StubHandler.readResponse(StubHandler.java:238)
        at com.sun.xml.internal.ws.db.DatabindingImpl.deserializeResponse(DatabindingImpl.java:189)
        at com.sun.xml.internal.ws.db.DatabindingImpl.deserializeResponse(DatabindingImpl.java:276)
        at com.sun.xml.internal.ws.client.sei.SyncMethodHandler.invoke(SyncMethodHandler.java:104)
        at com.sun.xml.internal.ws.client.sei.SyncMethodHandler.invoke(SyncMethodHandler.java:77)
        at com.sun.xml.internal.ws.client.sei.SEIStub.invoke(SEIStub.java:147)
        at com.sun.proxy.$Proxy51.helloMessage(Unknown Source)
        at cz.toce.learn.javaee.jaxws.simplest.fault.client.WebServiceClient.main(WebServiceClient.java:65)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:498)
        at org.codehaus.mojo.exec.ExecJavaMojo$1.run(ExecJavaMojo.java:282)
        at java.lang.Thread.run(Thread.java:748)
[INFO] ------------------------------------------------------------------------
</pre>
