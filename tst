#!/bin/bash    
java -cp dist/TestClientServer.jar Server.MyServer&
sleep 2s
java -cp dist/TestClientServer.jar OutputPrinter.OutputClient bar&
sleep 2s
java -cp dist/TestClientServer.jar OutputPrinter.OutputClient kitchen&
sleep 2s
java -cp dist/TestClientServer.jar Client.MyClient&
sleep 2s
java -cp dist/TestClientServer.jar Till.TillClient&
