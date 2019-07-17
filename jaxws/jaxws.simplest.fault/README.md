# Simplest SOAP fault
*TLDR;* SOAP Fault je chybový stav, který je zobecněný a popsaný XSD schématem, který definuje specifikace SOAP[11,12]. Technickým výstupem je to vždy výjimka (pokud se bavíme o java jazyce), jejíchž chování je popsáno ve JAX-WS specifikaci [13]. Bacha aktuálně jsou dvě verse, které se obsahem liší (na úrovni xml formátu, SOAP fault zprávy - proto mnoho ukázek kódu - postihuje obě verse) - popř. viz Závěr (kapitolá níže).

Studijní projekt pro případ, že chci nasimulovat soap fault chování. Má dva výstupy (nejenom, že jsem zjistil, jak soap fault fungují, ale minimalizoval jsem kód nuntý na straně soap ws klienta - stačí použít "chytře" co již bylo vymyšleno ve specce a implementaci v java ee).

## Rozdělení SOAP Fault dle typu
Podle manuálu [1] soap fault xml zprávy vypadají různě (obsahují jinak pojmenované elementy) v SOAP versích (viz. dále). Dále dle zdroje [2] lze výjimky dělit na "Unmodeled"[6] a "Modeled"[7], což si lze vysvětlit několika způsoby jako:

* "runtime==Unmodeled" a "checked=Modeled"  (technický pohled vývojáře) >> vytvoří se xsd struktura a vznikne výjimka, kt. je v throws definici (checked exception)
* "technicalError==Unmodeled" a "businessError=Modeled" (analytický pohled) >> wsdl/soap java ee (dle [13]) implementace ji mapuji na javax.xml.ws.WebServiceException

... pokud to shrnu, tak jsou to všechno výjimky/chybové stavy, které WSDL specifiace[3,4] překládá dle SOAP specifikace[9,10] na SOAP Fault zprávy[11,12]. Podle WSDL specifikace je v případě "unmodeled" nutné takovou výjimku (chybový stav) deklarovat ve WSDL souboru (via WSDL binding SOAP [3,4]).

## Ukázky SOAP fault zpráv
Předpokládám, že checked výjimka níže uvedená (`UserDefinedFault`, viz. Figure 1) je definována v WSDL souboru (viz. odkaz[5]) - to je nutnost (tedy a pouze jenom pro checked/modeled chybové stavy).

### Vyhození  checked výjimky v kódu
V případech, kdy budeme používat "modeled" (checked exception) výjimku - tak ji lze vyhodit (viz. Figure 1) v programu (java) chybu[2], abychom mohli presentovat chování soap fault.
```
//Generate the fault bean and specify the information you want marshlling
UserDefinedFault fault = new UserDefinedFault();
fault.additionalInfo = 257;
fault.detail = "Failed by some reason.";
fault.message = "Contact your administrator.";
 
//wrapper exception class is thrown
throw new UserDefinedException(
 "Something happens.", fault );
```
*Figure 1* - příčina pádu v aplikaci - je vyhozena checked/modeled[6] výjimka (kt. je definována jako fault v WSDL[2])

### Vyhození runtime výjimky v kódu
V případech, kdy budeme používat "unmodeled" (runtime exception) výjimku - tak ji lze vyhodit (viz. Figure 1) v programu (java) chybu[2], abychom mohli presentovat chování soap fault. Toto chování a mapování by default je užitečné, že dojde k chybě v knihovánách třetích stran, tzn. ne ve Vašech kódu, tzn. chybový stav v soap specifikaci je ošetřen a namapuje i výjimky, jejichž příčina není ve Vašem kódu.
```
//runtime exception is thrown
throw new IllegalArgumentException( "Something illegal." );
```
*Figure 2* - příčina pádu v aplikaci - je vyhozena runtime/unmodeled[6] výjimka

Formát soap fault zpráv
-----------------------
Ukázky, jak vypadají xml soap fault zprávy. Dělím to dále dle typu vyjímek, které z webové služby mohou "vyletět". V případě Modeled výjimek použiji výše uvedenou - UserDefinedFault (viz. Figure 1).

