@ECHO OFF
SETLOCAL

set WRAPPER_DIR=%~dp0\.mvn\wrapper
set MAVEN_VERSION=3.9.9
set MAVEN_BASE_DIR=%USERPROFILE%\.m2\wrapper\dists\apache-maven-%MAVEN_VERSION%-bin
set MAVEN_HOME=%MAVEN_BASE_DIR%\apache-maven-%MAVEN_VERSION%
set MAVEN_ZIP=%MAVEN_BASE_DIR%\apache-maven-%MAVEN_VERSION%-bin.zip

if not exist "%MAVEN_HOME%\bin\mvn.cmd" (
    echo Maven local nao encontrado. Baixando Maven %MAVEN_VERSION%...
    if not exist "%MAVEN_BASE_DIR%" mkdir "%MAVEN_BASE_DIR%"
    powershell -Command "Invoke-WebRequest -Uri 'https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/%MAVEN_VERSION%/apache-maven-%MAVEN_VERSION%-bin.zip' -OutFile '%MAVEN_ZIP%'"
    powershell -Command "Expand-Archive -Path '%MAVEN_ZIP%' -DestinationPath '%MAVEN_BASE_DIR%' -Force"
)

"%MAVEN_HOME%\bin\mvn.cmd" %*
ENDLOCAL
