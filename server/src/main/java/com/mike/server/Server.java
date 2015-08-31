package com.mike.server;

import com.mike.message.TableList;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;

/**
 * @author Goldy
 */
public class Server
{    
    private ServerSocket socket; 
    private ServerRunThread listener;
    private HashSet<ClientConnection> waiterClient;
    private HashSet<ClientConnection> tillClient;   
    private ClientConnection barClient = null;
    private ClientConnection kitchenClient = null;
    private TableList tables;
    private boolean socketListening;
    
    public Server(ServerSocket socket, HashSet<ClientConnection> waiterClient,
                HashSet<ClientConnection> tillClient) throws IOException {
        this.waiterClient = waiterClient;
        this.tillClient = tillClient;
        this.socket = socket;
    } // myserver
    
    public Server() {         }
    
    public TableList getTables() {
        return tables;
    } // table

    /**
     * @return A HashSet of all the waiter clients
     */
    public HashSet<ClientConnection> getWaiterClients() {
        synchronized(getWaiterClient()) {
            return getWaiterClient();
        } // sync
    }
    
    /**
     *
     * @return A hash set of all the till clients
     */
    public HashSet<ClientConnection> getTillClients() {
        synchronized(getTillClient()){
            return getTillClient();
        } // sync
    }
    
    public ClientConnection getBarClient()  {
        return barClient;
    }
    
    public void setBarClient(ClientConnection client) {
        synchronized(barClient) {
            barClient = client;
        }
    }
    
    public ClientConnection getKitchenClient() {
        return kitchenClient;
    }
    
    public void setKitchenClient(ClientConnection client){
        kitchenClient = client;
    }

    /**
     * @return the listener
     */
    public ServerRunThread getListener() {
        return listener;
    }

    /**
     * @param listener the listener to set
     */
    public void setListener(ServerRunThread listener) {
        this.listener = listener;
    }

    /**
     * @return the socket
     */
    public ServerSocket getSocket() {
        return socket;
    }

    /**
     * @param socket the socket to set
     */
    public void setSocket(ServerSocket socket) {
        this.socket = socket;
    }

    /**
     * @return the waiterClient
     */
    public HashSet<ClientConnection> getWaiterClient() {
        return waiterClient;
    }

    /**
     * @param waiterClient the waiterClient to set
     */
    public void setWaiterClient(HashSet<ClientConnection> waiterClient) {
        this.waiterClient = waiterClient;
    }

    /**
     * @return the tillClient
     */
    public HashSet<ClientConnection> getTillClient() {
        return tillClient;
    }

    /**
     * @param tillClient the tillClient to set
     */
    public void setTillClient(HashSet<ClientConnection> tillClient) {
        this.tillClient = tillClient;
    }

    /**
     * @param tables the tables to set
     */
    public void setTables(TableList tables) {
        this.tables = tables;
    }

    /**
     * @return the socketListening
     */
    public boolean isSocketListening() {
        return socketListening;
    }

    /**
     * @param socketListening the socketListening to set
     */
    public void setSocketListening(boolean socketListening) {
        this.socketListening = socketListening;
    }
} // MySocket class
