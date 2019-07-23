@echo off

REM
REM Using wsimport to generate java soap client structures without provided 
REM wsdl online (via http protocol) and use locally provided.
REM
REM @author tomas.cejka
REM

SET wsname=SimpleWebServiceHandler
SET resdir=src\main\webapp
SET gendir=target\generated-sources\wsimport
REM SET port=8084

IF NOT EXIST %gendir% (
	MD %gendir% 
	GOTO generate
)

:generate
REM wsimport -s src\main\java -d %gendir% http://localhost:%port%/ws/%wsname%?wsdl
wsimport %resdir%\WEB-INF\wsdl\%wsname%.wsdl -extension -s %gendir% -d %gendir% 