package com.example.ggjimmy.mobilebroadcast;

import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ggjimmy on 3/28/17.
 */

public class StreamServer extends IStreamServer {

    public BufferedWriter socketServerWriter;

    public StreamServer(int port) throws Exception{
        super(port);
    }

    @Override
    public void startServer(){
        Socket clientSocket = null;
        BufferedReader socketServerReader;
        String responseMessage;

        try{
            while(true){
                clientSocket = getServerSocket().accept();
                socketServerWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                socketServerReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                responseMessage = socketServerReader.readLine();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void sendData(byte[] data) {
        try {
            socketServerWriter.write(String.valueOf(data));
            socketServerWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
