# Nejjednoduší implementace server/klient v soap
Nutno dodat, že používám top-down [přístup](https://dzone.com/articles/our-next-stop-in-the-software-journey-a-top-down-s), tzn. že mám předpřipravený WSDL soubor.

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

```
@WebService(
        serviceName="SimplestWebServiceFault", // viz. /definitions/service[@name]
        portName = "SimplestWebServiceFaultSoap12Http",// viz. /definitions/service/port[@name]
        targetNamespace = "http://api.fault.simplest.jaxws.javaee.learn.toce.cz", // viz. /definitions[@targetNamespace]
        endpointInterface = "cz.toce.learn.javaee.jaxws.simplest.fault.api.SimplestWebServiceFaultPortType" // viz. složenina, viz. targetNamespace + /definitions/portType[@name]
)
@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
public class SimpleWebServiceFault12Impl implements SimplestWebServiceFaultPortType {
    // ... vynechano pro prehlednost
}

```
**Figure 1** - nejjednodušší zápis soap web service server

... pozn: všechny xml cesty výše uvedené platí pro názvosloví praktikované/používané v [WSDL 1.1](https://www.w3.org/TR/wsdl.html#_introduction) a pro mapování/binding je použit [SOAP 1.2](https://www.w3.org/TR/soap12/), respektive binding pro [SOAP Fault 1.2](https://www.w3.org/TR/soap12/#soapfault). A pak Váš soap klient v java jazyce vypadá následovně:


```
public class WebServiceClient {
    public static void main(String[] args) throws MalformedURLException {
        URL wsdlLocation = new URL(SERVICE_URL);
        SimplestWebServiceFault service = new SimplestWebServiceFault(wsdlLocation);
        
        HelloRuntimeExceptionRequest request = new HelloRuntimeExceptionRequest();
        request.setGreetings("Hello from RUNTIME Tomas");
        
        SimplestWebServiceFaultPortType port = service.getSimplestWebServiceFaultSoap12Http();
        port.helloRuntimeException(request);
    }
}

```
**Figure 2** - nejjednodušší zápis soap web service klienta