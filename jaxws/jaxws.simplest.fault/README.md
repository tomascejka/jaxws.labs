# Simplest SOAP fault
Studijní projekt pro případ, že chci nasimulovat soap fault chování. Cílem je **minimalistický** [příklad užití](https://github.com/tomascejka/jaxws.labs/blob/master/jaxws/jaxws.simplest.fault/SIMPLEST_USECASE.md) *SOAP Fault*-u na obou stranách (*server*/*klient*).

*SOAP Fault* je chybový stav, který definuje specifikace SOAP [1,2]. V Java EE je SOAP specifikace popsána interní JSR specifikací *JSR-224: Java API for XML Web Services* (JAX-WS) [3].

## Jak projekt používat

Zde se dozvíte, jak s projektem pracovat/spouštět. Další informace o zkoumáních a případně studijních závěrech, lze nalézt zde:
* [studijní dokumentace](https://github.com/tomascejka/jaxws.labs/blob/master/jaxws/jaxws.simplest.fault/STUDY.md)
* [jednoduchá implementace server/klient](https://github.com/tomascejka/jaxws.labs/blob/master/jaxws/jaxws.simplest.fault/SIMPLEST_USECASE.md)

### Build project
Pro laborování a rychlé prototypování si vystačíš s jednoduchým buildem pomocí maven a spouštění testů.
```
mvn clean test
```
**Figure 1** - zbuilduj projekt

### Publikování endpoint-u
V případě, že potřebuješ testovat pomocí, např. soapui nástroje  - bude nutné vystavit WSDL. To lze spuštěním třídy ([WebServicePublisher.java](https://github.com/tomascejka/jaxws.labs/blob/master/jaxws/jaxws.simplest/src/main/java/cz/toce/learn/javaee/ws/simplest/fault/WebServicePublisher.java))

```
start call mvn exec:java -Dexec.mainClass="cz.toce.learn.javaee.jaxws.simplest.fault.WebServicePublisher"
```
**Figure 2** - pro vystavení WSDL na http/URL

To zařídí, že jsou vystaveny WSDL na dvou adresách pomocí Java SE  ([Endpoint](https://docs.oracle.com/javase/7/docs/api/javax/xml/ws/Endpoint.html)), abych mohl presentovat použití SOAP Fault ve versích 1.1 a 1.2:

* [http://localhost:8081/jax-ws/simplest/fault11](http://localhost:8081/jax-ws/simplest/fault11)
* [http://localhost:8081/jax-ws/simplest/fault12](http://localhost:8081/jax-ws/simplest/fault12)

... proč dvě adresy vysvětlím podrobněji ve [studii](https://github.com/tomascejka/jaxws.labs/blob/master/jaxws/jaxws.simplest.fault/STUDY.md).

### Spuštění klienta
V tuto chvíli není implementováno, a předpokládám, že budou stačit junit testy, kde lze dostatečně laborovat s různými případy.

## Zdroje
1. https://www.w3.org/TR/2000/NOTE-SOAP-20000508/#_Toc478383507 << spec SOAP Fault 1.1
2. https://www.w3.org/TR/soap12/#soapfault <<spec SOAP Fault 1.2
3. https://www.jcp.org/en/jsr/detail?id=224
4. [JAX-WS 2.2 implementace (Metro)](https://javaee.github.io/metro-jax-ws/)
5. JAX-WS návod pro JAVA EE <a href="https://docs.oracle.com/javaee/5/tutorial/doc/bnayl.html" target="_blank">5</a>, <a href="https://docs.oracle.com/javaee/6/tutorial/doc/bnayl.html" target="_blank">6</a>, <a href="https://docs.oracle.com/javaee/7/tutorial/jaxws.htm" target="_blank">7</a>, <a href="https://javaee.github.io/tutorial/jaxws.html" target="_blank">8</a> 

