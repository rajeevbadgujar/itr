@ECHO OFF

set ITR_WEBSERVICE_CONFIG_FILE=
REM -- ############### End of Global Variables ###########

REM -- ####################################################################
REM -- # Check if the user has specified any option, if not print the usage 
REM -- ####################################################################
IF [X%3]==[X] (GOTO USAGE)
REM -- ############## End of Parameter Checking #######################


REM -- ###############################################################
REM -- # Parse the arguments and set global flags and varibales
REM -- ###############################################################

set COMMAND=
set do_executeBulkItr=0
set do_bulkItrStatus=0
set ITR_WEBSERIVCE_INSTALL_DIR=
set JRE_HOME=
set JAVA_OPTS=
set ITR_WEBSERVICE_LOG_DIR=
set ITR_USERID=
set ITR_PASSWORD=
set PFX_FILEPATH=
set PFX_FILE_PASSWORD=
set XML_ZIP_FILE_PATH=
set RESPONSE_FILE_PATH=
set ERROR_FILE_PATH=
set ITR_WEBSERVICE_LOG_DIR=
set TOKEN_NUMBER=
set PAN_ID=
set IS_XMLSIGNED=
set IS_HARDTOKEN=
set HARDTOKEN_PIN=
set XML_PFX_FILEPATH=
set XML_PFX_FILE_PASSWORD=
set HARDTOKEN_TYPE=

REM -- Checking if the ITR_WEBSERVICE_CONFIG_FILE is set, else read the parameter after /c. If both are not set, come out with an error
IF [X%ITR_WEBSERVICE_CONFIG_FILE%]==[X] (GOTO PARAMCHECK)

:PARAMCHECK
if [X%2] == [X/c] (
	set ITR_WEBSERVICE_CONFIG_FILE=%3
	set COMMAND=%1
	GOTO CHECKFILE
)

if [X%1] == [X/c] (
	set ITR_WEBSERVICE_CONFIG_FILE=%2  
	set COMMAND=%3
	GOTO CHECKFILE
)

IF [X%ITR_WEBSERVICE_CONFIG_FILE%]==[X] (GOTO USAGE)

:CHECKFILE
IF EXIST %ITR_WEBSERVICE_CONFIG_FILE% ( GOTO PARSEPARAMS )
echo "Failed to read the configuration file. Please specify the valid path of the file"
GOTO USAGE

:PARSEPARAMS

IF [X%COMMAND%]==[X] ( GOTO USAGE )

IF [%COMMAND%]==[executeBulkItr] ( GOTO EXECUTE )


IF [%COMMAND%]==[bulkItrStatus] ( GOTO STATUS )


echo "Please specify a valid command"
GOTO USAGE

:EXECUTE
set do_executeBulkItr=1
call :READCONFIGFILE
goto :EOF

:STATUS
set do_bulkItrStatus=1
call :READCONFIGFILE
goto :EOF


