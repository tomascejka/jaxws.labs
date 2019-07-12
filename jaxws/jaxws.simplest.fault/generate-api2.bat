@echo off


REM
REM Generated sources/classes to temp-based maven target directory (after clean are deleted)
REM @author tomas.cejka
REM 

SET wsdlFile=SimpleWebServiceFault.wsdl
SET gendir=src\main\java

REM
REM using wsimport to generate java soap client structures
REM
:generate
REM wsimport -s src\main\java -d %gendir% 
wsimport -extension -s %gendir% -d %gendir% src\main\resources\WEB-INF\%wsdlFile% 