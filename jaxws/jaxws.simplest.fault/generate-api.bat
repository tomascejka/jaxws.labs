@echo off

REM
REM Use 'wsimport' to generate jaxb archetypes locally without online 
REM providing wsdl using javax.xml.ws.Endpoint.
REM
REM @author tomas.cejka
REM
REM @see https://docs.oracle.com/javase/7/docs/technotes/tools/share/wsimport.html
REM @see https://docs.oracle.com/javase/7/docs/api/javax/xml/ws/Endpoint.html
REM @see https://docs.oracle.com/javase/8/docs/api/javax/xml/ws/Endpoint.html
REM

REM Name of wsdl file
SET wsname=SimplestWebServiceFault

REM Location where wsdl is located
SET wsdldir=src\main\webapp\WEB-INF\wsdl

REM Location where jaxb archetypes will be generated
SET gendir=target\generated-sources\wsimport

REM When script is executed first than maven goal (output dir must be created)
IF NOT EXIST %gendir% (
    MD %gendir% 
    GOTO generate
)

REM Call useful wsimport tool with locally wsdl file
:generate
wsimport %wsdldir%\%wsname%.wsdl -extension -s %gendir% -d %gendir% 