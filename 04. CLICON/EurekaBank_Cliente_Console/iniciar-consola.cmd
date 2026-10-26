@echo off
setlocal
cd /d "%~dp0"
set "MAVEN_CMD=C:\netbeans\java\maven\bin\mvn.cmd"
if not exist "%MAVEN_CMD%" set "MAVEN_CMD=mvn.cmd"
call "%MAVEN_CMD%" package dependency:copy-dependencies -DskipTests
if errorlevel 1 (
    echo No se pudo preparar la consola. Revise el error anterior.
    pause
    exit /b 1
)
java -cp "target\classes;target\dependency\*" ec.edu.monster.app.AppConsola
pause
