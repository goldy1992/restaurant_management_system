:begin
start java -cp dist/TestClientServer.jar Server.MyServer
ping 1.1.1.1 -n 1 -w 3000 > nul
start java -cp dist/TestClientServer.jar Client.OutputClient bar
ping 1.1.1.1 -n 1 -w 3000 > nul
start java -cp dist/TestClientServer.jar Client.OutputClient kitchen
ping 1.1.1.1 -n 1 -w 3000 > nul
start java -cp dist/TestClientServer.jar Client.WaiterClient
ping 1.1.1.1 -n 1 -w 3000 > nul
java -cp dist/TestClientServer.jar Client.TillClient
:end