### Modeled výjimky (checked exception)
Zdrojem mapování checked výjimek a jejich příkladů je odkaz. Takto vypadá soap fault dle SOAP 1.1
```
<?xml version="1.0" ?>
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
	<S:Body>
		<ns2:Fault xmlns:ns2="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns3="http://www.w3.org/2003/05/soap-envelope">
			<faultcode>ns2:Server</faultcode>
			<faultstring>Something happens.</faultstring>
			<detail>
				<ns2:UserDefinedFault xmlns:ns2="http://example.com/sample">
					<additionalInfo>257</additionalInfo>
					<detail>Failed by some reason.</detail>
					<message>Contact your administrator.</message>
				</ns2:UserDefinedFault>
			</detail>
		</ns2:Fault>
	</S:Body>
</S:Envelope>
```
*Figure 3* - SOAP Fault zpráva ve formátu deklarující SOAP v1.1

Takto vypadá soap fault dle SOAP 1.2. Zpráva vždycky obsahuje definici obou SOAP versí (viz. namespace: ns2, ns3, viz. Figure 4 níže)
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
*Figure 4* - SOAP Fault zpráva ve formátu deklarující SOAP v1.2

Unmodeled výjimky (runtime exception)
-------------------------------------
Zdrojem mapování checked výjimek a jejich příkladů je odkaz. Zdrojem/přičinou chyby je runtime výjimka, která může "vyletět" kdekoli v kódu, tzn. i mimo váš kod, např. v knihovnách třetích stran, např. aplikačním serveru a pod. 

### Runtime exception
Přičinou je výjimka uvedená v Figure 2, viz. výše. Takto vypadá SOAP Fault dle SOAP 1.1
```
<?xml version="1.0" ?>
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
	<S:Body>
		<ns2:Fault xmlns:ns2="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns3="http://www.w3.org/2003/05/soap-envelope">
			<faultcode>ns2:Server</faultcode>
			<faultstring>Something illegal.</faultstring>
		</ns2:Fault>
	</S:Body>
</S:Envelope>
```
*Figure 5* - SOAP Fault zpráva ve formátu deklarující SOAP v1.1

Takto vypadá SOAP Fault ve versi SOAP 1.2, opět obsahuje xml namespace z obou specifikaci (viz. namespace, ns2 a ns3, viz. Figure 6 níže)
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
        <ns3:Text xml:lang="ja">Something illegal.</ns3:Text>
      </ns3:Reason>
    </ns3:Fault>
  </S:Body>
</S:Envelope>
```
*Figure 6* - SOAP Fault zpráva ve formátu deklarující SOAP v1.2

### javax.xml.ws.WebServiceException
V případě, když namísto runtime exception vyhodíme javax.xml.ws.WebServiceException přímo (zdroj příkladu, zde). Tak výstupem jsou tyto formáty.
```
//javax.xml.ws.WebServiceException is thrown
throw new javax.xml.ws.WebServiceException( "Web Service Exception." );
```
Figure 7 - WebServiceException vyhozena v kódu
```
<?xml version="1.0" ?>
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
	<S:Body>
		<ns2:Fault xmlns:ns2="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ns3="http://www.w3.org/2003/05/soap-envelope">
			<faultcode>ns2:Server</faultcode>
			<faultstring>Web Service Exception.</faultstring>
		</ns2:Fault>
	</S:Body>
</S:Envelope>
```
*Figure 8* - SOAP Fault zpráva (binding WebServiceException) dle SOAP 1.2

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
        <ns3:Text xml:lang="ja">Something illegal.</ns3:Text>
      </ns3:Reason>
    </ns3:Fault>
  </S:Body>
</S:Envelope>
```
*Figure 9* - SOAP Fault zpráva (binding WebServiceException) dle SOAP 1.2 (opět obsahuje xml namespace obou SOAP versí, viz. ns2 a ns3)

### javax.xml.ws.soap.SOAPFaultException
Toto je případ, kdy vyhodíme v kódu specifickou výjimku pro soap based api - javax.xml.ws.soap.SOAPFaultException (zdroj příkladu).

#### SOAP Fault v1.1
```
SOAPFault soapFault = ...;
soapFault.setFaultCode( new QName( "http://sample.org", "UserDefined" ) );
soapFault.setFaultActor( "http://example.com/sample" );
soapFault.setFaultString( "SOAPFaultException happens." );
Detail detail = soapFault.addDetail();
SOAPElement soapElement = detail.addChildElement( new QName( "", "detailTest" ) );
soapElement.addTextNode( "TEST." );
 
//javax.xml.ws.soap.SOAPFaultException is thrown
throw new SOAPFaultException( soapFault );
```
*Figure 10* - SOAP Fault zpráva (binding SOAPFaultException) dle SOAP 1.1 specifikace vyhozena v kódu

