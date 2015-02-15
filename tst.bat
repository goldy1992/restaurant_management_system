:begin
start java -cp dist/TestClientServer.jar Server.MyServer&
timeout /t 5 /nobreak
start java -cp dist/TestClientServer.jar OutputPrinter.OutputClient bar&
timeout /t 5 /nobreak
start java -cp dist/TestClientServer.jar OutputPrinter.OutputClient kitchen&
timeout /t 5 /nobreak
start java -cp dist/TestClientServer.jar Client.MyClient&
:end

