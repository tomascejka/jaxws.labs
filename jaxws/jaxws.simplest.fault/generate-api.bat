@echo off

REM
REM Generated sources/classes to temp-based maven target directory (after clean are deleted)
REM @author tomas.cejka
REM 

SET wsdlFile=SimpleWebServiceFault.wsdl
SET gedir=target\generated-sources\wsimport
SET apidir=%target\generated-sources\wsimport%\api
IF NOT EXIST %apidir% (
	MD %apidir% 
	GOTO generate
)

REM
REM using wsimport to generate java soap client structures
REM
:generate
REM wsimport -s src\main\java -d %gendir% 
wsimport -s %gendir%\api -d %gendir% src\main\resources\WEB-INF\%wsdlFile% 