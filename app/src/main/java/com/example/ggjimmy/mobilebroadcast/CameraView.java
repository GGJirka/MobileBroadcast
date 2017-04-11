package com.example.ggjimmy.mobilebroadcast;

import android.content.Context;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.provider.MediaStore;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;


import java.io.IOException;

/**
 * Created by ggjimmy on 3/23/17.
 */

public class CameraView extends SurfaceView implements SurfaceHolder.Callback, Camera.PictureCallback{

    private SurfaceHolder holder;
    private Camera camera;
    private MediaRecorder mediaRecorder;
    private TextView view;

    public CameraView(Context context, Camera camera,TextView view){
        super(context);
        this.camera = camera;
        holder = getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        this.view = view;
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


        try{
            camera.stopPreview();
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        camera.stopPreview();
        camera.release();
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera){

    }

    //open media recorder for video capturing (sending vide over sockets)
    public void openMediaRecorder(){
        mediaRecorder = new MediaRecorder();
                //camera.unlock();
                mediaRecorder.setCamera(camera);

                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
                mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

                mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
                mediaRecorder.setOutputFile(FileExtension.getMediaFile(FileExtension.TYPE_VIDEO).toString());
                mediaRecorder.setPreviewDisplay(getHolder().getSurface());

                try{
                    mediaRecorder.prepare();
                    view.setText("media recorder prepared");
                }catch(Exception e){
                    view.setText(e.getMessage());
                }
    }
    /*
    * starting media recorder
    * */
    public boolean startMediaRecorder(){
        try {
            if (mediaRecorder != null) {
                this.mediaRecorder.start();
                view.setText("media recorder started ");
            }
        }catch(Exception e){
            e.printStackTrace();
            view.setText(e.getMessage());
            return false;
        }
        return true;
    }

    public Surface getSurfaceHolder(){
        return holder.getSurface();
    }
}
