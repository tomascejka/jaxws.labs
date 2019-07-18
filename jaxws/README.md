# JAX-WS study project
Používám tento projekt pro studijní účely (to především), a k různých typům příkladů/pokusům, popř. k prototypování apod.

## Seznam projektů
* **jaxws.simplest.fault** - jak implementovat/pracovat s soap fault konstruktem (mechanismus pro chybové stavy s WSDL/SOAP API)
* **jaxws.simplest.faulthandler** - navazuji na předchozí projekt s tím, že soap fault zaloguji v triviálním handleru (kt. definuji programovým přístupem, tzn. nepoužívám anotaci+soubor)
* **jaxws.simplest.rpc** - jak implementovat WSDL/SOAP (rpc/literal, bez xsd/wsdl) server/client aplikaci v java
* **jaxws.simplest.handler** - jak implementovat/pracovat s soap handler-em (JAX-WS AOP přístup k soap zprávám)
... další budou přibývat

## Spustitelnost
Příklady jdou jednoduše spustit v konsoli (primární cíl, protože chci rychle a snadno ověřit myšlenku/nápad/tvrzení apod.). Nicméně příklady jsou to maven projekty s packaging war, tzn. lze je spouštět i jako webové aplikace (např. v Netbeans IDE stačí pravé tl. myši a příkaz Run).

## Poznámka pod čarou
Pokud existuje jednodušší cesta, kterou naleznete kdekoli v kódu/projektech a budete se chtít podělit - dejte vědet (love minimalismus).

## Zdroje
* [JAX-WS SE documentation](https://docs.oracle.com/javase/7/docs/technotes/guides/xml/jax-ws/index.html)
* JAX-WS [release notes](https://javaee.github.io/metro-jax-ws/doc/user-guide/ch02.html)
* JAX-WS [user guid](https://javaee.github.io/metro-jax-ws/doc/user-guide/ch03.html)
* documentation of [wsimport](https://docs.oracle.com/javase/7/docs/technotes/tools/share/wsimport.html) tool
