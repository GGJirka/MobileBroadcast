package com.example.ggjimmy.mobilebroadcast;

import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    private Camera camera = null;
    private CameraView cameraView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        camera = Camera.open();
        cameraView = new CameraView(this, camera);
        FrameLayout layout = (FrameLayout) findViewById(R.id.camera_view);
        layout.addView(cameraView);
    }
}
