@echo off

REM IF [%1]==[] (
REM	goto error_input
REM )
REM set WSDL=%1
REM http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso?WSDL
set WSDL=CountryInfoService.wsdl

echo --------------------------------------
echo   Name your api
echo --------------------------------------
SET /P noa="eg. stockApi: "
IF [%noa%]==[] (
	goto error_name
)

:WSGEN
set JAVA_HOME=E:\Tools\jdk\openjdk\jdk-8u312-b07\bin
call %JAVA_HOME%\wsimport -extension -p %noa% -keep %WSDL%
echo --------------------------------------
echo  Your api model has been stored:
echo      at path: .\%noa%
echo --------------------------------------
goto eof

:error_input
echo ERROR - wrong parameter - is null or empty
goto eof

:error_name
echo ERROR - wrong name - is null or empty
goto eof

:eof