Takto vypadá SOAP Fault pro SOAP v1.1
```
<?xml version="1.0" ?>
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
  <S:Body>
    <ns2:Fault xmlns:ns2="http://schemas.xmlsoap.org/soap/envelope/"
               xmlns:ns3="http://www.w3.org/2003/05/soap-envelope">
     <faultcode xmlns:ns0="http://sample.org">ns0:UserDefined</faultcode>
     <faultstring>SOAPFaultException happens.</faultstring>
     <faultactor>http://example.com/sample</faultactor>
     <detail><detailTest>TEST.</detailTest></detail>
    </ns2:Fault>
  </S:Body>
</S:Envelope>
```
*Figure 11* - SOAP Fault zpráva dle SOAP 1.1 specifikace

#### SOAP Fault v1.2
```
SOAPFactory soapFactory = SOAPFactory.newInstance( SOAPConstants.SOAP_1_2_PROTOCOL );
SOAPFault soapFault = soapFactory.createFault();
soapFault.appendFaultSubcode( new QName( "http://sample.org", "UserDefined" ) );
soapFault.setFaultRole( "http://example.com/sample" );
soapFault.addFaultReasonText( "SOAPFaultException happens.", Locale.getDefault() );
Detail detail = soapFault.addDetail();
SOAPElement soapElement = detail.addChildElement( new QName( "", "detailTest" ) );
soapElement.addTextNode( "TEST." );

//javax.xml.ws.soap.SOAPFaultException is thrown
throw new SOAPFaultException( soapFault );
```
*Figure 12* - SOAP Fault zpráva (binding SOAPFaultException) dle SOAP 1.2 specifikace vyhozena v kódu

Takto vypadá SOAP Fault pro SOAP v1.2
```
<?xml version="1.0" ?>
<S:Envelope xmlns:S= "http://www.w3.org/2003/05/soap-envelope">
  <S:Body>
    <ns3:Fault xmlns:ns2="http://schemas.xmlsoap.org/soap/envelope/"
               xmlns:ns3="http://www.w3.org/2003/05/soap-envelope">
      <ns3:Code>
        <ns3:Value>ns3:Sender</ns3:Value>
        <ns3:Subcode>
          <ns3:Value xmlns:ns0="http://sample.org">ns0:UserDefined</ns3:Value>
        </ns3:Subcode>
      </ns3:Code>
      <ns3:Reason>
        <ns3:Text xml:lang="ja">SOAPFaultException happens.</ns3:Text>
      </ns3:Reason>
      <ns3:Role>http://example.com/sample</ns3:Role>
      <ns3:Detail>
        <env:Detail xmlns:env="http://www.w3.org/2003/05/soap-envelope">
          <detailTest>TEST.</detailTest>
        </env:Detail>
      </ns3:Detail>
    </ns3:Fault>
  </S:Body>
</S:Envelope>
```
*Figure 13* - SOAP Fault zpráva dle SOAP 1.2 specifikace (pozn. opět obsahuje namepspace obou SOAP spec, viz. ns2 a ns3)

## Závěr
SOAP Fault je chybový stav, který je zobecněný a popsaný XSD schématem, který definuje specifikace SOAP[11,12]. Technickým výstupem je to vždy výjimka (pokud se bavíme o java jazyce), jejíchž chování je popsáno ve JAX-WS specifikaci [13]. Bacha aktuálně jsou dvě verse, které se obsahem liší (na úrovni xml formátu, SOAP fault zprávy - proto mnoho ukázek kódu - postihuje obě verse).

# Jak nejjednodušeji definovat SEI
Při psaní implementace, jsem si vylámal zuby, jak pojmenovávat třídy, popř. jaké používat package name a vůbec názvosloví. Po několikahodinovém zkoumání a nutno říct, že jsem ořezával dokud to šlo, jsem skončil s tímto výsledkem:

>**"Všechno je ve WSDL souboru"**

... detailní popis, co kde lze najít ve WSDL souboru, viz. níže.

