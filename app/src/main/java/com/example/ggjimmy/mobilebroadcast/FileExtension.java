package com.example.ggjimmy.mobilebroadcast;

import android.annotation.SuppressLint;
import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ggjimmy on 3/25/17.
 */

public class FileExtension {
    public static final int TYPE_IMAGE = 1;
    public static final int TYPE_VIDEO = 2;

    public static File getMediaFile(int type){
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES),"mediastreamer");
        if(!file.exists()){
            if(!file.mkdirs()){
                return null;
            }
        }
        @SuppressLint("SimpleDateFormat")
        String format = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mainFile = type == 1 ?
                  new File(file.getPath() + File.separator+"IMG_"+format+".jpg")
                : new File(file.getPath() + File.separator+"VID_"+format+".mp4");
        return mainFile;
    }
}
