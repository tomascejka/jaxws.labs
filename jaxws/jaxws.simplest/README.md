# Simplest web service project
For quickly prototyping - do not use complex creating of wsdl implementation (use Bottom-Up (Contract-Last) Approach). 
WSDL uses RPC style to develop quick/ease example with hello command. Use JDK 7

Do not use some special runtimes and generators, use native java. There is a script file for call native [wsimport](https://docs.oracle.com/javase/7/docs/technotes/tools/share/wsimport.html) tool (native/bundled jdk tools).

## Sources
 * use [wsimport] (https://docs.oracle.com/javase/7/docs/technotes/tools/share/wsimport.html) native bundled script for generate soap based structures
 * use [maven](https://maven.apache.org/) tool to create/manage java based project
 * use [jaxws](https://docs.oracle.com/javase/7/docs/technotes/guides/xml/jax-ws/index.html) with jdk 7
 * use [com.airhack](https://mvnrepository.com/artifact/com.airhacks/javaee7-essentials-archetype) maven archetype for java ee 7 project