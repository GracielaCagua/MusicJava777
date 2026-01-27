@echo off
setlocal enabledelayedexpansion

REM Obtener la carpeta raíz del proyecto (donde está este .bat)
for %%A in ("%~dp0\.") do set "PROJECT_ROOT=%%~fA"

echo ============================================
echo Compilando proyecto Java...
echo ============================================

REM Crear carpeta bin si no existe
if not exist "%PROJECT_ROOT%\bin" mkdir "%PROJECT_ROOT%\bin"

REM Limpiar binarios antiguos
del /S /Q "%PROJECT_ROOT%\bin" >nul 2>&1
mkdir "%PROJECT_ROOT%\bin" >nul 2>&1

REM Compilar con Java 8
for /r "%PROJECT_ROOT%\src" %%F in (*.java) do (
    set "JAVA_FILES=!JAVA_FILES! "%%F""
)
javac -cp "%PROJECT_ROOT%\lib\jlayer-1.0.1 (1).jar" -d "%PROJECT_ROOT%\bin" -source 1.8 -target 1.8 !JAVA_FILES!

if errorlevel 1 (
    echo.
    echo ERROR en la compilacion!
    echo.
    pause
    exit /b 1
)

echo.
echo ============================================
echo Compilacion exitosa!
echo ============================================
echo.
pause
