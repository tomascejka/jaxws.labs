# STUDY - simplest soap fault
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
