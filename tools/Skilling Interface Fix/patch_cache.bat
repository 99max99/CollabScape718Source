@echo off
set /p cp=Enter cache path:
java -cp bin; app.Main %cp%
pause