REM -- ############### Reading values from the configuration file ###############
:READCONFIGFILE
FOR /F "tokens=1* delims==" %%A IN ('type %ITR_WEBSERVICE_CONFIG_FILE% ^| findstr "ITRWEBSERVICEINSTALLDIR"') DO SET ITR_WEBSERVICE_INSTALL_DIR=%%B
FOR /F "tokens=1* delims==" %%A IN ('type %ITR_WEBSERVICE_CONFIG_FILE% ^| findstr "ITRUSERID"') DO SET ITR_USERID=%%B
FOR /F "tokens=1* delims==" %%A IN ('type %ITR_WEBSERVICE_CONFIG_FILE% ^| findstr "ITRPASSWORD"') DO SET ITR_PASSWORD=%%B
FOR /F "tokens=1* delims==" %%A IN ('type %ITR_WEBSERVICE_CONFIG_FILE% ^| findstr "PFXFILEPATH"') DO SET PFX_FILEPATH=%%B
FOR /F "tokens=1* delims==" %%A IN ('type %ITR_WEBSERVICE_CONFIG_FILE% ^| findstr "PFXFILEPASSWORD"') DO SET PFX_FILE_PASSWORD=%%B
FOR /F "tokens=1* delims==" %%A IN ('type %ITR_WEBSERVICE_CONFIG_FILE% ^| findstr "XMLZIPFILEPATH"') DO SET XML_ZIP_FILE_PATH=%%B
FOR /F "tokens=1* delims==" %%A IN ('type %ITR_WEBSERVICE_CONFIG_FILE% ^| findstr "RESPONSEFILEPATH"') DO SET RESPONSE_FILE_PATH=%%B
FOR /F "tokens=1* delims==" %%A IN ('type %ITR_WEBSERVICE_CONFIG_FILE% ^| findstr "ERRORFILEPATH"') DO SET ERROR_FILE_PATH=%%B
FOR /F "tokens=1* delims==" %%A IN ('type %ITR_WEBSERVICE_CONFIG_FILE% ^| findstr "LOGDIR"') DO SET ITR_WEBSERVICE_LOG_DIR=%%B
FOR /F "tokens=1* delims==" %%A IN ('type %ITR_WEBSERVICE_CONFIG_FILE% ^| findstr "TOKENNUMBER"') DO SET TOKEN_NUMBER=%%B
FOR /F "tokens=1* delims==" %%A IN ('type %ITR_WEBSERVICE_CONFIG_FILE% ^| findstr "PANID"') DO SET PAN_ID=%%B
FOR /F "tokens=1* delims==" %%A IN ('type %ITR_WEBSERVICE_CONFIG_FILE% ^| findstr "XMLSIGNATURE"') DO SET IS_XMLSIGNED=%%B
FOR /F "tokens=1* delims==" %%A IN ('type %ITR_WEBSERVICE_CONFIG_FILE% ^| findstr "HARDTOKEN"') DO SET IS_HARDTOKEN=%%B
FOR /F "tokens=1* delims==" %%A IN ('type %ITR_WEBSERVICE_CONFIG_FILE% ^| findstr "HARDTOKENPIN"') DO SET HARDTOKEN_PIN=%%B
FOR /F "tokens=1* delims==" %%A IN ('type %ITR_WEBSERVICE_CONFIG_FILE% ^| findstr "XMLPFXFILEPATH"') DO SET XML_PFX_FILEPATH=%%B
FOR /F "tokens=1* delims==" %%A IN ('type %ITR_WEBSERVICE_CONFIG_FILE% ^| findstr "XMLPFXFILEPASSWORD"') DO SET XML_PFX_FILE_PASSWORD=%%B
FOR /F "tokens=1* delims==" %%A IN ('type %ITR_WEBSERVICE_CONFIG_FILE% ^| findstr "HARDTOKENTYPE"') DO SET HARDTOKEN_TYPE=%%B



if "X%ITR_WEBSERVICE_INSTALL_DIR%"=="X" (
	echo "Invalid Configuration File"
	GOTO END
)
if not "%ITR_WEBSERVICE_INSTALL_DIR%"=="" (set ITR_WEBSERVICE_INSTALL_DIR=%ITR_WEBSERVICE_INSTALL_DIR%) else (set ITR_WEBSERVICE_INSTALL_DIR=.)
if not "%ITR_USERID%"=="" (set ITR_USERID=%ITR_USERID%)
if not "%ITR_PASSWORD%"=="" (set ITR_PASSWORD=%ITR_PASSWORD%)
if not "%PFX_FILEPATH%"=="" (set PFX_FILEPATH=%PFX_FILEPATH%)
if not "%PFX_FILE_PASSWORD%"=="" (set PFX_FILE_PASSWORD=%PFX_FILE_PASSWORD%)
if not "%XML_ZIP_FILE_PATH%"=="" (set XML_ZIP_FILE_PATH=%XML_ZIP_FILE_PATH%)
if not "%RESPONSE_FILE_PATH%"=="" (set RESPONSE_FILE_PATH=%RESPONSE_FILE_PATH%)
if not "%ERROR_FILE_PATH%"=="" (set ERROR_FILE_PATH=%ERROR_FILE_PATH%)
if not "%ITR_WEBSERVICE_LOG_DIR%"=="" (set ITR_WEBSERVICE_LOG_DIR=%ITR_WEBSERVICE_LOG_DIR%)
if not "%TOKEN_NUMBER%"=="" (set TOKEN_NUMBER=%TOKEN_NUMBER%)
if not "%PAN_ID%"=="" (set PAN_ID=%PAN_ID%)

