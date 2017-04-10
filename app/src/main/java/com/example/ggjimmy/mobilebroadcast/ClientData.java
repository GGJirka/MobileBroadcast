package com.example.ggjimmy.mobilebroadcast;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by ggjimmy on 3/31/17.
 */

public abstract class ClientData extends AppCompatActivity{
    /*
    * Abstract class for client for easier code looking
    * This class helps main clients with optimalizing
    *
    * */
    private final String SERVER_IP = "10.42.0.120";
    private final int SERVER_PORT  =            4758;
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private TextView view;
    /*
    * Creates socket, must be run on new thread on androids
    * */
    public ClientData(TextView view){
        this.view = view;
    }

    public void startClient() throws Exception{
        new Thread(new SocketCreate()).start();
        //new Thread(this).start();
    }
    /*
    * Main constructor creates socket on my ip address and random port
    * init. buffers for streaming
    * */
    private class SocketCreate implements Runnable{
        @Override
        public void run(){
            try {
                /*
                * main reading thread that listenes to server*/
                socket = new Socket(InetAddress.getByName(SERVER_IP), SERVER_PORT);
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                send("ROOMCONNECT");

                while(socketConnected()){
                    final String message = bufferedReader.readLine();
                    if (message != null) {
                        runOnUiThread(new Runnable(){
                            @Override
                            public void run() {
                                view.setText(message);
                            }
                        });
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }


    /*
    * send data to server
    * */
    public abstract void sendData(String message);

    /*
    * send manager \r\n for real time so the buffer writes instantly
    * , normally he waits until another buffer flush
    * */
    public void send(String message) throws IOException {
        getBufferedWriter().write(message+"\r\n");
        getBufferedWriter().flush();
    }

    //returns bufferedWriter
    public BufferedWriter getBufferedWriter(){
        return this.bufferedWriter;
    }
    //returns bufferedReader
    public BufferedReader getBufferedReader(){
        return this.bufferedReader;
    }

    //returns actual socket state
    public boolean socketConnected(){
        return socket.isConnected();
    }

    //returns message from server
    public String getMessage(){
        String message = null;
        try {
            message =  this.bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }
}
