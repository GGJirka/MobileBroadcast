/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.ggjimmy.mobilebroadcast;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;


/**
 *
 * @author root
 */

public class ClientHandler extends AppCompatActivity implements Runnable {
    private BufferedReader br;
    private BufferedWriter bw;
    private ServerData server;
    private String receivedMessage = "";
    private TextView view;

    public ClientHandler(ServerData server, BufferedReader br, BufferedWriter bw, TextView view) {
        this.server = server;
        this.br = br;
        this.bw = bw;
        this.view = view;
    }

    @Override
    public void run(){
        while (true){
            try {
                receivedMessage = br.readLine();
                runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        view.setText(receivedMessage);
                    }
                });
                server.sendToAllClients(receivedMessage);
                /*if(manageMessage.equals("ROOMNAME")){
                    Room room = new Room(receivedMessage.split(" ")[2]);
                    server.getRooms().add(room);

                }else if(manageMessage.equals("ROOMCONNECT")){
                    if(server.roomExist(receivedMessage.split(" ")[2])){
                        System.out.println(receivedMessage.split(" ")[2]+" exists");
                        if(!server.findRoomByName(receivedMessage.split(" ")[2]).getUsers()
                                .contains(receivedMessage.split(" ")[1])){
                            server.findRoomByName(receivedMessage.split(" ")[2]).addUser(
                            receivedMessage.split(" ")[1]);
                            System.out.println(receivedMessage.split(" ")[1]+" added");
                        }
                        server.sendToAllClients(receivedMessage+"\r\n");
                    }else{
                        server.sendToAllClients("NOEXISTROOM "+receivedMessage.split(" ")[1]+" "+receivedMessage.split(" ")[2]);
                    }
                }else if(manageMessage.equals("ROOMDISCONNECT")){
                     server.findRoomByName(receivedMessage.split(" ")[2])
                             .removeUser(receivedMessage.split(" ")[1]);
                     System.out.println(server.findRoomByName(receivedMessage.split(" ")[2]).getUsers().size());
                     if(server.findRoomByName(receivedMessage.split(" ")[2])
                             .getUsers().isEmpty()){
                         server.getRooms().remove(server.findRoomByName(receivedMessage.split(" ")[2]));
                         System.out.println("removed "+receivedMessage.split(" ")[2]);
                     }else{
                        server.sendToAllClients(receivedMessage);
                     }
                }else if(manageMessage.equals("ROOMSTART")){
                    server.sendToAllClients(receivedMessage);
                }else if(manageMessage.equals("ROOMEXISTS")){
                     if(server.roomExist(receivedMessage.split(" ")[2])){
                        System.out.println(receivedMessage.split(" ")[2]+" exists");
                        if(!server.findRoomByName(receivedMessage.split(" ")[2]).getUsers()
                                .contains(receivedMessage.split(" ")[1])){
                            server.findRoomByName(receivedMessage.split(" ")[2]).addUser(
                            receivedMessage.split(" ")[1]);
                            System.out.println(receivedMessage.split(" ")[1]+" added");
                        }
                        server.sendToAllClients(receivedMessage+"\r\n");
                    }else{
                        server.sendToAllClients("NOEXISTROOM "+receivedMessage.split(" ")[1]+" "+receivedMessage.split(" ")[2]);
                    }
                }*/
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void send() throws IOException{
        bw.write(receivedMessage);
        bw.flush();
    }

    public String getMessage() {
        return receivedMessage;
    }
}
