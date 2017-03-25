package com.example.ggjimmy.mobilebroadcast;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.File;
import java.io.IOException;

/**
 * Created by ggjimmy on 3/23/17.
 */

public class CameraView extends SurfaceView implements SurfaceHolder.Callback, Camera.PictureCallback{
    private SurfaceHolder holder;
    private Camera camera;

    public CameraView(Context context, Camera camera) {
        super(context);
        this.camera = camera;
        holder = getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if(holder != null)
            return;

        camera.stopPreview();

        try{
            camera.setPreviewDisplay(holder);
        }catch(IOException e){
            e.printStackTrace();
        }
        camera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
    }


    @Override
    public void onPictureTaken(byte[] data, Camera camera) {

    }
}
