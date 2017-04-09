package com.example.ggjimmy.mobilebroadcast;

import android.app.NotificationManager;
import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity{    

    private Camera camera = null;
    private CameraView cameraView = null;
    private Client client;
    private boolean state = true;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        camera = Camera.open(0);
        cameraView = new CameraView(this, camera);
        FrameLayout layout = (FrameLayout) findViewById(R.id.camera_view);
        layout.addView(cameraView);
        text = (TextView) findViewById(R.id.text);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());

        try {
            client = new Client();
            client.startClient();
            client.sendData("USERCONNECT "+"connect");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.camera_rotation:
                swapCamera();
                break;

            case R.id.menu:
                camera.takePicture(null, null, pictureCallback);
                camera.release();
                break;

            case R.id.create_room:
                client.sendData("CREATEROOM "+"testing");
                break;

            default:
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    private Camera.PictureCallback pictureCallback = new Camera.PictureCallback(){
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            try{
                File file = FileExtension.getMediaFile(FileExtension.TYPE_IMAGE);
                if(file == null){
                    return;
                }
                FileOutputStream stream = new FileOutputStream(file);
                stream.write(data);
                stream.close();
            } catch(FileNotFoundException e) {
                e.getStackTrace();
            } catch(IOException e){
                e.getStackTrace();
            }
        }
    };

    public void swapCamera(){
        FrameLayout layout = (FrameLayout) findViewById(R.id.camera_view);
        layout.removeAllViews();

        camera = !state ? Camera.open(0) : Camera.open(1);

        state = !state ? true : false;

        cameraView = new CameraView(this, camera);
        layout.addView(cameraView);
    }

    private class Client extends ClientData{

        public Client(){
            super(text);
        }

        @Override
        public void sendData(String message){
            try {
                send(message);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
