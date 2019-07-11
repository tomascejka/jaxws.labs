@echo off

SET outputdir=src\main\java\cz\toce\learn\javaee\ws\simplest\client\api
REM
REM check if exist directory for output classes
REM
IF NOT EXIST %outputdir% (
	md %outputdir% GOTO generate
)

REM
REM using wsimport to generate java soap client structures
REM
:generate
wsimport -s %outputdir% http://localhost:8080/ws/SimplestWebService?wsdl