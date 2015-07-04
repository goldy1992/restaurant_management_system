:begin
powershell -command " jps | Out-File -encoding utf32 ./process-list"

java FindProcesses

for /F "tokens=*" %%A in (processes-to-kill.txt) do (taskkill /f /pid %%A)
:end