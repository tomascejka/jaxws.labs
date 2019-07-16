@echo off

SET gendir=target\generated-sources\wsimport
IF NOT EXIST %gendir% (
	MD %gendir% 
	GOTO generate
)

REM
REM using wsimport to generate java soap client structures
REM
:generate
wsimport -s src\main\java -d %gendir% http://localhost:8080/ws/SimpleWebService?wsdl