set JRE_HOME=%ITR_WEBSERVICE_INSTALL_DIR%\jre

if not "X%ITR_USERID%"=="X" (
	set JAVA_OPTS=%JAVA_OPTS% -DeriUserId=%ITR_USERID%
)
if not "X%ITR_PASSWORD%"=="X" (
	set JAVA_OPTS=%JAVA_OPTS% -DeriPassword=%ITR_PASSWORD% 
)

set JAVA_OPTS=%JAVA_OPTS% -DpfxFilePath=%PFX_FILEPATH% -DpfxFilePassword=%PFX_FILE_PASSWORD%  -DresponseFilePath="%RESPONSE_FILE_PATH%" -DerrorFilePath="%ERROR_FILE_PATH%" -Djava.awt.headless=true -Dsun.java2d.noddraw=true -DinstallDir="%ITR_WEBSERVICE_INSTALL_DIR%"

REM -- ##################### End JAVA_OPTS #########################
GOTO NEXT
REM -- ############## End of Reading Configuration File #######################

:NEXT
REM -- ############## execute the webservice #######################
IF [%do_executeBulkItr%]==[1] ( 
	set JAVA_OPTS_FOR_EXECUTE=%JAVA_OPTS% -DxmlPfxFilePath=%XML_PFX_FILEPATH% -DxmlPfxFilePassword=%XML_PFX_FILE_PASSWORD% -DxmlSignature=%IS_XMLSIGNED% -DhardToken=%IS_HARDTOKEN% -DhardTokenPin=%HARDTOKEN_PIN%  -DhardTokenType=%HARDTOKEN_TYPE% -DxmlZipFilePath="%XML_ZIP_FILE_PATH%"
	echo %JAVA_OPTS_FOR_EXECUTE%
	call  "%JRE_HOME%\bin\java" %JAVA_OPTS_FOR_EXECUTE% -cp "%ITR_WEBSERVICE_INSTALL_DIR%\lib\webservice_itr-1.0.jar";"%ITR_WEBSERVICE_INSTALL_DIR%\lib\axis-1.4.jar" com.nextleap.itr.webservice.ITRWebService itrWeb!123 >>"%ITR_WEBSERVICE_LOG_DIR%"\monitorWebService.log 2>&1
	echo Done.
	GOTO END
)

REM -- ############## status of the webservice #######################
IF [%do_bulkItrStatus%]==[1] ( 
	set JAVA_OPTS_FOR_STATUS=%JAVA_OPTS% -DpanID="%PAN_ID%" -DtokenNumber="%TOKEN_NUMBER%"
	echo %JAVA_OPTS_FOR_STATUS%
	call "%JRE_HOME%\bin\java" %JAVA_OPTS_FOR_STATUS% -cp "%ITR_WEBSERVICE_INSTALL_DIR%\lib\webservice_itr-1.0.jar";"%ITR_WEBSERVICE_INSTALL_DIR%\lib\axis-1.4.jar" com.nextleap.itr.webservice.ITRVStatusService itrWeb!123 >>"%ITR_WEBSERVICE_LOG_DIR%"\monitorWebService.log 2>&1
	echo Done.
	GOTO END
)


REM -- ############### Function Usage ####################
:USAGE
	ECHO "Usage: itrwebservice /c configfile executeBulkItr|bulkItrStatus"
	@ECHO OFF
goto :EOF
REM -- ############## End of Usage #######################

:End
REM -- ############## Resetting the variables to the default value ############## 
set ITR_WEBSERVICE_CONFIG_FILE=
set JRE_HOME=
set COMMAND=
set JAVA_OPTS=
