package com.example.ggjimmy.mobilebroadcast;

import java.net.ServerSocket;

/**
 * Created by ggjimmy on 3/28/17.
 */

public abstract class IStreamServer {

    private ServerSocket socket;

    public IStreamServer(int port) throws Exception{
        socket = new ServerSocket(port);
    }

    public abstract void startServer();

    public abstract void sendData(byte[] data);

    public ServerSocket getServerSocket(){
        return this.socket;
    }
}
