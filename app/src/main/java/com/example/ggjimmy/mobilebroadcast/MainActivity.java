package com.example.ggjimmy.mobilebroadcast;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity{    

    private Camera camera = null;
    private CameraView cameraView = null;
    private boolean state = true;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        camera = Camera.open(0);
        cameraView = new CameraView(this, camera);
        FrameLayout layout = (FrameLayout) findViewById(R.id.camera_view);
        layout.addView(cameraView);
        setTitle("a2");

        try {
            Client client = new Client();
            client.startClient();
        } catch (Exception e) {
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

        //this constructor is reduant, but it gives better navigation
        public Client(){
            super();
        }

        @Override
        public void sendData(String message){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    swapCamera();
                }
            });
            try {
                send(message);
            }catch(IOException e){
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while(socketConnected()){
                if(getMessage()!=null){
                    swapCamera();
                }
            }
        }
    }
}