## Postup pro jednoduché SEI
Stačí použít anotaci [WebService](https://docs.oracle.com/javaee/7/api/javax/jws/WebService.html) a explicitně uvést hodnoty těchto atributech:

 * serviceName, viz. `/definitions/service[@name]`
 * portName, viz. `/definitions/service/port[@name]`
 * targetNamespace, viz. `/definitions[@targetNamespace]`
 * endpointInterface (celá package cesta, tzn. targetNamespace+name), a name zjistim viz. `/definitions/portType[@name]`

... pozn: všechny xml cesty výše uvedené platí pro názvosloví praktikované/používané v [WSDL 1.1](https://www.w3.org/TR/wsdl.html#_introduction) a pro mapování/binding je použit [SOAP 1.2](https://www.w3.org/TR/soap12/), respektive binding pro [SOAP Fault 1.2](https://www.w3.org/TR/soap12/#soapfault). A pak Váš soap klient v java jazyce vypadá následovně:

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
**Figure 14** - nejjednodušší zápis soap web service klienta

## Jak projekt použít/spustit
Naklonuj si příslušný adresář via git a přesuň se do root adresáře projektu.

### Build project
Používám maven, takže v příslušném modulu (více modulový projekt v maven terminologii) spusť tento příkaz:

```
mvn clean package
``` 
**Figure 15** - zbuilduj projekt

### Publikování endpoint-u
Pro publikování WSDL via ([Endpoint](https://docs.oracle.com/javase/7/docs/api/javax/xml/ws/Endpoint.html)) používám nativní JDK chování/nástroj a poté jsem schopen spustit zbuildovanou/zkompilovanou třídu ([WebServicePublisher.java](https://github.com/tomascejka/jaxws.labs/blob/master/jaxws/jaxws.simplest/src/main/java/cz/toce/learn/javaee/ws/simplest/fault/server/WebServicePublisher.java)), který vystaví WSDL na adrese = `http://localhost:8080/ws/SimpleWebService`. Pro spuštění použiji maven plugin [exec](http://www.mojohaus.org/exec-maven-plugin/usage.html) - spuštění je blokující, takže tento skript spouštím ve svém vlastním okně (v tomto případě pomocí Windows CMD/Batch příkazu: `start call ...`):

```
start call mvn exec:java -Dexec.mainClass="cz.toce.learn.javaee.jaxws.simplest.fault.server.WebServicePublisher"
```
**Figure 16** - spusť endpoint webové služby (vystavíš WSDL)

### Spuštění klienta
Pro spuštění na klientské straně - použiji opět předkompilovanou třídu ([WebServiceClient.java](https://github.com/tomascejka/jaxws.labs/blob/master/jaxws/jaxws.simplest/src/main/java/cz/toce/learn/javaee/ws/simplest/client/impl/WebServiceClient.java))(připravenou ve stejném projektu,pouze pro jednoduchost, v praxi by byla klientská implementace v jiném projektu) a opět ji zavolám pomocí maven pluginu [exec](http://www.mojohaus.org/exec-maven-plugin/usage.html):

```
mvn exec:java -Dexec.mainClass="cz.toce.learn.javaee.jaxws.simplest.fault.client.WebServiceClient"
```
**Figure 17** - spusť klienta webové služby (a sleduj konsoli)

Výstupem je výjimka výpsana do konsole, což znamená, že je vrácen soap fault (v1.2). viz níže:

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
**Figure 18** - jak je presentován soap fault v konsoli (je to prachsprostá výjimka :))

## Zdroje
1. http://itdoc.hitachi.co.jp/manuals/3020/30203Y2310e/EY230182.HTM#ID00331 << Fault and exception processing on the Web Service - Wrapper exception class binding
2. https://docs.oracle.com/cd/E24329_01/web.1211/e24965/faults.htm#BABEGCDC << JAX-WS Java-to-WSDL mapping binds subclasses of java.lang.Exception to wsdl:fault messages
3. https://www.w3.org/TR/2001/NOTE-wsdl-20010315#_soap:fault << specifikace WSDL 1.1 binding SOAP 1.1
4. https://www.w3.org/Submission/wsdl11soap12/#fault-element << specifikace WSDL 1.1 binding SOAP 1.2
5. Jak deklarovat soap fault ve WSDL
6. https://docs.oracle.com/cd/E24329_01/web.1211/e24965/faults.htm#WSADV633 << Using Modeled Faults
7. https://docs.oracle.com/cd/E24329_01/web.1211/e24965/faults.htm#WSADV650 << Using Unmodeled Faults
8. https://www.w3.org/TR/soap/ << SOAP specifikace (rozcestník na aktuálně obě SOAP 1.1/1.2 verse)
9. https://www.w3.org/TR/2000/NOTE-SOAP-20000508/ << specifikace SOAP 1.1
10. https://www.w3.org/TR/soap12/ << specifikace SOAP 1.2
11. https://www.w3.org/TR/2000/NOTE-SOAP-20000508/#_Toc478383507 << spec SOAP Fault 1.1
12. https://www.w3.org/TR/soap12/#soapfault <<spec SOAP Fault 1.2
13. JAX-WS 2.2 specifications (najít odkaz)
