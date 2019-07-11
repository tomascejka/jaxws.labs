@echo off

REM
REM using wsimport to generate java soap client structures
REM
:generate
wsimport -s src\main\java -d target\generated-sources\wsimport  http://localhost:8080/ws/SimplestWebService?wsdl