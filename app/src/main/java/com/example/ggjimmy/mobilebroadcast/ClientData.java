package com.example.ggjimmy.mobilebroadcast;
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

public abstract class ClientData implements Runnable{
    /*
    * Abstract class for client for easier code looking
    * This class helps main clients with optimalizing
    *
    * */
    private final String SERVER_IP = "192.168.1.101";
    private final int SERVER_PORT  =            4758;
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;

    /*
    * Creates socket, must be run on new thread on androids
    * */

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
        public void run() {
            try {
                socket = new Socket(InetAddress.getByName(SERVER_IP), SERVER_PORT);
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                send("new message insert");
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    /*
    * reading thread from server
    */

    @Override
    public abstract void run();

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
