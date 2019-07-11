# Simplest web service project
For quickly prototyping - do not use complex creating of wsdl implementation (use Bottom-Up (Contract-Last) Approach). 
WSDL uses RPC style to develop quick/ease example with hello command. Use JDK 7

Do not use some special runtimes and generators, use native java. There is a script file for call native [wsimport](https://docs.oracle.com/javase/7/docs/technotes/tools/share/wsimport.html) tool (native/bundled jdk tools).

## Prerequisite
JDK 7+ (minimal JDK 7) and maven installed locally at your laptop.

## How to use
Clone repository locally into specific directory and move into parent directory.

### Build project
I use maven so call this command (in parent, this is multi-module maven project):

```
mvn clean package
``` 

### Publish endpoint
I use native jdk behaviour ([Endpoint](https://docs.oracle.com/javase/7/docs/api/javax/xml/ws/Endpoint.html)) and run already builded/compiled class which it publishes web service api at url = `http://localhost:8080/ws/SimplestWebService`. I use maven plugin [exec](http://www.mojohaus.org/exec-maven-plugin/usage.html) for execute precompiled java class (because publishing web service is blocking - open new console window via combination commands(windows batch): `start call ...`):

```
start call mvn exec:java -Dexec.mainClass="cz.toce.learn.javaee.ws.simplest.SimplestWebServicePublisher"
```

### Run client
Use same approach - use precompiled java class (bundled in same project for easily presentation) and call one via maven [exec](http://www.mojohaus.org/exec-maven-plugin/usage.html) plugin.

```
mvn exec:java -Dexec.mainClass="cz.toce.learn.javaee.ws.simplest.client.impl.SimplestWebServiceClient"
```

... and soap client write to output console "Hello Tomas !"

### Other usage
Web service is published and you can use eg. [soapui](https://www.soapui.org/) tool to get WSDL at `http://localhost:8080/ws/SimplestWebService?wsdl` in order to write another testcase.

## Sources
 * use [jdk 7] (https://openjdk.java.net/)
 * use [maven](https://maven.apache.org/) tool to create/manage java based project
 * use [com.airhack](https://mvnrepository.com/artifact/com.airhacks/javaee7-essentials-archetype) maven archetype for java ee 7 project
 * use [wsimport] (https://docs.oracle.com/javase/7/docs/technotes/tools/share/wsimport.html) native bundled script for generate soap based structures
 * use [jaxws](https://docs.oracle.com/javase/7/docs/technotes/guides/xml/jax-ws/index.html) with jdk 7
 * use RCP style because be simply ([to think about RPC style](https://developer.ibm.com/articles/ws-whichwsdl/))