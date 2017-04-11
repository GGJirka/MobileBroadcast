/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ggjimmy.mobilebroadcast;

import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.view.Surface;
import android.view.SurfaceView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerData extends AppCompatActivity{

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader br;
    private BufferedWriter bw;
    private TextView view;
    private MediaRecorder mMediaRecorder;
    private String message = "";
    private Camera camera;
    private SurfaceView surface;

    public ServerData(Camera camera, SurfaceView surface, TextView view) {
        this.view = view;
        this.listen();
        this.camera = camera;
        this.surface = surface;
    }

    public void listen() {
        Thread listenThread = new Thread(new Runnable() {
            ParcelFileDescriptor parcelFileDescriptor;
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(4758);
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            view.setText("beyi");
                        }
                    });
                    clientSocket = serverSocket.accept();
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            view.setText("pripojil");
                        }
                    });

                    bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    sendToAllClients("ahoj");

                } catch (IOException e) {
                    e.printStackTrace();
                }

                while (true) {
                    try {
                        message = br.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            view.setText(message);
                        }
                    });

                }
            }
        });
        listenThread.start();
    }

    public void sendToAllClients(final String message){
        try {
            bw.write(message+"\r\n");
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tryToReconnect() throws IOException {
        this.serverSocket.close();
        clientSocket = null;
        System.gc();
        clientSocket = serverSocket.accept();
    }

}
