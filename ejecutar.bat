@echo off
setlocal enabledelayedexpansion

REM Obtener la carpeta raíz del proyecto (donde está este .bat)
for %%A in ("%~dp0\.") do set "PROJECT_ROOT=%%~fA"

REM Ejecutar el programa
java -cp "%PROJECT_ROOT%\bin;%PROJECT_ROOT%\lib\jlayer-1.0.1 (1).jar" principal.PruebaInterfaz

pause
