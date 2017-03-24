package com.example.ggjimmy.mobilebroadcast;

import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    private Camera camera = null;
    private CameraView cameraView = null;
    private boolean state = false;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        camera = Camera.open(0);
        cameraView = new CameraView(this, camera);
        FrameLayout layout = (FrameLayout) findViewById(R.id.camera_view);
        layout.addView(cameraView);
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
                FrameLayout layout = (FrameLayout) findViewById(R.id.camera_view);
                layout.removeAllViews();
                camera = !state ? Camera.open(0) : Camera.open(1);
                cameraView = new CameraView(this, camera);
                layout.addView(cameraView);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
